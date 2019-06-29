package org.jeecg.common.util;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jeecg.common.config.WeChatConfig;
import org.jeecg.common.util.wechat.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 微信帮助类
 */
@Slf4j
@Component
public class WeChatUtils {
//
//    @Autowired
//    private static SysAccessTokenService sysAccessTokenService;

    @Autowired
    private WeChatConfig weChatConfig;

    private static WeChatUtils weChatUtils;


    @PostConstruct
    public void init() {
        weChatUtils = this;
        weChatUtils.weChatConfig =this.weChatConfig;

    }




    public static String getAccessToken(){
//        SysAccessTokenEntity  accessTokenEntity = sysAccessTokenService.queryLastAccessToken();
//        if(accessTokenEntity != null){
//            if(checkAccessToken(accessTokenEntity.getAccessToken())){
//                return accessTokenEntity.getAccessToken();
//            }
//        }


        String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+weChatUtils.weChatConfig.getAppId()+"&secret="+weChatUtils.weChatConfig.getSecret();
        String responseString = HttpClientUtil.doGet(url);

        Map resultMap =  JSONObject.parseObject(responseString,Map.class);
        System.out.println(resultMap.values());
        log.debug(resultMap.values().toString());
        if(resultMap!=null){
            if(resultMap.get("errcode")!=null){
                return "";
            }
        }
        String accessToke = (String )resultMap.get("access_token");
        int expires = (int)resultMap.get("access_token");

//        addToken(accessToke,expires);


        return accessToke;


    }

    public static boolean checkAccessToken(String accessToken){

        if (StringUtils.isBlank(accessToken)) {
            return false;
        }
        String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="+accessToken;
        String responseString = HttpClientUtil.doGet(url);

        Map resultMap = JSONObject.parseObject(responseString,Map.class);
        if (resultMap.get("errcode")!=null&& resultMap.get("errcode")!=null) {
            return false;
        }
        return true;
    }


    public static Map getOpenId(String code) {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+weChatUtils.weChatConfig.getAppId()+
                "&secret="+weChatUtils.weChatConfig.getSecret()+"&js_code="+code+"&grant_type=authorization_code";
        String responseString = HttpClientUtil.doGet(url);
        Map resultMap =JSONObject.parseObject(responseString,Map.class);
        return resultMap;
    }

    /**
     * 解密数据
     * @return
     * @throws Exception
     */
    public static String decrypt( String encryptedData, String sessionKey, String iv){

        String result = "";
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                result = new String(WxPKCS7Encoder.decode(resultByte));
                JSONObject jsonObject = JSONObject.parseObject(result,JSONObject.class);//.fromObject(result);
                String decryptAppid = jsonObject.getJSONObject(WeChatConstant.WATERMARK).getString(WeChatConstant.APPID);
                if(!weChatUtils.weChatConfig.getAppId().equals(decryptAppid)){
                    result = "";
                }
            }
        } catch (Exception e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    public String getQrcode(String video_id, String user_id){

        return "";
    }

//    private static void addToken(String accessToke,int expires){
//        SysAccessTokenEntity sysAccessToken = new SysAccessTokenEntity();
//        sysAccessToken.setAccessToken(accessToke);
//        sysAccessToken.setExpires(expires);
//        sysAccessToken.setCreatetime(new Date());
//        sysAccessTokenService.save(sysAccessToken);
//    }


    /**
     * 方法描述：根据签名加密请求参数
     * 创建时间：2017年6月8日  上午11:28:52
     * 作者： xubo
     *
     * @param
     * @return
     */
    public static String arraySign(Map<Object, Object> params, String paySignKey) {
        boolean encode = false;
        Set<Object> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = value.toString();
            }
            if (encode) {
                try {
                    temp.append(URLEncoder.encode(valueString, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                temp.append(valueString);
            }
        }
        temp.append("&key=");
        temp.append(paySignKey);
        System.out.println(temp.toString());
        String packageSign = MD5.getMessageDigest(temp.toString());
        return packageSign;
    }


    /**
     * 请求，只请求一次，不做重试
     *
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    public static String requestOnce(final String url, String data) throws Exception {
        BasicHttpClientConnectionManager connManager;
        connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(10000).build();

        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        //TODO
//        httpPost.addHeader("User-Agent", "wxpay sdk java v1.0 " + ResourceUtil.getConfigByName("wx.mchId"));
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        String result = EntityUtils.toString(httpEntity, "UTF-8");
        log.info("请求结果:" + result);
        return result;

    }
}
