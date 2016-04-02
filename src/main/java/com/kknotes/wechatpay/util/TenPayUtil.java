package com.kknotes.wechatpay.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by kuikui on 3/17/16.
 */
public class TenPayUtil {

    /**
     * 获取签名
     * @param payInfo
     * @return
     * @throws Exception
     */
    public static String getSign(TenPayInfo payInfo)  {
        String signTemp = "appid="+payInfo.getAppid()
                +"&attach="+payInfo.getAttach()
                +"&body="+payInfo.getBody()
                +"&device_info="+payInfo.getDevice_info()
                +"&detail=" +  payInfo.getDetail()
                +"&limit_pay=no_credit"
                +"&mch_id="+payInfo.getMch_id()
                +"&nonce_str="+payInfo.getNonce_str()
                +"&notify_url="+payInfo.getNotify_url()
                +"&openid="+payInfo.getOpenid()
                +"&out_trade_no="+payInfo.getOut_trade_no()
                +"&spbill_create_ip="+payInfo.getSpbill_create_ip()
                +"&time_expire=" + payInfo.getTime_expire()
                +"&total_fee="+payInfo.getTotal_fee()
                +"&trade_type="+payInfo.getTrade_type()
                +"&key="+TenPayConfig.KEY; 

        String sign = Util.caculateMD5HashOfString(signTemp).toUpperCase();
      
        return sign;
    }


    /**
     *
     * @param xml
     * @return
     */
    public static Map<String, String> parseXml(String xml) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if(null == xml){
            return map;
        }
        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        @SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();
        for (Element e : elementList){
            map.put(e.getName(), e.getText());
        }

        return map;
    }

    public static boolean checkWeChatReturnData(Map<String,String> returnData){
        if(returnData != null &&
                returnData.containsKey("result_code") &&
                returnData.containsKey("appid") &&
                returnData.containsKey("mch_id") &&
                returnData.containsKey("sign") &&
                "SUCCESS".equals(returnData.get("result_code"))){
            return true;
        }
        return false;
    }

    /**
     * 
     * @param params
     * @return blank string if params is null
     */
    public static String getSignature(Map<String, String> params){
        if(params == null){
            return "";
        }
        List<String> keys = new ArrayList<>();
        keys.addAll(params.keySet());
        keys.sort((o1 ,o2) -> {
            int index = 0;
            int length1 = o1.length();
            int length2 = o2.length();
            int res = 0;
            do{
                if(index < length1 && index < length2){
                    int intO1 = o1.charAt(index);
                    int intO2 = o2.charAt(index);
                    res = intO1 - intO2;
                }else{
                    res = length1 - length2;
                }
                ++index;
            }while (res == 0);
            return  res;

        });

        StringBuilder paramString = new StringBuilder();
        for (Iterator<String> iter = keys.iterator()
             ; iter.hasNext();) {
            String name = (String) iter.next();
            String valueStr = params.get(name);


            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if(name.equals("sign")){
            }else{
                paramString.append("&" + name + "=" + valueStr);
            }

        }
        paramString.append("&key=" + TenPayConfig.KEY);
        return Util.caculateMD5HashOfString(paramString.substring(1)).toUpperCase();
    }
}
