package org.jeecg.modules.mall.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mall.api.vo.ProductListVO;
import org.jeecg.modules.mall.api.vo.ProductVO;
import org.jeecg.modules.mall.api.vo.ReqProductDetail;
import org.jeecg.modules.mall.api.vo.ReqProductListByCategory;
import org.jeecg.modules.mall.config.MallConfig;
import org.jeecg.modules.mall.entity.Cart;
import org.jeecg.modules.mall.entity.Product;
import org.jeecg.modules.mall.entity.ProductDetail;
import org.jeecg.modules.mall.entity.bo.CartProductBO;
import org.jeecg.modules.mall.service.ICartService;
import org.jeecg.modules.mall.service.IProductDetailService;
import org.jeecg.modules.mall.service.IProductService;
import org.jeecg.modules.support.entity.Image;
import org.jeecg.modules.support.service.IImageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date:   2019-06-03
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品信息")
@RestController
@RequestMapping("/api/mall/product")
public class ProductApiController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IProductDetailService productDetailService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private MallConfig mallConfig;
    @Autowired
    private ICartService cartService;

    /**
     * 分页列表查询
     * @param product
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @PostMapping(value = "/list")
    public Result<List<ProductListVO>> queryPageList(Product product,
                                               @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                               @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                               HttpServletRequest req) {
        Result<List<ProductListVO>> result = new Result<>();
        product.setStatus(1);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>(product);
        Page<Product> page = new Page<>(pageNo, pageSize);
        IPage<Product> pageList = productService.page(page, queryWrapper);
        result.setSuccess(true);
        List<ProductListVO> listVO = new ArrayList<>();
        for (Product productDO :pageList.getRecords()) {
            ProductListVO vo = new ProductListVO();
            BeanUtils.copyProperties(productDO, vo);
            List<Image> imageList = queryImageListByMainId(vo.getId(),1);
            if(imageList!=null && imageList.size()>0){
                String url = imageList.get(0).getUrl();
                vo.setPicUrl(mallConfig.getPicPrefix()+url);
            }
            listVO.add(vo);
        }

        result.setResult(listVO);
        return result;
    }


    /**
     * 分页列表查询
     * @param productDetail
     * @param req
     * @return
     */
    @PostMapping(value = "/detail")
    public Result<ProductVO> queryDetail(ReqProductDetail productDetail, HttpServletRequest req) {
        Result<ProductVO> result = new Result<>();
        Product product = new Product();
        product.setId(productDetail.getProductId());
        product.setStatus(1);
        Product productDO = productService.getById(product.getId());
        ProductVO vo = new ProductVO();
        BeanUtils.copyProperties(productDO, vo);
        ProductDetail detail = productDetailService.selectByMainId(productDO.getId());

        vo.setDetail(detail);
        List<Image> mainImageList = queryImageListByMainId(vo.getId(),1);
        if(!mainImageList.isEmpty()){
            vo.setMainPic(new ArrayList<>());
            mainImageList.forEach(e->vo.getMainPic().add(mallConfig.getPicPrefix() + e.getUrl()));
        }

        List<Image> subImageList = queryImageListByMainId(vo.getId(),2);
        if(!subImageList.isEmpty()) {
            vo.setSubPic(new ArrayList<>());
            subImageList.forEach(e -> vo.getSubPic().add(mallConfig.getPicPrefix() + e.getUrl()));
        }
        if(StringUtils.isNotEmpty(productDetail.getUserId())) {
            Page<Cart> page = new Page<>(1, 100);
            IPage<CartProductBO> pageList = cartService.queryPageForUser(page, productDetail.getUserId());
            vo.setCartNum(pageList.getRecords().size());
        }
        result.setSuccess(true);
        result.setResult(vo);
        return result;
    }


    /**
     * 分页列表查询
     * @param reqInfo
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/listByCategory")
    public Result<List<ProductListVO>> queryPageListByCategory(ReqProductListByCategory reqInfo,
                                                               @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                               @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Result<List<ProductListVO>> result = new Result<>();
        Product product = new Product();
        if( reqInfo.getCategory()!=null){
            product.setCategoryId(Integer.parseInt(reqInfo.getCategory()));
        }
        product.setStatus(1);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>(product);
        reqInfo.getOrderType();
        if(StringUtils.isNotEmpty(reqInfo.getOrderType())) {
            if ("asc".equals(reqInfo.getSortType())) {
                //升序
                queryWrapper.orderByAsc(oConvertUtils.camelToUnderline(reqInfo.getOrderType()));
            } else {
                //降序
                queryWrapper.orderByDesc(oConvertUtils.camelToUnderline(reqInfo.getOrderType()));
            }
        }
        Page<Product> page = new Page<>(pageNo, pageSize);
        IPage<Product> pageList = productService.page(page, queryWrapper);
        result.setSuccess(true);
        List<ProductListVO> listVO = new ArrayList<>();
        for (Product productDO :pageList.getRecords()) {
            ProductListVO vo = new ProductListVO();
            BeanUtils.copyProperties(productDO, vo);
            List<Image> imageList = queryImageListByMainId(vo.getId(),1);
            if(imageList!=null && imageList.size()>0){
                String url = imageList.get(0).getUrl();
                vo.setPicUrl(mallConfig.getPicPrefix()+url);
            }
            listVO.add(vo);
        }

        result.setResult(listVO);
        return result;
    }


    /**
     * 通过业务ID和相应的图片类型获取图片
     * @param mainId
     * @param type
     * @return
     */
    private List<Image> queryImageListByMainId(String mainId,Integer type) {
        List<Image> result = null;
        if (mainId != null) {
            result = imageService.selectByBusinessIdAndType(mainId,type);
        }
        return result;
    }
}
