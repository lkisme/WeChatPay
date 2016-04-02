package com.kknotes.wechatpay.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.kknotes.wechatpay.exception.WeChatPayException;

/**
 * Created by kuikui on 3/17/16.
 */
public class UrlUtil {
    
	/**
	 * 
	 * @param url
	 * @param header
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
    public static String requestGetString(String url,Map<String, String> header, Map<String, String> parameters) throws Exception {
        StringBuilder ps = new StringBuilder();
        if(parameters != null && !parameters.isEmpty()){
            for (Map.Entry<String, String> entry :
                    parameters.entrySet()) {
                ps.append("&").append(entry.getKey()).append("=").append(entry.getValue());  
            }
            ps.replace(0,1,"?");
            
        }


        URL tokenUrl = new URL(url + ps.toString());

        HttpURLConnection connection = (HttpURLConnection) tokenUrl.openConnection();
        connection.setRequestMethod("GET");
        if(null != header){
            for (Map.Entry<String, String> entry :
                    header.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }


        int responseCode = connection.getResponseCode();

        if(200 != responseCode){
        	throw new WeChatPayException("返回码为：" + responseCode);
        }
        return new String(readInputStream(connection.getInputStream()));
    }

    public static String requestPostStringData(String url, String parameters) throws Exception {
        URL tokenUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) tokenUrl.openConnection();
        connection.setRequestMethod("POST");

        connection.setDoOutput(true);

        connection.getOutputStream().write(parameters.getBytes());


        int responseCode = connection.getResponseCode();

        if(200 != responseCode){
        	throw new WeChatPayException("返回码为：" + responseCode);
        }
        InputStream inStream=connection.getInputStream();
        return new String(readInputStream(inStream));
    }
    /**
     * 从输入流中读取数据
     * @param inStream
     * @return
     * @throws IOException 
     */
    public static byte[] readInputStream(InputStream inStream) throws IOException{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }
}
