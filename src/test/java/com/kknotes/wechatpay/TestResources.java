package com.kknotes.wechatpay;

import org.junit.Assert;
import org.junit.Test;

import com.kknotes.wechatpay.util.TenPayConfig;

public class TestResources {

	@Test
	public void testResource(){
		Assert.assertNotNull(TenPayConfig.APPID);
	}
}
