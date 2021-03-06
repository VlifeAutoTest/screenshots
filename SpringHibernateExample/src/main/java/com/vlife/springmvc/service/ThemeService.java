package com.vlife.springmvc.service;

import java.util.List;
import com.vlife.springmvc.model.Theme;

public interface ThemeService {

	Theme findById(int id);

	void saveTheme(Theme theme);

	void deleteThemeByID(int id);

	List<Theme> findAllTheme();

	Integer getMaxCheckNumberByName(String name);

	void updateTheme(Theme theme);

	List<Theme> searchByName(String name);

	List<Theme> findThemeByPage(int offset, int length);

	List<Theme> findThemeByNameAndPage(String partName, int offset, int length);
}
