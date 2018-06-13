/**
 * 
 */
package com.university.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.university.utility.Constants;


@PropertySource({"classpath:application-prod.properties","classpath:application-dev.properties"})
public class FileDownloadServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(FileDownloadServlet.class);
	private static final long serialVersionUID = 1L;
	private static final int BYTES_DOWNLOAD = 1024;
	private static String BASE_PATH = Constants.BASE_PATH;    
	
	private WebApplicationContext springContext;

	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
		log.debug("Initializing the servlet.");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		String fileName = BASE_PATH;
		log.debug("Downloading file through post " + fileName);
		downloadFile(fileName, request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Path path = Paths.get(request.getRequestURI());
		if (path.toString().toLowerCase().contains("profilepicture")){
			
			String fileName = request.getParameter("fileName").toString();
			String current =  BASE_PATH;
			String filePath = current + File.separator + fileName;
			log.debug("Downloading File ... " + filePath);
			downloadFile(filePath, request, response);
			//FileUtils.deleteDirectory(new File(filePath).getParentFile());
		} 
	}

	private void downloadFile(String fileName, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String contentType = getContentType(fileName);
		log.debug("Download: File Name = " + fileName);
		log.debug("Download: Content Type = " + contentType);
		response.setContentType(contentType);
		 String downloadFileName = "";
		 if (fileName.contains(File.separator)) {
			downloadFileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
			log.debug("Download: Modified FileName = " + downloadFileName);
		} else {
			downloadFileName = fileName;
		}

		if (!fileName.endsWith(".html")) {
			response.setHeader("Content-Disposition", "attachment;filename=" + downloadFileName);
		}

		File file = new File(fileName);
		if(file.exists()){
			System.out.println("exists");
		}
		InputStream is = new FileInputStream(new File(fileName));
		int read = 0;
		byte[] bytes = new byte[BYTES_DOWNLOAD];
		OutputStream os = response.getOutputStream();

		while ((read = is.read(bytes)) != -1) {
			os.write(bytes, 0, read);
		}
		os.flush();
		os.close();
		is.close();
		log.debug("File download complete");
	}

	private String getContentType(String fileName) {
		String contentType = "text/html";
		if (fileName.endsWith(".html") || fileName.endsWith(".kp")) {
			contentType = "text/html";
		} else if (fileName.endsWith(".jpg")) {
			contentType = "image/jpg";
		} else if (fileName.endsWith(".pdf")) {
			contentType = "application/pdf";
		} else if (fileName.endsWith(".xlsx")) {
			contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		} else if (fileName.endsWith(".jar")) {
			contentType = "application/jar";
		} else if (fileName.endsWith(".zip")) {
			contentType = "application/octet-stream";
		}

		return contentType;
	}

}