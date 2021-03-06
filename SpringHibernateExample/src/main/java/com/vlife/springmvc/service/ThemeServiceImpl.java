package com.vlife.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlife.springmvc.dao.ThemeDao;
import com.vlife.springmvc.model.Theme;

@Service("themeService")
@Transactional
public class ThemeServiceImpl implements ThemeService {

	@Autowired
	private ThemeDao dao;

	public Theme findById(int id) {
		return dao.findById(id);
	}

	public void saveTheme(Theme theme) {
		dao.saveTheme(theme);
	}

	public void deleteThemeByID(int id) {
		dao.deleteThemeByID(id);
	}

	public List<Theme> findAllTheme() {
		return dao.findAllTheme();
	}

	public void updateTheme(Theme theme) {
		Theme entity = dao.findById(theme.getId());
		if (entity != null) {
			entity.setName(theme.getName());
			entity.setPath(theme.getPath());

		}
	}

	@Override
	public List<Theme> searchByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public List<Theme> findThemeByPage(int offset, int length) {
		return dao.findThemeByPage(offset, length);

	}

	@Override
	public List<Theme> findThemeByNameAndPage(String partName, int offset, int length) {
		return dao.findThemeByNameAndPage(partName, offset, length);
	}

	public Integer getMaxCheckNumberByName(String name) {
		return dao.getMaxCheckNumberByName(name);
	}

}
