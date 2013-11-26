package com.android.speedsearch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtil{
	
	private static String convertStreamToString(InputStream is){
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line  = null;
		while(true){
			try{
				line = reader.readLine();
				if(line == null){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return sb.toString();
				}else{
					sb = new StringBuilder().append(line).append("\n");
				}
			}catch (IOException e) {
				e.printStackTrace();
				try{
					is.close();
				}catch (IOException e1) {
					e1.printStackTrace();
				}
				return sb.toString();
			}

		}
	}
	public String doGet(String url){
	    String  data = null;
	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    HttpGet httpGet = new HttpGet(url);
	    try{
	      HttpResponse httpResponse = httpClient.execute(httpGet);
	      HttpEntity httpEntity = httpResponse.getEntity();
	      if (httpEntity != null){
	    	  data = convertStreamToString(httpEntity.getContent());
	      }
	    }catch (UnknownHostException eUnknownHostException){
	    }catch (SocketException eSocketException){
	    }catch (ClientProtocolException eClientProtocolException){
	        eClientProtocolException.printStackTrace();
	    }catch (IOException eIOException){
	        eIOException.printStackTrace();
	    }
	    return data;
	}
}