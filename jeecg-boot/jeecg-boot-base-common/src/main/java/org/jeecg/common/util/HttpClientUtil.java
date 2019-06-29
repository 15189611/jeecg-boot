package org.jeecg.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class HttpClientUtil {

    private static final String ContentEncoding = "UTF-8";
    private static final int SocketTimeout = 5000;

    private static String sessionId="";


    /**
     * httpClient的get请求方式
     * @author yeqing
     * @date 2017-11-27
     * @return
     * @throws Exception
     */
    public static String doGet(String url)   {
        log.info("==================================doGet=={}",url);

        HttpClient httpClient = new HttpClient();
        // 设置 Http 连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(SocketTimeout);
        GetMethod getMethod = new GetMethod(url);
        getMethod.setRequestHeader("Referer","https://xueqiu.com/S/SH600206");
        // 设置 get 请求超时为 5 秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SocketTimeout);
        // 设置请求重试处理，用的是默认的重试处理：请求三次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        String response = "";

        try {
            int statusCode = httpClient.executeMethod(getMethod);
            log.info("==================================doGet=={}",statusCode);

            if (!isSuccess(statusCode)) {
                log.error("请求出错: "+ getMethod.getStatusLine());
                return response;
            }

            byte[] responseBody = getMethod.getResponseBody();// 读取为字节数组
            response = new String(responseBody, ContentEncoding);
            log.info("----------response:" + response);
            // 读取为 InputStream，在网页内容数据量大时候推荐使用
            // InputStream response = getMethod.getResponseBodyAsStream();
        } catch (HttpException e) {
            log.error("请检查输入的URL!");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("发生网络异常!");
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return response;
    }

    public static String doGetWithSession(String urlStr) throws IOException{
        String key = "";
        String cookieVal = "";
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if(!sessionId.equals("")){
            connection.setRequestProperty("Cookie", sessionId);
        }
        connection.connect(); //到此步只是建立与服务器的tcp连接，并未发送http请求
        /**
         * 设置cookie
         */

        for(int i=1;(key=connection.getHeaderField(i))!=null;i++){
            cookieVal = connection.getHeaderField(i);
            cookieVal = cookieVal.substring(0,cookieVal.indexOf(";")>-1?cookieVal.indexOf(";"):cookieVal.length()-1);
            sessionId = sessionId + cookieVal + ";";
        }

        //直到getInputStream()方法调用请求才真正发送出去
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line=br.readLine()) != null){
            sb.append(line);
            sb.append("\n");
        }
        String result = sb.toString();

        log.info("====================doPost:{}====result:{}",urlStr,result);
        br.close();
        connection.disconnect();
        return  result;
    }



    /**
     * HttpClient PUT请求
     * @author yeqing
     * @date 2017-11-27
     * @return
     */
    public static String doPut(String uri,String jsonObj){
        log.info("==================================doPut=={},{}",uri,jsonObj);

        String resStr = "";
        HttpClient htpClient = new HttpClient();
        PutMethod putMethod = new PutMethod(uri);
        putMethod.addRequestHeader( "Content-Type","application/json" );
        putMethod.getParams().setParameter( HttpMethodParams.HTTP_CONTENT_CHARSET, ContentEncoding );
        putMethod.setRequestBody( jsonObj );
        try{
            int statusCode = htpClient.executeMethod( putMethod );
            log.info("==================================doPut=={}",statusCode);
            if (!isSuccess(statusCode)) {
                log.error("Method failed: "+putMethod.getStatusLine() );
                return resStr;
            }
            byte[] responseBody = putMethod.getResponseBody();
            resStr = new String(responseBody,ContentEncoding);
            log.info("----------response:" + resStr);
        }catch(Exception e){
            log.error(" failed: " + e.getMessage());
            e.printStackTrace();
        }finally{
            putMethod.releaseConnection();
        }
        return resStr;
    }

    /**
     * post请求
     * @author yeqing
     * @date 2017-11-27
     * @param url
     * @param jsonObj
     * @return
     */
    public static String doPost(String url,String jsonObj){
        log.info("HttpClientUtil.doPost=={},{}",url,jsonObj);

        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", "application/json; charset=UTF-8");
        String response = "";
        try {
            StringEntity stringEntity = new StringEntity( jsonObj,ContentEncoding);
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(stringEntity);
            HttpResponse res = client.execute(post);
            int statusCode=res.getStatusLine().getStatusCode();
            log.info("==================================doPost=={}",statusCode);
            if (!isSuccess(statusCode)) {
                log.error("Method failed: "+res.getStatusLine() );
                return response;
            }
            response = EntityUtils.toString(res.getEntity());// 返回json格式：
            log.info("HttpClientUtil.doPost response:" + response);
        } catch (Exception e) {
            log.error("HttpClientUtil.doPost Exception: " + e.getMessage());
        }
        return response;
    }

    /**
     * @author yeqing
     * @date 2017-11-27
     * @param uri
     * @return
     */
    public static String doDelete(String uri)  {
        log.info("==================================doDelete=={}",uri);

        String data= "";
        HttpClient httpClient= new HttpClient();
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, ContentEncoding);
        DeleteMethod method = null;
        try{
            method= new DeleteMethod();
            method.setURI(new URI(uri,false));
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, SocketTimeout);
            int statusCode = httpClient.executeMethod(method);
            log.info("==================================doDelete=={}",statusCode);
            if (!isSuccess(statusCode)) {
                log.error("Method failed: " + method.getStatusLine());
                return data;
            }
            data= new String(method.getResponseBody(),ContentEncoding);
            log.info("----------response:" + data);
        }catch(HttpException e){
            e.printStackTrace();
            log.error("Please check your provided http address!");
        }catch(IOException e){
            e.printStackTrace();
            log.error(e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }finally{
            if(method!=null)
                method.releaseConnection();
        }
        return data;
    }

    private static boolean isSuccess(int statusCode){
        boolean result = true;
        if (statusCode != HttpStatus.SC_OK&&statusCode != HttpStatus.SC_CREATED&&statusCode != HttpStatus.SC_NO_CONTENT) {
            result =false;
        }
        return result;
    }
}
