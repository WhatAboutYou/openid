package com.cabin.Controller;

import com.alibaba.fastjson.JSONObject;
import com.cabin.Utils.Constant;
import com.cabin.Utils.RequestWx;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by fjc on 2018-08-21.
 */
@RequestMapping(value = "/wx")
@RestController
public class WeixinController{
    public static Logger logger = Logger.getLogger(WeixinController.class);

    private String state  = "";

    /**
     * 用户注册,获取微信code
     * @param url
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getWxOpenId",method = RequestMethod.GET)
    public synchronized void getWxOpenId(String url, HttpServletResponse response) throws Exception{
        logger.info("参数URL:"+url);
        String wxurl = Constant.WX_GETCODE.replace("APPID",Constant.WX_APPID)
                .replace("REDIRECT_URI", URLEncoder.encode("http://wscan.cabin-tech.com/wx/getWxCodeCallBack","utf-8"))
                                .replace("SCOPE","snsapi_base");
        state = url;
                                //.replace("STATE",URLEncoder.encode(url,"UTF-8"));
        logger.info("参数WXURL:"+wxurl);
        response.sendRedirect(wxurl);
    }


    /**
     * 获取微信code时,已回调的形式返回
     *  {"code":"xxxx"}
     * @return
     */
    @RequestMapping(value = "/getWxCodeCallBack",method = RequestMethod.GET)
    public synchronized void getWxCodeCallBack(String code/*,String state*/,HttpServletResponse response) throws Exception{
        logger.info("code=" +code);
        logger.info("state=" +state);
        //通过code获取access_token
        String url2 = Constant.WX_GETACCESSTOKEN.replace("APPID",Constant.WX_APPID).replace("SECRET",Constant.WX_APPSECRET).replace("CODE",code);
        logger.info("url2=" +url2);
        JSONObject json2 =  RequestWx.doGetWx(url2);
        logger.info("json2=" +json2);
        String openid = json2.get("openid").toString();
        logger.info("openid=" +openid);
        if(state.indexOf("?")!=-1){
            response.sendRedirect(state+"&openid="+openid);
        }else{
            response.sendRedirect(state+"?openid="+openid);
        }
    }

}
