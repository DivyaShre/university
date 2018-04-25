/**
 * 
 */
package com.un.consumer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.json.JSONArray;
import org.json.JSONObject;

public class CommonUtility {
	static Properties msgProp = null;
	private static final String ALPHA_NUMERIC_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	
	public static boolean isNullEmptyString(String str) {
		if (null == str) {
			return false;
		}
		else if ("".equalsIgnoreCase(str.trim())) {
			return false;
		}
		return true;
	}

	public static void writeResponse(HttpServletResponse response, String responseParam, int statusCode) {
		try {
			responseParam = URLEncoder.encode(responseParam, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			responseParam = "";
		}
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "application/x-www-form-urlencoded");
		response.addIntHeader("Content-Length", responseParam.getBytes().length);
		response.setStatus(statusCode);
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			if (writer != null) {
				writer.write(responseParam);
				writer.flush();
			}
		}
		catch (IOException e) {
		}
		finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public static void writeResponseHLS(HttpServletResponse response, String responseParam, int statusCode) {
		/*
		 * try { responseParam = URLEncoder.encode(responseParam, "UTF-8"); } catch
		 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
		 * responseParam = ""; }
		 */
		// response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "binary/octet-stream");
		response.addIntHeader("Content-Length", responseParam.getBytes().length);
		response.setStatus(statusCode);

		try {
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(responseParam.getBytes());
			outputStream.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String readMessageForKey(String key) {

		return msgProp.getProperty(key);
	}

	public static JSONObject readInputStream(HttpServletRequest req) {
		// Read the stream and decode the JSON string to JSON Object
		InputStream is;
		StringBuffer response = new StringBuffer();
		JSONObject reqObj = new JSONObject();
		try {
			is = req.getInputStream();

			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;

			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			reqObj = new JSONObject(response.toString());
		}
		catch (Exception e) {
		}
		return reqObj;
	}
	
	public static JSONArray readArrayInputStream(HttpServletRequest req) {
		// Read the stream and decode the JSON string to JSON Object
		InputStream is;
		StringBuffer response = new StringBuffer();
		JSONArray reqObj = new JSONArray();
		try {
			is = req.getInputStream();

			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;

			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			reqObj = new JSONArray(response.toString());
		}
		catch (Exception e) {
		}
		return reqObj;
	}

	public static JSONObject readInputStreamWithUTF8(HttpServletRequest request) {
		// Read the stream and decode the JSON string to JSON Object
		JSONObject reqObj = new JSONObject();
		try {
			InputStream is = request.getInputStream();
			String jsonString = IOUtils.toString(is, "UTF-8");
			// System.out.println("myString"+jsonString);
			reqObj = new JSONObject(jsonString);

		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reqObj;
	}

	public static String convertToDate(long time, String dateFormat) {

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return new SimpleDateFormat(dateFormat).format(c.getTime());
	}

	public static void displayPaymentResult(HttpServletResponse response, String responseMsg) {
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			if (writer != null) {
				writer.write("<html><head><title>Payment Status</title></head><body><center><div><h3>" + responseMsg
						+ "</h3></div></center></body></html>");
				writer.flush();
			}
		}
		catch (IOException e) {
		}
	}

	public static String convertPasswordHash(String passwordHash) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		}
		catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String text = passwordHash;

		try {
			md.update(text.getBytes("UTF-8"));
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} // Change this to "UTF-16" if needed
		byte[] mdbytes = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		System.out.println("hex" + sb.toString());
		return sb.toString();
	}

	public static JSONObject readJSONFromFile(File file) throws IOException {
		String fileContent = FileUtils.readFileToString(file);

		JSONObject fileJSONObj = new JSONObject(fileContent);
		return fileJSONObj;
	}
	
	public static void isSessionActive(HttpServletResponse response ,String access_token){
		if(access_token==null){
			CommonUtility.writeResponse(response,"",Constants.HTTP_STATUS_CODE_SESSION_EXPIRED);
		}
	}
	
	/**
	 * generates the random alphanumeric characters based on count parameter
	 * @param count
	 * @return
	 */
	public static String randomAlphaNumeric(int count) {

		StringBuilder builder = new StringBuilder();

		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	/**
	 * Getting String body from the respective template file
	 * @param templateName,name of the template file
	 * @param paramsMap, params required for the template file
	 * @return string , Final body of the mail
	 * @throws Exception
	 */
	public static String getMsgTextWithTemplate(String templateName, Map<String, String> paramsMap) throws Exception {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		Template template = ve.getTemplate("templates/" + templateName + ".vm");
		VelocityContext context = new VelocityContext();

		Set set = paramsMap.entrySet();
		Iterator i = set.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			context.put(me.getKey().toString(), me.getValue().toString());
		}

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		return writer.toString();
	}
	public static void main(String[] args) {
		System.err.println(convertPasswordHash("Welcome@123"));
	}
}
