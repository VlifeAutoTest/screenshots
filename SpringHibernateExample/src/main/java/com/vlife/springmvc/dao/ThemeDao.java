package com.vlife.springmvc.dao;

import java.util.List;

import com.vlife.springmvc.model.Theme;

public interface ThemeDao {

	Theme findById(int id);

	void saveTheme(Theme theme);
	
	List<Theme> findAllTheme();
	
	void deleteThemeByID(int id);
	
	 List<Theme> findByName(String  partName);

}