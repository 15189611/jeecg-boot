package org.jeecg.modules.mall.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 商品详细信息
 * @Author: jeecg-boot
 * @Date:   2019-07-13
 * @Version: V1.0
 */
@Data
@TableName("t_shop_product_detail")
public class ProductDetail implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.UUID)
	private String id;
	/**productId*/
	private String productId;
	/**brand*/
    @Excel(name = "brand", width = 15)
	private String brand;
	/**level*/
    @Excel(name = "level", width = 15)
	private String level;
	/**pack*/
    @Excel(name = "pack", width = 15)
	private String pack;
	/**shape*/
    @Excel(name = "shape", width = 15)
	private String shape;
	/**season*/
    @Excel(name = "season", width = 15)
	private String season;
	/**specialty*/
    @Excel(name = "specialty", width = 15)
	private String specialty;
	/**kind*/
    @Excel(name = "kind", width = 15)
	private String kind;
	/**madeIn*/
    @Excel(name = "madeIn", width = 15)
	private String madeIn;
	/**净含量*/
    @Excel(name = "净含量", width = 15)
	private Integer weight;
	/**year*/
    @Excel(name = "year", width = 15)
	private String year;
}
