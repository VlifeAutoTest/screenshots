package com.vlife.springmvc.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import com.vlife.springmvc.model.Theme;

public interface UploadFilesServices  {
	
	String getFilename();
	String getDirectory();
	public void doGet(HttpServletRequest request);
	public void doUpdate(HttpServletRequest request);

		void processUploadField(FileItem fileitem);
		// 目录打散
		String makeChildDirectory(File storeDirectory, String filename);
		// 按日期打散
		
		void processFormField(FileItem fileitem);
		void updateProcessFormField(FileItem fileitem);
		void updateTheme(Theme theme);
		void saveTheme(Theme theme);
		String getName();
		String getPath();
		String getUpdateName();
		String getUpdatePath();
		String getSavePath() ;
		

	}
	
	
	
