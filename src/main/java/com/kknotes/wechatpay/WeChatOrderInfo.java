package com.kknotes.wechatpay;

import java.math.BigDecimal;

public class WeChatOrderInfo {

	private BigDecimal total_fee;
	private String body;
	private String detail;
	private String attach;
	private String out_trade_no;
	
	private WeChatOrderInfo(){
		
	}
	
	public static WeChatOrderInfo getInstance(String body, BigDecimal total_fee, String out_trade_no, String detail, String attach ){
		WeChatOrderInfo order = new WeChatOrderInfo();
		if(null == body || null == total_fee || null == out_trade_no){
			return null;
		}
		order.body = body;
		order.total_fee = total_fee;
		order.out_trade_no = out_trade_no;
		order.attach = attach;
		order.detail = detail;
		return order;
	}
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public BigDecimal getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
	
	
}
