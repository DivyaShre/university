package com.un.consumer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.client.HttpServerErrorException;


/**
 * 
 * @author raghunath
 *
 */

public class HttpMultipartUtil {

	/**
	 * 
	 * @param url
	 * @param userId
	 * @param token
	 * @param filePath
	 * @return ServiceResponse
	 */
	public static ServiceResponse uploadFile(String url, List<String> fileTempLocationList, Map<String, String> otherParams) {
		
		ServiceResponse serviceResponse = new ServiceResponse();
		try{
			
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost uploadFile = new HttpPost(url);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			Iterator it = otherParams.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
				builder.addTextBody(pair.getKey().toString(), pair.getValue().toString(), ContentType.TEXT_PLAIN);
		        it.remove();
		    }
		    File fileObj = null;
		    for(String filePath : fileTempLocationList){
		    	
		    	fileObj = new File(filePath);
				builder.addBinaryBody("file", new FileInputStream(fileObj), ContentType.APPLICATION_OCTET_STREAM,
						fileObj.getName());
		    }
	
			HttpEntity multipart = builder.build();
			uploadFile.setEntity(multipart);
			CloseableHttpResponse postResponse = httpClient.execute(uploadFile);
			int responseStatus = postResponse.getStatusLine().getStatusCode();
			if(responseStatus == 200){
				HttpEntity responseEntity = postResponse.getEntity();
				InputStream in = responseEntity.getContent();
		
				BufferedReader bufferedReader = null;
				bufferedReader = new BufferedReader(new InputStreamReader(in));
		
				String responseData = "";
				while ((responseData = bufferedReader.readLine()) != null) {
					serviceResponse.setData(responseData);
				}		
				serviceResponse.setStatus(200);
			}else{
				serviceResponse.setData("Error while uploading file "); 
				serviceResponse.setStatus(500);
			}
		}
	
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.SC_INTERNAL_SERVER_ERROR)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.SC_NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		}
		catch (Exception e) {
			serviceResponse.setData("Error while uploading file "+e.getMessage()); 
			serviceResponse.setStatus(500);
		}

		return serviceResponse;

	}
	
	/**
	 * 
	 * @param url
	 * @param userId
	 * @param token
	 * @param filePath
	 * @return ServiceResponse
	 */
	public static ServiceResponse sendPost(String url, String userId, String token,String filePath) {
		ServiceResponse serviceResponse = new ServiceResponse();
		try{
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost uploadFile = new HttpPost(url);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("userId", userId, ContentType.TEXT_PLAIN);
		builder.addTextBody("token", token, ContentType.TEXT_PLAIN);

		// This attaches the file to the POST:
		File f = new File(filePath);

		builder.addBinaryBody("file", new FileInputStream(f), ContentType.APPLICATION_OCTET_STREAM,
				f.getName());

		HttpEntity multipart = builder.build();
		uploadFile.setEntity(multipart);
		CloseableHttpResponse postResponse = httpClient.execute(uploadFile);
		HttpEntity responseEntity = postResponse.getEntity();
		InputStream in = responseEntity.getContent();

		BufferedReader bufferedReader = null;
		bufferedReader = new BufferedReader(new InputStreamReader(in));

		String responseData = "";
		while ((responseData = bufferedReader.readLine()) != null) {
			serviceResponse.setData(responseData);
		}		
		serviceResponse.setStatus(200);
		}
	
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.SC_INTERNAL_SERVER_ERROR)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.SC_NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		}
		catch (Exception e) {
			serviceResponse.setData("Error while uploading file "+ filePath+ " "+e.getMessage()); 
			serviceResponse.setStatus(500);
		}

		return serviceResponse;

	}
	
	/**
	 * Send file download request to KPApi
	 * @param url
	 * @param req
	 * @param res
	 */
	public static void sendDownloadRequest(String url,HttpServletRequest req,
			HttpServletResponse res) {
		try{
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet uploadFile = new HttpGet(url);
		//call to api
		CloseableHttpResponse postResponse = httpClient.execute(uploadFile);
		HttpEntity responseEntity = postResponse.getEntity();
			
		//add returned headers to response headers
		for (Header header : postResponse.getAllHeaders()){
			res.setHeader(header.getName(), header.getValue());
		}
		//write request stream to response 
		responseEntity.writeTo(res.getOutputStream());
		//set status returned by api
		res.setStatus(postResponse.getStatusLine().getStatusCode());
		
		}
	
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.SC_INTERNAL_SERVER_ERROR)) {
				res.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.SC_NOT_IMPLEMENTED)) {
				res.setStatus(501);
			}
		}
		catch (Exception e) {
			res.setStatus(500);
		}

	}
	
}
