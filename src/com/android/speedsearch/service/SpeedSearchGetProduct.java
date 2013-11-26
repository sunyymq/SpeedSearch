package com.android.speedsearch.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;

import com.android.speedsearch.util.ProductUtil;

public class SpeedSearchGetProduct{
	public static ProductUtil getProductInfo(String string1, String string2, String string3, String string4, String string5, String string6, int int1, int int2, boolean boolean1, boolean boolean2){
		StringBuffer stringBuffer = new StringBuffer();
	    stringBuffer.append("http://www.gwdang.com");
	    stringBuffer.append("/app/search/?format=json&s_product=");
		try{
			stringBuffer.append(URLEncoder.encode(string1, "UTF-8"));
		}catch(UnsupportedEncodingException e){
			return null;
		}
		if ((string2 != null) && (!string2.equals(""))){
			stringBuffer.append("&s_review=");
			try{
				stringBuffer.append(URLEncoder.encode(string2, "UTF-8"));
			}catch(UnsupportedEncodingException e1){
				return null;
			}
		}
		if ((string3 != null) && (!string3.equals(""))){
			stringBuffer.append("&class_id=" + string3);
		}
		if ((string4 != null) && (!string4.equals(""))){
			stringBuffer.append("&brand=");
			try{
				stringBuffer.append(URLEncoder.encode(string4, "GBK"));
			}catch(UnsupportedEncodingException e2){
				return null;
			}
		}
		stringBuffer.append("&price_st=");
		if ((string5 != null) && (!string5.equals(""))){
			stringBuffer.append(string5);
		}
		stringBuffer.append("&price_end=");
		if ((string6 != null) && (!string6.equals(""))){
			stringBuffer.append(string6);
		}
		stringBuffer.append("&pg=");
	    stringBuffer.append(int1);
	    stringBuffer.append("&ps=");
	    stringBuffer.append(10);
		if (boolean1){
			stringBuffer.append("&scanner=1");
		}else{
			stringBuffer.append("&scanner=0");
		}
		if (!boolean2){
			stringBuffer.append("&_stock=0-1");
		}
		switch(int2){
			case 1:
				stringBuffer.append("&fs=review_cnt&fa=0");
				break;
			case 2:
				stringBuffer.append("&fs=star_level&fa=0&ss=star_rev_cnt&sa=0");
				break;
			case 3:
				stringBuffer.append("&fs=avg_price&fa=2");
				break;
			case 4:
				stringBuffer.append("&fs=avg_price&fa=0");
				break;
		}
		HttpGet localHttpGet = new HttpGet(stringBuffer.toString());
		Long localLong1 = Long.valueOf(System.currentTimeMillis());
		new StringBuilder("## ·¢³öËÑË÷ÇëÇóv1.1  ").append(string1).append(stringBuffer).toString();
		DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
		localDefaultHttpClient.getParams().setIntParameter("http.socket.timeout", 600000);
		localDefaultHttpClient.getParams().setIntParameter("http.connection.timeout", 60000);
		InputStream localInputStream;
		Long localLong2;
		try{
			HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
			if (localHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				new StringBuilder("##1.1 search get time = ").append(System.currentTimeMillis() - localLong1.longValue()).toString();
				localLong2 = Long.valueOf(System.currentTimeMillis());
				localInputStream = localHttpResponse.getEntity().getContent();
			}else{
				localDefaultHttpClient.getConnectionManager().shutdown();
				return null;
			}
			if(localInputStream == null){
				localDefaultHttpClient.getConnectionManager().shutdown();
				return null;					
			}
			BufferedInputStream localBufferedInputStream = new BufferedInputStream(localInputStream, 1024);
			ProductUtil locall = new ProductUtil(localBufferedInputStream);
            localBufferedInputStream.close();
            new StringBuilder("##1.1 search parse time = ").append(System.currentTimeMillis() - localLong2.longValue()).toString();
            localDefaultHttpClient.getConnectionManager().shutdown();
            return locall;
		}catch(ClientProtocolException e){
			localDefaultHttpClient.getConnectionManager().shutdown();
			return null;			
		}catch(ConnectTimeoutException e){
			localDefaultHttpClient.getConnectionManager().shutdown();
			return null;			
		}catch(SocketTimeoutException e){
			localDefaultHttpClient.getConnectionManager().shutdown();
			return null;			
		}catch(InterruptedIOException e){
			localDefaultHttpClient.getConnectionManager().shutdown();
			return null;			
		}catch(IOException e){
			localDefaultHttpClient.getConnectionManager().shutdown();
			return null;		
		}
	}
}