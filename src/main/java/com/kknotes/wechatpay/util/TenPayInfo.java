package com.kknotes.wechatpay.util;

/**
 * Created by kuikui on 3/17/16.
 * 统一支付接口获取pre pay id
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.kknotes.wechatpay.WeChatOrderInfo;

public class TenPayInfo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appid;
    private String mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String body;
    private String attach;
    private String out_trade_no;
    private int total_fee;
    private String spbill_create_ip;
    private String notify_url;
    private String trade_type;
    private String limit_pay = "no_credit";
    private String time_expire ;
    private String openid;
    private String detail;
    //下面是get,set方法


    /**
     * 创建统一下单的xml的java对象
     * @param bizOrder 系统中的业务单号
     * @param ip 用户的ip地址
     * @param openId 用户的openId
     * @return
     */
    public static TenPayInfo createPayInfo(WeChatOrderInfo bizOrder, String ip, String openId, String tradeType) {
        TenPayInfo payInfo = new TenPayInfo();
        payInfo.setAppid(TenPayConfig.APPID);
        payInfo.setDevice_info("WEB");
        payInfo.setMch_id(TenPayConfig.MCH_ID);
        payInfo.setNonce_str(CodeGenerator.generateRandomString(16));
        payInfo.setBody(bizOrder.getBody());
        String attach = bizOrder.getAttach();
        String detail = bizOrder.getDetail();
        payInfo.setAttach((attach == null || attach.isEmpty()) ? "blank":attach);
        payInfo.setDetail(detail == null || detail.isEmpty() ? "blank":detail);
        payInfo.setOut_trade_no(bizOrder.getOut_trade_no());
        payInfo.setTotal_fee(bizOrder.getTotal_fee().multiply(BigDecimal.valueOf(100)).intValue());
        payInfo.setSpbill_create_ip(ip);
        payInfo.setNotify_url(TenPayConfig.notifyUri);
        payInfo.setTrade_type(tradeType);
        payInfo.setOpenid(openId);
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR_OF_DAY, TenPayConfig.EXPIRE_HOUR);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String expireTime = simpleDateFormat.format(now.getTime());
        payInfo.time_expire = expireTime;
        payInfo.setSign(TenPayUtil.getSign(payInfo));

        return payInfo;
    }

    /**
     *
     * @return
     */
    public String toXML(){
        StringBuilder sb = new StringBuilder("<xml>");
        sb.append("<appid>" + appid +"</appid>");
        sb.append("<detail>" + detail +"</detail>");
        sb.append("<mch_id>" + mch_id +"</mch_id>");
        sb.append("<device_info>" + device_info +"</device_info>");
        sb.append("<nonce_str>" + nonce_str +"</nonce_str>");
        sb.append("<sign>" + sign +"</sign>");
        sb.append("<body>" + body +"</body>");
        sb.append("<attach>" + attach +"</attach>");
        sb.append("<out_trade_no>" + out_trade_no +"</out_trade_no>");
        sb.append("<total_fee>" + total_fee +"</total_fee>");
        sb.append("<spbill_create_ip>" + spbill_create_ip +"</spbill_create_ip>");
        sb.append("<notify_url>" + notify_url +"</notify_url>");
        sb.append("<out_trade_no>" + out_trade_no +"</out_trade_no>");
        sb.append("<limit_pay>" + limit_pay +"</limit_pay>");
        sb.append("<openid>" + openid +"</openid>");
        sb.append("<time_expire>" + time_expire + "</time_expire>");
        sb.append("<trade_type>" + trade_type + "</trade_type>");
        sb.append("</xml>");
        return sb.toString();
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

	public String getLimit_pay() {
		return limit_pay;
	}

	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
