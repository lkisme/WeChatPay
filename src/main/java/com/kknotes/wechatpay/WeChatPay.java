package com.kknotes.wechatpay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kknotes.wechatpay.exception.WeChatPayException;
import com.kknotes.wechatpay.util.TenPayConfig;
import com.kknotes.wechatpay.util.TenPayConstants;
import com.kknotes.wechatpay.util.TenPayInfo;
import com.kknotes.wechatpay.util.TenPayUtil;
import com.kknotes.wechatpay.util.UrlUtil;

/**
 * see SDK of wechat in http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
 * 
 * @author kuikui
 *
 */
public class WeChatPay {
	private static final int LOWEST_WECHAT_PAY_VERSION_NO = 5;
	private static final String STATE_STRING = "lk";
	
	/**
	 * 用户点击链接：https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
	 * 
	 * 之后的跳转，判断是否可以支付
	 * 
	 * @param userAgent value of "user-agent" in request header
	 * @param code  parameter from WeChat server
	 * @param state parameter from WeChat server
	 * @return the whole result that openid is included as string.
	 * @throws WeChatPayException 
	 */
	public static String getWeChatInfo(String userAgent, String code, String state) throws WeChatPayException{

        System.out.println("in get wechat info");
        Pattern userAgentPattern = Pattern.compile("MicroMessenger/(\\d+).(\\d+).(\\d+)");

        Matcher matcher = userAgentPattern.matcher(userAgent);

        if(!matcher.find()){
        	throw new WeChatPayException("不是微信客户端");
        }else{
        	Integer majorVersion = Integer.valueOf(matcher.group(1));

            if( majorVersion < LOWEST_WECHAT_PAY_VERSION_NO){
            	throw new WeChatPayException("微信版本过低");
            }
        }
        
        if(!STATE_STRING.equals(state)){
        	throw new WeChatPayException("state字段不正确");
        }
        if(code == null || code.isEmpty()){
        	throw new WeChatPayException("code字段不正确");
        }
        HashMap<String,String> ps = new HashMap<String, String>();
        ps.put("appid", TenPayConfig.APPID);
        ps.put("secret", TenPayConfig.SECRET);
        ps.put("code", code);
        ps.put("grant_type","authorization_code");
        
        try {
			return UrlUtil.requestGetString(TenPayConstants.OAUTH_URL, null, ps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WeChatPayException(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WeChatPayException(e.getMessage());
		}
        
	}
	
	/**
	 * 
	 * 用户点击链接：https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
	 * 
	 * 之后的跳转，判断是否可以支付
	 * @param userAgent value of "user-agent" in request header
	 * @param code  parameter from WeChat server
	 * @param state parameter from WeChat server
	 * @return openid
	 * @throws WeChatPayException
	 */
	public static String getOpenID(String userAgent, String code, String state) throws WeChatPayException{
		
        try {
			String resultString = getWeChatInfo(userAgent, code, state);
			String openidKey = "\"openid\":\"";
			int indexOfOpenID = resultString.indexOf(openidKey);
			boolean isError = resultString.contains("errcode");
			if(indexOfOpenID > 0 && !isError){
				int endOfOpenID = resultString.indexOf("\"", indexOfOpenID + openidKey.length());
				return resultString.substring(indexOfOpenID + openidKey.length(), endOfOpenID);
			}
			throw new WeChatPayException("从微信返回结果中找不到openid:" + resultString);

		} catch (Exception e) {
			throw new WeChatPayException(e.getMessage());
		}
        
	}
	
	/**
	 * 
	 * @param order
	 * @param ip
	 * @param openid
	 * @param tradeType
	 * @return value of ("prepay_id=" + prepay_id, e,g, prepay_id=wx0984029385093869435243)
	 * @throws WeChatPayException
	 */
	public static String unifiedPay(WeChatOrderInfo order, String ip, String openid, String tradeType) throws WeChatPayException{
		
        TenPayInfo info = TenPayInfo.createPayInfo(order,ip,openid, tradeType);
        
        if(order == null){
        	throw new WeChatPayException("参数不能为空");
        }else if(openid == null){
        	throw new WeChatPayException("用户未授权或者授权已过期,请重新点击购买链接");
        }else if(ip == null){
        	throw new WeChatPayException("缺少参数: IP" );
        }
        
        try {
            String returnData = UrlUtil.requestPostStringData(TenPayConstants.weChatUnifyOrder,info.toXML());
            
            Map<String,String> result = TenPayUtil.parseXml(returnData);
            if(TenPayUtil.checkWeChatReturnData(result)){
                String prePayId = "prepay_id=" + result.get("prepay_id");
                return prePayId;
            }else{
            	//this exception will always be thrown if no else here
            	throw new WeChatPayException("返回XML格式错误");
            }
        } catch (Exception e) {
        	throw new WeChatPayException(e.getMessage());
            
        }
	}
}
