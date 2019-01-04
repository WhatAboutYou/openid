package com.cabin.Utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2018/3/27.
 */
public class RequestWx {

    /**
     * get 方式
     * @param getUrl  请求的url
     * @return
     * @throws Exception
     * @throws IOException
     */
    public static JSONObject doGetWx(String getUrl) throws Exception{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(getUrl);
        JSONObject jsonObject = null;
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        if(entity != null) {
            String result = EntityUtils.toString(entity,"UTF-8");
            jsonObject = JSONObject.parseObject(result);
        }
        return jsonObject;
    }

    /**
     * post 方式
     * @param getUrl  请求的url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static JSONObject doPostWx(String getUrl) throws ClientProtocolException, IOException{
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(getUrl);
        JSONObject jsonObject = null;
        //httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
        HttpResponse httpResponse =  httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();
        if(entity != null) {
            String result = EntityUtils.toString(entity,"UTF-8");
            jsonObject = JSONObject.parseObject(result);
        }
        return jsonObject;
    }



}
