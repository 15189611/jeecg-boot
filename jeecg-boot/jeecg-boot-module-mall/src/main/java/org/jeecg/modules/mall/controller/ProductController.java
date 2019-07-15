package org.jeecg.modules.mall.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.mall.entity.Collection;
import org.jeecg.modules.mall.entity.Product;
import org.jeecg.modules.mall.entity.ProductDetail;
import org.jeecg.modules.mall.service.ICollectionService;
import org.jeecg.modules.mall.service.IProductDetailService;
import org.jeecg.modules.mall.service.IProductService;
import org.jeecg.modules.mall.vo.ProductPage;
import org.jeecg.modules.support.entity.Image;
import org.jeecg.modules.support.service.IImageService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
* @Description: 商品信息
* @Author: jeecg-boot
* @Date:   2019-06-03
* @Version: V1.0
*/
@RestController
@RequestMapping("/mall/product")
@Slf4j
public class ProductController {
   @Autowired
   private IProductService productService;
   @Autowired
   private IImageService imageService;
   @Autowired
   private IProductDetailService productDetailService;
   @Autowired
   private ICollectionService collectionService;

   /**
     * 分页列表查询
    * @param product
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @GetMapping(value = "/list")
   public Result<IPage<Product>> queryPageList(Product product,
                                     @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                     @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                     HttpServletRequest req) {
       Result<IPage<Product>> result = new Result<IPage<Product>>();
       QueryWrapper<Product> queryWrapper = QueryGenerator.initQueryWrapper(product, req.getParameterMap());
       Page<Product> page = new Page<Product>(pageNo, pageSize);
       IPage<Product> pageList = productService.page(page, queryWrapper);
       result.setSuccess(true);
       result.setResult(pageList);
       return result;
   }

   /**
     *   添加
    * @param productPage
    * @return
    */
   @PostMapping(value = "/add")
   public Result<Product> add(@RequestBody ProductPage productPage) {
       Result<Product> result = new Result<Product>();
       try {
           Product product = new Product();
           BeanUtils.copyProperties(productPage, product);

           if(product.getStatus()==1){
               //新增时是上架状态则设置上架时间，否则为空
               product.setOnShelvesTime(new Date());
           }

           productService.save(product);
           result.success("添加成功！");
       } catch (Exception e) {
           log.error(e.getMessage(),e);
           result.error500("操作失败");
       }
       return result;
   }

   /**
     *  编辑
    * @param productPage
    * @return
    */
   @PutMapping(value = "/edit")
   public Result<Product> edit(@RequestBody ProductPage productPage) {
       Result<Product> result = new Result<Product>();
       Product product = new Product();
       BeanUtils.copyProperties(productPage, product);
       Product productEntity = productService.getById(product.getId());
       if(productEntity==null) {
           result.error500("未找到对应实体");
       }else {
           if(productEntity.getOnShelvesTime()==null && product.getStatus()==1){
               product.setOnShelvesTime(new Date());
           }
           productService.updateById(product);
           result.success("修改成功!");
       }

       return result;
   }

   /**
     *   通过id删除
    * @param id
    * @return
    */
   @DeleteMapping(value = "/delete")
   public Result<Product> delete(@RequestParam(name="id",required=true) String id) {
       Result<Product> result = new Result<Product>();
       Product product = productService.getById(id);
       if(product==null) {
           result.error500("未找到对应实体");
       }else {
           productService.delMain(id);
           result.success("删除成功!");
       }

       return result;
   }

   /**
     *  批量删除
    * @param ids
    * @return
    */
   @DeleteMapping(value = "/deleteBatch")
   public Result<Product> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       Result<Product> result = new Result<Product>();
       if(ids==null || "".equals(ids.trim())) {
           result.error500("参数不识别！");
       }else {
           this.productService.removeByIds(Arrays.asList(ids.split(",")));
           result.success("删除成功!");
       }
       return result;
   }

   /**
     * 通过id查询
    * @param id
    * @return
    */
   @GetMapping(value = "/queryById")
   public Result<Product> queryById(@RequestParam(name="id",required=true) String id) {
       Result<Product> result = new Result<Product>();
       Product product = productService.getById(id);
       if(product==null) {
           result.error500("未找到对应实体");
       }else {
           result.setResult(product);
           result.setSuccess(true);
       }
       return result;
   }

   //===========================以下是子表信息操作相关API====================================

   /**
     * 通过主表id查询图片
    * @param mainId
    * @return
    */
   @GetMapping(value = "/listImageByMainId")
   public Result<List<Image>> queryImageListByMainId(@RequestParam(name="mainId",required=false) String mainId) {
       Result<List<Image>> result = new Result<List<Image>>();
       List<Image> imageList = null;
       if (mainId != null) {
           imageList = imageService.selectByBusinessId(mainId);
           result.setResult(imageList);
           result.setSuccess(true);
           return result;
       }else return null;
   }

   /**
    * 添加图片
    *
    * @param image
    * @return
    */
   @PostMapping(value = "/addImage")
   public Result<Image> addImage(@RequestBody Image image) {
       Result<Image> result = new Result<>();
       try {
           image.setBusinessType(2);//
           boolean ok = imageService.save(image);
           if (ok) {
               result.setSuccess(true);
               result.setMessage("添加图片成功.");
           } else {
               result.setSuccess(false);
               result.setMessage("添加图片失败!");
           }
           return result;
       } catch (Exception e) {
           e.fillInStackTrace();
           result.setSuccess(false);
           result.setMessage("添加图片过程中出现了异常: " + e.getMessage());
           return result;
       }
   }

   /**
    * 编辑图片
    *
    * @param image
    * @return
    */
   @PutMapping("/editImage")
   public Result<Image> editImage(@RequestBody Image image) {
       Result<Image> result = new Result<>();
       try {
           boolean ok = imageService.updateById(image);
           if (ok) {
               result.setSuccess(true);
               result.setMessage("更新图片成功.");
           } else {
               result.setSuccess(false);
               result.setMessage("更新图片失败!");
           }
           return result;
       } catch (Exception e) {
           result.setSuccess(false);
           result.setMessage("更新数据过程中出现异常啦: " + e.getMessage());
           return result;
       }
   }

   /**
    * 通过id删除图片
    *
    * @param id
    * @return
    */
   @DeleteMapping(value = "/deleteImage")
   public Result<Image> deleteImage(@RequestParam(name = "id", required = true) String id) {
       Result<Image> result = new Result<>();
       try {
           boolean ok = imageService.removeById(id);
           if (ok) {
               result.setSuccess(true);
               result.setMessage("删除图片成功.");
           } else {
               result.setSuccess(false);
               result.setMessage("删除图片失败!");
           }
           return result;
       } catch (Exception e) {
           result.setSuccess(false);
           result.setMessage("删除图片过程中出现异常啦: " + e.getMessage());
           return result;
       }
   }

   /**
    * 批量删除图片
    *
    * @param ids
    * @return
    */
   @DeleteMapping(value = "/deleteBatchImage")
   public Result<Image> deleteBatchImage(@RequestParam(name = "ids", required = true) String ids) {
       Result<Image> result = new Result<Image>();
       if (ids == null || "".equals(ids.trim())) {
           result.error500("参数不识别！");
       } else {
           this.imageService.removeByIds(Arrays.asList(ids.split(",")));
           result.success("删除成功!");
       }
       return result;
   }

   /**
     * 通过主表id查询收藏信息
    * @param mainId
    * @return
    */
   @GetMapping(value = "/listCollectionByMainId")
   public Result<List<Collection>> queryCollectionListByMainId(@RequestParam(name="mainId",required=false) String mainId) {
       Result<List<Collection>> result = new Result<List<Collection>>();
       List<Collection> collectionList = null;
       if (mainId != null) {
           collectionList = collectionService.selectByMainId(mainId);
           result.setResult(collectionList);
           result.setSuccess(true);
           return result;
       }else return null;
   }

   /**
    * 添加收藏信息
    *
    * @param collection
    * @return
    */
   @PostMapping(value = "/addCollection")
   public Result<Collection> addCollection(@RequestBody Collection collection) {
       Result<Collection> result = new Result<>();
       try {
           boolean ok = collectionService.save(collection);
           if (ok) {
               result.setSuccess(true);
               result.setMessage("添加收藏信息成功.");
           } else {
               result.setSuccess(false);
               result.setMessage("添加收藏信息失败!");
           }
           return result;
       } catch (Exception e) {
           e.fillInStackTrace();
           result.setSuccess(false);
           result.setMessage("添加收藏信息过程中出现了异常: " + e.getMessage());
           return result;
       }
   }

   /**
    * 编辑收藏信息
    *
    * @param collection
    * @return
    */
   @PutMapping("/editCollection")
   public Result<Collection> editCollection(@RequestBody Collection collection) {
       Result<Collection> result = new Result<>();
       try {
           boolean ok = collectionService.updateById(collection);
           if (ok) {
               result.setSuccess(true);
               result.setMessage("更新收藏信息成功.");
           } else {
               result.setSuccess(false);
               result.setMessage("更新收藏信息失败!");
           }
           return result;
       } catch (Exception e) {
           result.setSuccess(false);
           result.setMessage("更新数据过程中出现异常啦: " + e.getMessage());
           return result;
       }
   }

   /**
    * 通过id删除收藏信息
    *
    * @param id
    * @return
    */
   @DeleteMapping(value = "/deleteCollection")
   public Result<Collection> deleteCollection(@RequestParam(name = "id", required = true) String id) {
       Result<Collection> result = new Result<>();
       try {
           boolean ok = collectionService.removeById(id);
           if (ok) {
               result.setSuccess(true);
               result.setMessage("删除收藏信息成功.");
           } else {
               result.setSuccess(false);
               result.setMessage("删除收藏信息失败!");
           }
           return result;
       } catch (Exception e) {
           result.setSuccess(false);
           result.setMessage("删除收藏信息过程中出现异常啦: " + e.getMessage());
           return result;
       }
   }

   /**
    * 批量删除收藏信息
    *
    * @param ids
    * @return
    */
   @DeleteMapping(value = "/deleteBatchCollection")
   public Result<Collection> deleteBatchCollection(@RequestParam(name = "ids", required = true) String ids) {
       Result<Collection> result = new Result<Collection>();
       if (ids == null || "".equals(ids.trim())) {
           result.error500("参数不识别！");
       } else {
           this.collectionService.removeByIds(Arrays.asList(ids.split(",")));
           result.success("删除成功!");
       }
       return result;
   }

    /**
     * 通过主表id查询商品详细信息
     * @param mainId
     * @return
     */
    @GetMapping(value = "/listProductDetailByMainId")
    public Result<List<ProductDetail>> queryProductDetailListByMainId(@RequestParam(name="mainId",required=false) String mainId) {
        Result<List<ProductDetail>> result = new Result();
        List<ProductDetail> productDetailList = new ArrayList<>();
        if (mainId != null) {
            ProductDetail detail = productDetailService.selectByMainId(mainId);
            productDetailList.add(detail);
            result.setResult(productDetailList);
            result.setSuccess(true);
            return result;
        }else return null;
    }
    /**
     * 添加商品详细信息
     *
     * @param productDetail
     * @return
     */
    @PostMapping(value = "/addProductDetail")
    public Result<ProductDetail> addProductDetail(@RequestBody ProductDetail productDetail) {
        Result<ProductDetail> result = new Result<>();
        try {
            boolean ok = productDetailService.save(productDetail);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("添加商品详细信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("添加商品详细信息失败!");
            }
            return result;
        } catch (Exception e) {
            e.fillInStackTrace();
            result.setSuccess(false);
            result.setMessage("添加商品详细信息过程中出现了异常: " + e.getMessage());
            return result;
        }
    }

    /**
     * 编辑商品详细信息
     *
     * @param productDetail
     * @return
     */
    @PutMapping("/editProductDetail")
    public Result<ProductDetail> editProductDetail(@RequestBody ProductDetail productDetail) {
        Result<ProductDetail> result = new Result<>();
        try {
            boolean ok = productDetailService.updateById(productDetail);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("更新商品详细信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("更新商品详细信息失败!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("更新数据过程中出现异常啦: " + e.getMessage());
            return result;
        }
    }

    /**
     * 通过id删除商品详细信息
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteProductDetail")
    public Result<ProductDetail> deleteProductDetail(@RequestParam(name = "id", required = true) String id) {
        Result<ProductDetail> result = new Result<>();
        try {
            boolean ok = productDetailService.removeById(id);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("删除商品详细信息成功.");
            } else {
                result.setSuccess(false);
                result.setMessage("删除商品详细信息失败!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("删除商品详细信息过程中出现异常啦: " + e.getMessage());
            return result;
        }
    }

    /**
     * 批量删除商品详细信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatchProductDetail")
    public Result<ProductDetail> deleteBatchProductDetail(@RequestParam(name = "ids", required = true) String ids) {
        Result<ProductDetail> result = new Result<ProductDetail>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.productDetailService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }


   /**
    * 导出excel
    *
    * @param request
    * @param response
    */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
       // Step.1 组装查询条件
       QueryWrapper<Product> queryWrapper = null;
       try {
           String paramsStr = request.getParameter("paramsStr");
           if (oConvertUtils.isNotEmpty(paramsStr)) {
               String deString = URLDecoder.decode(paramsStr, "UTF-8");
               Product product = JSON.parseObject(deString, Product.class);
               queryWrapper = QueryGenerator.initQueryWrapper(product, request.getParameterMap());
           }
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
       //Step.2 AutoPoi 导出Excel
       ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
       List<ProductPage> pageList = new ArrayList<ProductPage>();
       List<Product> productList = productService.list(queryWrapper);
       for (Product product : productList) {
           ProductPage vo = new ProductPage();
           BeanUtils.copyProperties(product, vo);
           List<Image> imageList = imageService.selectByBusinessId(product.getId());
           vo.setImageList(imageList);
           List<Collection> collectionList = collectionService.selectByMainId(product.getId());
           vo.setCollectionList(collectionList);
           pageList.add(vo);
       }
       //导出文件名称
       mv.addObject(NormalExcelConstants.FILE_NAME, "商品信息列表");
       mv.addObject(NormalExcelConstants.CLASS, ProductPage.class);
       mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品信息列表数据", "导出人:Jeecg", "导出信息"));
       mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
       return mv;
   }

   /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
   public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
       Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
       for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
           MultipartFile file = entity.getValue();// 获取上传文件对象
           ImportParams params = new ImportParams();
           params.setTitleRows(2);
           params.setHeadRows(1);
           params.setNeedSave(true);
           try {
               List<ProductPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ProductPage.class, params);
               for (ProductPage page : list) {
                   Product po = new Product();
                   BeanUtils.copyProperties(page, po);
                   productService.saveMain(po, page.getImageList(),page.getCollectionList());
               }
               return Result.ok("文件导入成功！数据行数:" + list.size());
           } catch (Exception e) {
               log.error(e.getMessage(),e);
               return Result.error("文件导入失败:"+e.getMessage());
           } finally {
               try {
                   file.getInputStream().close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
       return Result.ok("文件导入失败！");
   }
}
