package com.idsmanager.rp.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idsmanager.rp.util.HttpClientUtils;

@Controller("testController")
public class TestController {

	@RequestMapping(value="/v1/test/{test}")
	@ResponseBody
	public String test(@PathVariable String test){
		HttpClient hc = HttpClientUtils.warpClient();
		String url ="http://192.168.1.8:8080/FIDOServer/rp/RegisterRequest/"+test;
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse resp =hc.execute(get);
			HttpEntity enty =resp.getEntity();
			InputStream is = enty.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String backMessage = br.readLine();
			return backMessage;
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return test;
	}
}
