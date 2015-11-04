package com.idsmanager.rp.util;

import java.security.KeyStore;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class HttpClientUtils {
	public static synchronized  HttpClient warpClient(){
		HttpClient client = new DefaultHttpClient();
		 try {
			 
             KeyStore trustStore = KeyStore.getInstance(KeyStore
                     .getDefaultType());
             trustStore.load(null, null);
             SSLSocketFactory sf = new SSLSocketFactoryNew(trustStore);
             sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  //鍏佽鎵�湁涓绘満鐨勯獙璇�

             HttpParams params = new BasicHttpParams();

             HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
             HttpProtocolParams.setContentCharset(params,
                     HTTP.DEFAULT_CONTENT_CHARSET);
             HttpProtocolParams.setUseExpectContinue(params, true);

             // 璁剧疆杩炴帴绠＄悊鍣ㄧ殑瓒呮椂
             ConnManagerParams.setTimeout(params, 10000);
             // 璁剧疆杩炴帴瓒呮椂
             HttpConnectionParams.setConnectionTimeout(params, 10000);
             // 璁剧疆socket瓒呮椂
             HttpConnectionParams.setSoTimeout(params, 10000);

             // 璁剧疆http https鏀寔
             SchemeRegistry schReg = new SchemeRegistry();
             
             schReg.register(new Scheme("https", sf, 443));
             
             ClientConnectionManager conm = client.getConnectionManager();
             
             conm.getSchemeRegistry().register(new Scheme("https", sf, 443));

         
             client= new DefaultHttpClient(conm, params);
         } catch (Exception e) {
             e.printStackTrace();
             return new DefaultHttpClient();
         }
		 
		 
		 
		 return client;
     }
    
}
