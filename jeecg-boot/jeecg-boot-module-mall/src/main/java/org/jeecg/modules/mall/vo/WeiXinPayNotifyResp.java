package org.jeecg.modules.mall.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 
 * @author Administrator
 *
 */
@XStreamAlias("xml")
@Data
public class WeiXinPayNotifyResp {
	private String return_code ;//返回状态码
	private String return_msg ;//返回信息
}