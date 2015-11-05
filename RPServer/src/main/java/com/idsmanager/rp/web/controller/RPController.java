package com.idsmanager.rp.web.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.idsmanager.rp.domain.AuthenticateRequestData;
import com.idsmanager.rp.domain.AuthenticateResponseData;
import com.idsmanager.rp.domain.RegisterRequestData;
import com.idsmanager.rp.domain.RegisterResponseData;
import com.idsmanager.rp.util.HttpClientUtils;
import com.idsmanager.rp.util.bean.ProcessResult;

@Controller("testController")
public class RPController {
	/**
	 * 注册请求
	 * thinkpad dushaofei
	 * @param test
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/v1/RegisterRequest/{username}")
	@ResponseBody
	public ProcessResult<RegisterRequestData> test(@PathVariable String username){//注册请求
		ProcessResult<RegisterRequestData> pr = new ProcessResult<RegisterRequestData>();
		String url ="http://192.168.1.8:8080/FIDOServer/rp/RegisterRequest/"+username;
		HttpGet get = new HttpGet(url);
		try {   
			JSONObject json = getJsonObject(get);
			pr =   (ProcessResult<RegisterRequestData>) JSONObject.toBean(json, pr.getClass());
			}catch(Exception e){
				System.out.println("注册请求异常:"+e.getMessage());
				e.printStackTrace();
			}
		return pr;
	}
	/**
	 * 注册响应
	 * thinkpad dushaofei
	 * @param clientData
	 * @param registData
	 * @param address
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value="/v1/RegisterReponse")
	@ResponseBody
	public ProcessResult<Integer> RegistResponse(@RequestBody RegisterResponseData responseData,HttpServletRequest address) throws UnsupportedEncodingException{
		ProcessResult<Integer> pr = new ProcessResult<Integer>();
		String url = "http://192.168.1.8:8080/FIDOServer/rp/RegisterResponse";
		HttpUriRequest request = new HttpPost(url);
		JSONObject obj = new JSONObject();
		obj.accumulate("clientData", responseData.getClientData());
		obj.accumulate("registrationData", responseData.getRegistrationData());
		StringEntity entity = new StringEntity(obj.toString(),HTTP.UTF_8);
		((HttpPost)request).setEntity(entity);
		try { 
			JSONObject jsonObj = getJsonObject(request);
			pr = (ProcessResult<Integer>) jsonObj.toBean(jsonObj,pr.getClass());
		} catch (ClientProtocolException e) { 
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pr;
	}
	/**
	 * 验证请求
	 * thinkpad dushaofei
	 * @param username
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value="/v1/AuthenticateRequest")
	@ResponseBody
	public ProcessResult<AuthenticateRequestData>AuthRequest(@PathVariable String username ){ 
		ProcessResult<AuthenticateRequestData> pr = new ProcessResult<AuthenticateRequestData>();
		String url = "http://192.168.1.8:8080/FIDOServer/rp/AuthenticateRequest/"+username;
		HttpGet get = new HttpGet(url);
		try {
			JSONObject jsonObj = getJsonObject(get);
			pr = (ProcessResult<AuthenticateRequestData>) jsonObj.toBean(jsonObj, pr.getClass());
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return pr;
	}
	/**
	 * 验证响应
	 * thinkpad dushaofei
	 * @param clientData
	 * @param signatureData
	 * @param keyHandle
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value="/v1/test/AuthenticateResponse")
	public ProcessResult<Integer>AuthResponse(@RequestBody AuthenticateResponseData responseData){
		ProcessResult<Integer> pr = new ProcessResult<Integer>();
		String url = "http://192.168.1.8:8080/FIDOServer/rp/AuthenticateResponse";
		HttpUriRequest post = new HttpPost(url);
		JSONObject jsonP = new JSONObject();
		jsonP.accumulate("clientData", responseData.getClientData());
		jsonP.accumulate("signatureData", responseData.getSignatureData());
		jsonP.accumulate("keyHandle", responseData.getKeyHandle());
		StringEntity entity;
		try {
			entity = new StringEntity(jsonP.toString(),HTTP.UTF_8);
			((HttpPost)post).setEntity(entity);
			JSONObject jsonObj = getJsonObject(post);
			pr = (ProcessResult<Integer>) jsonObj.toBean(jsonObj, pr.getClass());
			return pr;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		}
		System.out.println(pr.getErrorNum());
		return pr;
	}
	/**
	 * 执行GET请求
	 * thinkpad dushaofei
	 * @param get
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject getJsonObject(HttpGet get) throws ClientProtocolException, IOException{
		HttpClient hc = HttpClientUtils.warpClient();
		HttpResponse resp =hc.execute(get);
		HttpEntity enty =resp.getEntity();
		InputStream is = enty.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String backMessage = br.readLine();
		JSONObject json = JSONObject.fromObject(backMessage);
		return json;
	}
	/**
	 * 执行POST请求
	 * thinkpad dushaofei
	 * @param request
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject getJsonObject(HttpUriRequest post) throws ClientProtocolException, IOException{
		post.setHeader("Content-Type","application/json;charset=UTF-8");
		HttpClient hc = HttpClientUtils.warpClient();
		HttpResponse response = hc.execute(post);
		HttpEntity Respentity = response.getEntity();
		InputStream is = Respentity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String backMessage = br.readLine();
		JSONObject jsonObj = JSONObject.fromObject(backMessage);
		return jsonObj;
	}
	@SuppressWarnings({ "unchecked", "static-access" })
	public static void main(String[] args) throws ClientProtocolException, IOException {
//		ProcessResult<RegisterRequestData> pr = new ProcessResult<RegisterRequestData>();
//		String url ="http://192.168.1.8:8080/FIDOServer/rp/RegisterRequest/"+"shaofei";
//		HttpGet get = new HttpGet(url);
//		try {   
//			JSONObject json = getJsonObject(get);
//			pr =   (ProcessResult<RegisterRequestData>) JSONObject.toBean(json,  pr.getClass());
//			System.out.println(pr.getErrorNum()+"+"+pr.getErrorMessage()+"+"+pr.getSuccess()+pr.getDetail());
//		}  catch (IOException e) {
//			e.printStackTrace();
//		}
		//注册响应
//		ProcessResult<Integer> pr = new ProcessResult<Integer>();
//		String clientData="";
//		String registData = "";
//		String url = "http://192.168.1.8:8080/FIDOServer/rp/RegisterResponse";
//		HttpUriRequest request = new HttpPost(url);
//		clientData = "eyJjaGFsbGVuZ2UiOiIwXzI1VjJXMFRyWkdfNy1ZS0tBZXNiOUZodTBRWWJHdUc3WUItZ1ZQa29FIiwib3JpZ2luIjoiaHR0cDovL21lLmlkc21hbmFnZXIuY29tL0ZJRE9TZXJ2ZXIiLCJ0eXAiOiJuYXZpZ2F0b3IuaWQuZmluaXNoRW5yb2xsbWVudCJ9";
//		registData = "BQTVcBof2jtG-Ajk-uQA9n4QYP59--mK09QGUkWJDyeDPfNTwbLaOtk2VVDB_bOWTNWQh-QEowyplZUYw-FH9vqbAAAAAAAAAAAAAAAAAAA\u003d";
//		JSONObject obj = new JSONObject();
//		obj.accumulate("clientData", clientData);
//		obj.accumulate("registrationData", registData);
//		StringEntity entity = new StringEntity(obj.toString(),HTTP.UTF_8);
//		((HttpPost)request).setEntity(entity);
//		try {
//			JSONObject jsonObj = getJsonObject(request);
//			pr = (ProcessResult<Integer>) jsonObj.toBean(jsonObj,pr.getClass());
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		//验证请求

//		ProcessResult<AuthenticateRequestData> pr = new ProcessResult<AuthenticateRequestData>();
//		String url = "http://192.168.1.8:8080/FIDOServer/rp/AuthenticateRequest/"+"yalin";
//		String backMessager = "";
//		HttpGet get = new HttpGet(url);
//		try {
//			JSONObject jsonObj = getJsonObject(get);
//			pr = (ProcessResult<AuthenticateRequestData>) jsonObj.toBean(jsonObj, pr.getClass());
//			
//			System.out.println(pr.getDetail());
//		} catch (ClientProtocolException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
	//验证注册
		String clientData = "eyJjaGFsbGVuZ2UiOiJ4YjMxeG1DQ1ZNbDVpcTN1SDNFR1JkQzlrWEZ2YmJZaHY1ZTN4LW1VQ3FzIiwib3JpZ2luIjoiaHR0cDovLzE5Mi4xNjguMS4xMTYvRklET1NlcnZlciIsInR5cCI6Im5hdmlnYXRvci5pZC5nZXRBc3NlcnRpb24ifQ";
		String signatureData ="AQAAAAEwRgIhAODp8v3pEdKMQ4tpzalxlB8L5_vBOPtB-H8CjIfitCbaAiEA5FKoaVf4VaWeZWNftYDuKPBFOqCxOSAFE1QoTMZsshw=";
		String keyHandle = "iqSj1IaVHRx3zIms7w55SipzqIkhLlmoiOQh2xILekWhY2ko6uJp1KSaZ5TYkEyPBXtT6RElSD9TP-mi9IADyy6mJhc7vpxEHrSyn24KXGdfnBCNrk2g3Mt1Ph8-FzlyNaDYASVVDZwQqrRCPwOQhnAS_rRGacAfOvmiciAWZmhKmX2AamRrFHfkXh4ETktBKyqtbk0U7jkYgOp57fJAIdwiQYRd_x8DZGtfPAsBhx7sbyM15ZpONg";
		ProcessResult<Integer> pr = new ProcessResult<Integer>();
		String url = "http://192.168.1.8:8080/FIDOServer/rp/AuthenticateResponse";
		HttpUriRequest post = new HttpPost(url);
		JSONObject jsonP = new JSONObject();
		jsonP.accumulate("clientData", clientData);
		jsonP.accumulate("signatureData", signatureData);
		jsonP.accumulate("keyHandle", keyHandle);
		StringEntity entity = new StringEntity(jsonP.toString(),HTTP.UTF_8);
		((HttpPost)post).setEntity(entity);
		JSONObject jsonObj = getJsonObject(post);
		pr = (ProcessResult<Integer>) jsonObj.toBean(jsonObj, pr.getClass());
		System.out.println(pr.getErrorNum());
	}
}
