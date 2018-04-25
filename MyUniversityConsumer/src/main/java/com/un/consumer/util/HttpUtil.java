package com.un.consumer.util;

import java.io.ByteArrayInputStream;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {

	public static ServiceResponse sendPost(String url, String urlParameters, String authkey) {
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.APPLICATION_JSON);

		detailsTokenHeader.set("Authorization", "Bearer " + authkey);
		detailsTokenHeader.add("Accept", "application/json;charset=utf-8");
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(urlParameters, detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
			serviceResponse.setData(responseEntity.getBody());
			serviceResponse.setStatus(200);
		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				serviceResponse.setStatus(400);
			}
			if (ce.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
			{
				System.out.println("unauthrized.Session expired");
				serviceResponse.setStatus(Constants.HTTP_STATUS_CODE_SESSION_EXPIRED);
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		}
		catch (Exception e) {

			serviceResponse.setStatus(500);
		}

		return serviceResponse;

	}

	public static ServiceResponse sendPostForLoging(String url, String userName, String password) {
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			HttpHeaders detailsTokenHeader = new HttpHeaders();
			detailsTokenHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			detailsTokenHeader.set("Authorization", "Basic Y2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=");

			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			RestTemplate rest = new RestTemplate(factory);
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("client_id", "clientIdPassword");
			map.add("username", userName);
			map.add("password", password);
			map.add("grant_type", "password");
			HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(map, detailsTokenHeader);

			ResponseEntity<String> responseEntity = null;

			responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
			serviceResponse.setData(responseEntity.getBody());
			serviceResponse.setStatus(200);

		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				serviceResponse.setStatus(400);
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				System.out.println("server error");
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		}
		catch (Exception e) {
			System.out.println(e);
			serviceResponse.setStatus(500);
		}

		return serviceResponse;
	}

	public static ServiceResponse sendPut(String url, String urlParameters, String authkey) {
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.APPLICATION_JSON);

		detailsTokenHeader.set("Authorization", "Bearer " + authkey);
		detailsTokenHeader.add("Accept", "application/json;charset=utf-8");
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(urlParameters, detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.PUT, httpEntity, String.class);
			serviceResponse.setStatus(200);
		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				serviceResponse.setStatus(400);
			}
			if (ce.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
			{
				System.out.println("unauthrized.Session expired");
				serviceResponse.setStatus(Constants.HTTP_STATUS_CODE_SESSION_EXPIRED);
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				System.out.println("server error");
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		}
		catch (Exception e) {

			serviceResponse.setStatus(500);
		}

		return serviceResponse;

	}

	public static ServiceResponse sendGet(String url, String authkey) {
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.APPLICATION_JSON);

		detailsTokenHeader.set("Authorization", "Bearer " + authkey);
		detailsTokenHeader.add("Accept", "application/json;charset=utf-8");
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.GET, httpEntity, String.class);
			serviceResponse.setData(responseEntity.getBody());
			serviceResponse.setStatus(200);
		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				serviceResponse.setStatus(400);
			}
			if (ce.getStatusCode().equals(HttpStatus.REQUEST_TIMEOUT)) {
				System.out.println("request time out");
				serviceResponse.setData(ce.getResponseBodyAsString());
				serviceResponse.setStatus(408);
			}
			if (ce.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
			{
				System.out.println("unauthorized.Session expired");
				serviceResponse.setStatus(Constants.HTTP_STATUS_CODE_SESSION_EXPIRED);
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				System.out.println("server error");
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		}
		catch (Exception e) {

			serviceResponse.setStatus(500);
		}

		return serviceResponse;

	}

	public static ServiceResponse sendGetWithoutToken(String url) {
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.TEXT_PLAIN);

		detailsTokenHeader.add("Accept", "text/html;charset=utf-8");
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.GET, httpEntity, String.class);
			serviceResponse.setData(responseEntity.getBody());
			serviceResponse.setStatus(200);
		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				serviceResponse.setStatus(400);
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {

				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
			if (es.getStatusCode().toString().equals("510")) {

				serviceResponse.setStatus(606);
			}
		}
		catch (Exception e) {

			serviceResponse.setStatus(500);
		}

		return serviceResponse;

	}

	public static BinaryServiceResponse sendGetForBinaryData(String url, String authkey) {
		BinaryServiceResponse serviceResponse = new BinaryServiceResponse();
		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.APPLICATION_JSON);

		detailsTokenHeader.set("Authorization", "Bearer " + authkey);
		detailsTokenHeader.add("Accept", "text/csv;charset=utf-8");
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<byte[]> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
			serviceResponse.setData(new ByteArrayInputStream(responseEntity.getBody()));
			serviceResponse.setStatus(200);
			serviceResponse.setHeaders(responseEntity.getHeaders());
		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				serviceResponse.setStatus(400);
			}
			if (ce.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
				System.out.println("not found");
				serviceResponse.setStatus(404);
			}
			if (ce.getStatusCode().equals(HttpStatus.METHOD_NOT_ALLOWED)) {
				System.out.println("method not allowed");
				serviceResponse.setStatus(405);
			}
			if (ce.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
			{
				System.out.println("unauthrized.Session expired");
				serviceResponse.setStatus(Constants.HTTP_STATUS_CODE_SESSION_EXPIRED);
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				System.out.println("server error");
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().toString().equals("510")) {
				System.out.println("sourse not found");
				serviceResponse.setStatus(606);
			}

		}
		catch (Exception e) {

			serviceResponse.setStatus(500);

		}

		return serviceResponse;

	}

	public static ServiceResponse sendDelete(String url, String authkey) {
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.APPLICATION_JSON);

		detailsTokenHeader.set("Authorization", "Bearer " + authkey);
		detailsTokenHeader.add("Accept", "application/json;charset=utf-8");
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
			serviceResponse.setData(responseEntity.getBody());
			serviceResponse.setStatus(200);
		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				serviceResponse.setStatus(400);
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				System.out.println("server error");
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		}
		catch (Exception e) {

			serviceResponse.setStatus(500);
		}

		return serviceResponse;

	}

	public static ServiceResponse sendGetWithExtraParam(String url,
			String authKey, String userAdditionalInfo) {
		
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.APPLICATION_JSON);

		detailsTokenHeader.set("Authorization", "Bearer " + authKey);
		detailsTokenHeader.add("Accept", "application/json;charset=utf-8");
		detailsTokenHeader.add("userAdditionalInfo", userAdditionalInfo);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(
				detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.GET, httpEntity,
					String.class);
			serviceResponse.setData(responseEntity.getBody());
			serviceResponse.setStatus(200);
		} catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				serviceResponse.setStatus(400);
			}
			if (ce.getStatusCode().equals(HttpStatus.REQUEST_TIMEOUT)) {
				System.out.println("request time out");
				serviceResponse.setData(ce.getResponseBodyAsString());
				serviceResponse.setStatus(408);
			}
		} catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				System.out.println("server error");
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		} catch (Exception e) {

			serviceResponse.setStatus(500);
		}
		return serviceResponse;
	}
	
	public static ServiceResponse sendPutWithExtraParam(String url, String urlParameters, String authkey, 
			String userAdditionalInfo) {
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.APPLICATION_JSON);

		detailsTokenHeader.set("Authorization", "Bearer " + authkey);
		detailsTokenHeader.add("Accept", "application/json;charset=utf-8");
		detailsTokenHeader.add("userAdditionalInfo", userAdditionalInfo);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(urlParameters, detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.PUT, httpEntity, String.class);
			serviceResponse.setStatus(200);
		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				serviceResponse.setStatus(400);
			}
			if (ce.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
			{
				System.out.println("unauthrized.Session expired");
				serviceResponse.setStatus(Constants.HTTP_STATUS_CODE_SESSION_EXPIRED);
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				System.out.println("server error");
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		}
		catch (Exception e) {

			serviceResponse.setStatus(500);
		}
		return serviceResponse;
	}

	public static ServiceResponse sendPostWithExtraParam(String url, String urlParameters, String authkey, 
			String userAdditionalInfo) {
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.APPLICATION_JSON);

		detailsTokenHeader.set("Authorization", "Bearer " + authkey);
		detailsTokenHeader.add("Accept", "application/json;charset=utf-8");
		detailsTokenHeader.add("userAdditionalInfo", userAdditionalInfo);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(urlParameters, detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
			serviceResponse.setData(responseEntity.getBody());
			serviceResponse.setStatus(200);
		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				serviceResponse.setStatus(400);
			}
			if (ce.getStatusCode().equals(HttpStatus.UNAUTHORIZED))
			{
				System.out.println("unauthrized.Session expired");
				serviceResponse.setStatus(Constants.HTTP_STATUS_CODE_SESSION_EXPIRED);
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		}
		catch (Exception e) {

			serviceResponse.setStatus(500);
		}
		return serviceResponse;
	}

	public static ServiceResponse sendDeleteWithExtraParam(String url, String authkey, 
			String userAdditionalInfo) {
		ServiceResponse serviceResponse = new ServiceResponse();
		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.APPLICATION_JSON);

		detailsTokenHeader.set("Authorization", "Bearer " + authkey);
		detailsTokenHeader.add("Accept", "application/json;charset=utf-8");
		detailsTokenHeader.add("userAdditionalInfo", userAdditionalInfo);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.DELETE, httpEntity, String.class);
			serviceResponse.setData(responseEntity.getBody());
			serviceResponse.setStatus(200);
		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				serviceResponse.setStatus(409);
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				serviceResponse.setStatus(400);
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				System.out.println("server error");
				serviceResponse.setStatus(500);
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {
				serviceResponse.setData(es.getResponseBodyAsString());
				serviceResponse.setStatus(501);
			}
		}
		catch (Exception e) {

			serviceResponse.setStatus(500);
		}

		return serviceResponse;

	}
	
}
