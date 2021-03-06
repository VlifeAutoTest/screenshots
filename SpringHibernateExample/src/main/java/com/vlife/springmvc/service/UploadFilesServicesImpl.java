package com.vlife.springmvc.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.ThemeDao;
import com.vlife.springmvc.model.Theme;
@Service("uploadfiles")
@Transactional
public class UploadFilesServicesImpl implements  UploadFilesServices {
	
	
	
	@Autowired
	private ThemeDao dao;
	//文教保存路径
	String savePath="/diskb/uploadfiles";
	private String name;
	//临时文件存储路径
	private String tempPath="/diskb/tempData";
	private String  filename2;
	private String filename ;
	private String path;
	private String updateName;
	private String updatePath;
	@Override
	public void doGet(HttpServletRequest request) {
		// request.setCharacterEncoding("UTF-8");
		// 要执行文件上传的操作
		// 判断表单是否支持文件上传。即：enctype="multipart/form-data"
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);   //判断当前表单是否是一个上传的表单,
		if (!isMultipartContent) {
			throw new RuntimeException("your form is not multipart/form-data");
		}

		// 创建一个DiskFileItemfactory工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		File file =new File (tempPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		factory.setRepository(new File(tempPath));// 指定临时文件的存储目录
		// 创建一个ServletFileUpload核心对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 解决上传表单项乱码问题
		sfu.setHeaderEncoding("UTF-8");

		// 解析request对象，并得到一个表单项的集合
		try {
			List<FileItem> fileItems = sfu.parseRequest(request);

			// 遍历表单项数据
			for (FileItem fileitem : fileItems) {
				if (fileitem.isFormField()) {
					// 普通表单项
					processFormField(fileitem);
				} 
				else {
					// 上传表单项
					processUploadField(fileitem);
				}
			}

		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			// throw new RuntimeException("文件过在，不能超过3M");

			System.out.println("文件过在，不能超过3M");
		} catch (FileUploadBase.SizeLimitExceededException e) {
			System.out.println("总文件大小不能超过6M");
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doUpdate(HttpServletRequest request) {
		// request.setCharacterEncoding("UTF-8");
		// 要执行文件上传的操作
		// 判断表单是否支持文件上传。即：enctype="multipart/form-data"
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);   //判断当前表单是否是一个上传的表单,
		if (!isMultipartContent) {
			throw new RuntimeException("your form is not multipart/form-data");
		}

		// 创建一个DiskFileItemfactory工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(new File(tempPath));// 指定临时文件的存储目录
		// 创建一个ServletFileUpload核心对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 解决上传表单项乱码问题
		sfu.setHeaderEncoding("UTF-8");

		// 解析request对象，并得到一个表单项的集合
		try {
			// 限制上传文件的大小
			// sfu.setFileSizeMax(1024*1024*3);//表示3M大小
			// sfu.setSizeMax(1024*1024*6);
			List<FileItem> fileItems = sfu.parseRequest(request);

			// 遍历表单项数据
			for (FileItem fileitem : fileItems) {
				if (fileitem.isFormField()) {
					// 普通表单项
					updateProcessFormField(fileitem);
				} 
				
			}

		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			// throw new RuntimeException("文件过在，不能超过3M");

			System.out.println("文件过在，不能超过3M");
		} catch (FileUploadBase.SizeLimitExceededException e) {
			System.out.println("总文件大小不能超过6M");
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void processUploadField(FileItem fileitem) {
		try {
			String directoryRealPath= savePath;
			File storeDirectory = new File(directoryRealPath);// 即代表文件又代表目录
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// 创建一个指定的目录
			}
			// 得到上传的名子
			 filename = fileitem.getName();// 文件项中的值 F:\图片素材\小清新\43.jpg 或者
					filename2=filename;								// 43.jpg
			Date  date =new Date();
			 DateFormat dateformat= new SimpleDateFormat("yyyyMMdd-HHmmss");
			 String date1 = dateformat.format(date); 
			 
			filename = date1+filename;
			// 上传文件，自动删除临时文件
			fileitem.write(new File(storeDirectory,filename));
			fileitem.delete();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String makeChildDirectory(File storeDirectory, String filename) {
		 childDirectory = savePath;

		// 创建指定目录
		File file = new File(storeDirectory, childDirectory);
		if (!file.exists()) {
			file.mkdirs();
		}
		return childDirectory;
	}

	@Override
	public void processFormField(FileItem fileitem) {
		try {
			
			String fieldname = fileitem.getFieldName();// 字段名
			String fieldvalue = fileitem.getString("UTF-8");// 字段值
			
			if(fieldname.equals("name")) {
				name=fieldvalue;
			}
			if(fieldname.equals("path")) {
				
				path=fieldvalue;
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveTheme(Theme theme) {
		dao.saveTheme(theme);
	}

	@Override
	public String getPath() {
		return path;
	}
	public String getName() {
		return name;
	}
	private String childDirectory;
	public String getDirectory() {
		
		if(filename2==null|filename2=="") {
			return "";
		}
		else {
			
			return  savePath+"/"+filename;
		}
		
	}
	public String getFilename() {
		return filename;
	}

	@Override
	public void updateTheme(Theme theme) {
		Theme entity = dao.findById(theme.getId());
		if(entity!=null){
			entity.setName(theme.getName());
			entity.setPath(theme.getPath());

		}
		
	}

	@Override
	public void updateProcessFormField(FileItem fileitem) {
try {
			
			String fieldname = fileitem.getFieldName();// 字段名
			String fieldvalue = fileitem.getString("UTF-8");// 字段值
			
			if(fieldname.equals("name")) {
				
				updateName=fieldvalue;
			}
			if(fieldname.equals("path")) {
				
				updatePath=fieldvalue;
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public String getUpdateName() {
		return updateName;
	}

	
@Override
	public String getUpdatePath() {
		return updatePath;
	}

@Override
public String getSavePath() {
	return savePath;
}

	
}