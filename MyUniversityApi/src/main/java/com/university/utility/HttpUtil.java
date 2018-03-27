package com.university.utility;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.university.event.UniversityEvent;

public class HttpUtil {

	public static int sendPost(String url, UniversityEvent newKpEvent) {

		HttpHeaders detailsTokenHeader = new HttpHeaders();
		detailsTokenHeader.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(newKpEvent, detailsTokenHeader);
		RestTemplate rest = new RestTemplate();
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
			return 200;
		}
		catch (HttpClientErrorException ce) {
			if (ce.getStatusCode().equals(HttpStatus.CONFLICT)) {
				System.out.println("conflict");
				return 409;
			}
			if (ce.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				System.out.println("bad request");
				return 400;
			}
		}
		catch (HttpServerErrorException es) {
			if (es.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
				System.out.println("server error");
				return 500;
			}
			if (es.getStatusCode().equals(HttpStatus.NOT_IMPLEMENTED)) {
				return 501;
			}
		}
		catch (Exception e) {
			return 500;
		}
		return 0;
	}
}
