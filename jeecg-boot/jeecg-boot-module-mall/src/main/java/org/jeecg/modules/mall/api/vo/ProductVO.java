package org.jeecg.modules.mall.api.vo;

import lombok.Data;
import org.jeecg.modules.mall.entity.ProductDetail;

import java.util.List;

@Data
public class ProductVO {

    /**ID*/
    private String id;
    /**描述*/
    private String title;
    /**商品标题*/
    private String description;
    /**折扣价*/
    private Integer discountPrice;
    /**销售价*/
    private Integer sellingPrice;
    /**是否推荐:0-否,1-是*/
    private Integer isRecommend;
    /**是否热销:0-否,1-是*/
    private Integer isHot;
    /**是否新品:0-否,1-是*/
    private Integer isNew;
    /**是否免邮:0-否,1-是*/
    private Integer isFree;
    /**销量*/
    private Integer sells;
    private ProductDetail detail;

    private int cartNum;


    //主图
    private List<String> mainPic;
    private List<String> subPic;

}
