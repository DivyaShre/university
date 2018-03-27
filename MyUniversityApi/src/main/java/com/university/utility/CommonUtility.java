package com.university.utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CommonUtility {
	private static Logger log = Logger.getLogger(CommonUtility.class);

	public static SimpleDateFormat MONTH_YEAR = new SimpleDateFormat("yyyy" + File.separator + "MM");

	public static boolean isNullEmptyString(String str) {
		if (null == str) {
			return false;
		}
		else if ("".equalsIgnoreCase(str.trim())) {
			return false;
		}
		return true;
	}
	
	public static boolean isStringDigit(String str) {
		 try  
		  {  
		    double d = Double.parseDouble(str);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;
	}

	public static boolean isUserIdNull(long userId) {
		if (0 == userId) {
			return false;
		}
		return true;
	}

	public static Timestamp getTimeStamp(String date) {
		if (null != date) {
			Timestamp creaytedTS = new Timestamp(0);
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Date parsedDateCreate = dateFormat.parse(String.valueOf(date));
				creaytedTS = new Timestamp(parsedDateCreate.getTime());

			}
			catch (Exception e) {
				// e.printStackTrace();
				log.error(e);
			}
			return creaytedTS;
		}
		else {
			return null;
		}
	}

	public static void writeResponse(HttpServletResponse response, String responseParam, int statusCode) {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "application/json");
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
			log.error(e);
		}
		finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	public static String convertToDate(long time, String dateFormat) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		return new SimpleDateFormat(dateFormat).format(c.getTime());
	}
}
