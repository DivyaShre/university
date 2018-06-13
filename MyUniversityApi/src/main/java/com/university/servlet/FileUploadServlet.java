package com.university.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import com.university.service.UserService;
import com.university.utility.UNException;

@PropertySource({ "classpath:application-dev.properties", "classpath:error.properties" ,"classpath:application-staging.properties" // if same key then this will win
})
public class FileUploadServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(FileUploadServlet.class);
	private static final long serialVersionUID = 1L;
	@Value("${rootDir}")
	private String TEMP_PATH;

	@Value("${maxFileSize}")
	private String maxFileSize;
	@Value("${maxMemSize}")
	private String maxMemSize;
	@Value("${fileUploadDir}")
	private String fileUploadDir;
	@Value("${profilePicDir}")
	private String profilePicDir;

	@Autowired
	private Environment environment;
	
	@Autowired
	private UserService userService;
	
	private WebApplicationContext springContext;

	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
		log.debug("Initializing the servlet.");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		boolean uploadStatus = false;
		
		response.setContentType("application/json");
		// Check if it's a multipart request

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			response.setStatus(500);
			log.error("Not a Multipart File");
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(Integer.parseInt(maxMemSize));
		File tempPath = new File(TEMP_PATH);
		if (!tempPath.exists()) {
			tempPath.mkdirs();
		}
		factory.setRepository(tempPath);
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			String ext = null;
			File temp = null;
			MultipartFile multiPartLayoutFile = null;
			Map<String, String> idMap = new LinkedHashMap<String, String>();
			List fileItems = upload.parseRequest(request);
			Iterator i = fileItems.iterator();
			boolean isUserProfilePicUpload = request.getRequestURI().contains("/userprofilepic");
			
			String mediaFileName = null;
			boolean isValidFile = false;

			FileItem videoFile = null;
			upload.setSizeMax(Integer.parseInt(maxFileSize));
			while (i.hasNext()) {
				FileItem item = (FileItem) i.next();
				if (item.isFormField()) {
					String name = item.getFieldName();// text1
					String value = item.getString();
					idMap.put(name, value);
				}
				else {
					videoFile = item;
					String fileName = item.getName();
					mediaFileName = fileName;
					ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
					isValidFile = checkForImageFileExtension(ext);
					if(!isValidFile) {
						response.setStatus(500);
						response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Not a valid file format");
					}
					
				}
			}
			if (isValidFile) {
				if(videoFile == null) {
					response.setStatus(500);
					response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"No File to upload");
				}
				
				String userImageUrl = "";
				JSONObject systemDetails = new JSONObject();
				systemDetails.put("IP Address", request.getRemoteAddr());
				systemDetails.put("Host Name", request.getRemoteHost());
				
				if(StringUtils.isEmpty(idMap.get("userId"))){
					response.setStatus(500);
					response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "userId cannot be null or empty!");
				}else{
					long userId = Long.parseLong(idMap.get("userId").toString());
					String filePath = "";
					String relativePath = "";
					if(isUserProfilePicUpload){
						relativePath = fileUploadDir + File.separator + profilePicDir + File.separator + userId + File.separator + mediaFileName;
						filePath = tempPath + File.separator + relativePath;
					}
					temp = new File(filePath);
					if (!temp.exists()) {
						temp.getParentFile().mkdirs();
					}
					if(!filePath.isEmpty()){
						videoFile.write(temp);
						//FileItem writer = new FileItem(videoFile);
					}
					
					if(!StringUtils.isBlank(relativePath))
						uploadStatus = userService.updateImageUrl(userId, relativePath);
					else{
						response.setStatus(500);
						response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error occurred while uploading image!");
					}
				}
				if(!uploadStatus) {
					response.setStatus(500);
					response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error occurred while uploading image!");
				}else{
					response.setContentType("text/plain");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(userImageUrl);
				}
			}else{
				response.setStatus(500);
				response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Not a valid file format");
			}
		}
		catch (FileUploadException e) {
			log.error(e);
			response.setStatus(500);
		} catch (UNException e) {
			log.error(e);
			e.printStackTrace();
			response.setStatus(500);
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getErrMsg());
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response.setStatus(500);
		}
	}

	private boolean checkForImageFileExtension(String ext) {
		switch (ext) {
		case "jpg":
			break;
		case "jpeg":
			break;
		case "png":
			break;
		case "bmp":
			break;
		default:
			return false;
		}
		return true;
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
		throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
	}
}
