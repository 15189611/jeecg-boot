package org.jeecg.modules.mall.api.vo;

import lombok.Data;

/**
 * @Description: 收获地址
 * @Author: jeecg-boot
 * @Date:   2019-06-22
 * @Version: V1.0
 */
@Data
public class ReqAddAddress {

	private String action;
	private String userId;
//	private Integer isDefault;
	private String consignee;
	private String mobile;
	private String zipCode;
	private String provinceCode;
	private String cityCode;
	private String districtCode;
	private String address;
	private String addressId;

}
