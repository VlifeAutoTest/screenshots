package com.vlife.springmvc.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.vlife.springmvc.model.Theme;
import com.vlife.springmvc.model.Vendor;
import com.vlife.springmvc.service.ThemeService;
import com.vlife.springmvc.service.VendorService;
import com.vlife.springmvc.model.Mobile;
import com.vlife.springmvc.service.MobileService;
import com.vlife.springmvc.model.TestServer;
import com.vlife.springmvc.service.TestServerService;

@Controller
@RequestMapping("/")
public class AppController {

/*	@Autowired
	EmployeeService service;*/
	
	
	@Autowired
	MessageSource messageSource;
	
//	@Autowired
//	ApplicationService app_service;
//	
	@Autowired
	ThemeService theme_service;
	
	@Autowired
	MobileService mobile_service;
	
	@Autowired
	TestServerService tserver_service;
	
	@Autowired
	VendorService vendor_service;

	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String showMobile(ModelMap model) {

		return "mobilestatus";
	}
	
	@RequestMapping(value = { "/serverlist" }, method = RequestMethod.GET)
	public String listServers(ModelMap model) {

		List<TestServer> servers = tserver_service.findAllTestServer();
		model.addAttribute("servers", servers);
		return "allservers";
	}
	
	@RequestMapping(value = { "/vendorlist" }, method = RequestMethod.GET)
	public String listVendors(ModelMap model) {

		List<Vendor> vendors = vendor_service.findAllVendor();
		model.addAttribute("vendors", vendors);
		return "allvendors";
	}
	
	@RequestMapping(value = { "/newvendor" }, method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String newvendor(ModelMap model) {
		Vendor vendor = new Vendor();
		model.addAttribute("vendor", vendor);
		model.addAttribute("edit", false);
		return "vendor";
	}
	
	@RequestMapping(value = { "/newvendor" }, method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String saveVendor(@Valid Vendor vendor, BindingResult result,
			ModelMap model) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "vendor";
		}
		
		String temp = new String(vendor.getName().getBytes("iso-8859-1"),"utf-8");
		vendor.setName(temp);
		vendor_service.saveVendor(vendor);
		return "redirect:/vendorlist";
	}

	@RequestMapping(value = { "/edit-{id}-vendor" }, method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public String editVendor(@PathVariable int id, ModelMap model) {
		Vendor vendor = vendor_service.findById(id);
		model.addAttribute("vendor", vendor);
		model.addAttribute("edit", true);
		return "vendor";
	}
	

	@RequestMapping(value = { "/edit-{id}-vendor" }, method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public String updateVendor(@Valid Vendor vendor, BindingResult result,
			ModelMap model, @PathVariable int id) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "vendor";
		}
	
		String temp = new String(vendor.getName().getBytes("iso-8859-1"),"utf-8");
		vendor.setName(temp);
		vendor_service.updateVendor(vendor);
		
		return "redirect:/vendorlist";
	}
	
	@RequestMapping(value = { "/delete-{id}-vendor" }, method = RequestMethod.GET)
	public String deleteVendor(@PathVariable int id) {
		vendor_service.deleteVendorByID(id);
		return "redirect:/vendorlist";
	}
	
	@RequestMapping(value = { "/servernew" }, method = RequestMethod.GET)
	public String newServer(ModelMap model) {
		TestServer server = new TestServer();
		model.addAttribute("server", server);
		model.addAttribute("edit", false);
		return "addserver";
	}

	@RequestMapping(value = { "/servernew" }, method = RequestMethod.POST)
	public String saveServer(@Valid TestServer server, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "addserver";
		}
		
		if(!tserver_service.isTestServerSsnUnique(server.getId(), server.getSsn())){
			FieldError ssnError =new FieldError("server","ssn",messageSource.getMessage("non.unique.ssn", new String[]{server.getSsn()}, Locale.getDefault()));
		    result.addError(ssnError);
			return "addserver";
		}
		
		tserver_service.saveTestServer(server);

		return "redirect:/serverlist";
	}
	
	@RequestMapping(value = { "/edit-{ssn}-testserver" }, method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String editServer(@PathVariable String ssn, ModelMap model) {
		TestServer server = tserver_service.findTestServerBySsn(ssn);
		model.addAttribute("server", server);
		model.addAttribute("edit", true);
		return "addserver";
	}
	
	
	@RequestMapping(value = { "/edit-{ssn}-testserver" }, method = RequestMethod.POST)
	public String updateServer(@Valid TestServer server, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "addserver";
		}
		
		if(!tserver_service.isTestServerSsnUnique(server.getId(), server.getSsn())){
			FieldError ssnError =new FieldError("server","ssn",messageSource.getMessage("non.unique.ssn", new String[]{server.getSsn()}, Locale.getDefault()));
		    result.addError(ssnError);
			return "addserver";
		}
		
		tserver_service.updateTestServer(server);

		return "redirect:/serverlist";
	}
	
	@RequestMapping(value = { "/delete-{ssn}-testserver" }, method = RequestMethod.GET)
	public String deleteTestServer(@PathVariable String ssn) {
		tserver_service.deleteTestServerBySsn(ssn);
		return "redirect:/serverlist";
	}
	
	@RequestMapping(value = { "/newtheme" }, method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String newTheme(ModelMap model) {
		Theme app = new Theme();
		model.addAttribute("theme", app);
		model.addAttribute("edit", false);
		return "theme";
	}
	
	@RequestMapping(value = { "/newtheme" }, method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String saveTheme(@Valid Theme theme, BindingResult result,
			ModelMap model) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "theme";
		}
		
		String temp = new String(theme.getName().getBytes("iso-8859-1"),"utf-8");
		theme.setName(temp);
		theme_service.saveTheme(theme);
		return "redirect:/themelist";
	}
	
	@RequestMapping(value = { "/newmobile" }, method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String newMobile(ModelMap model) {
		Mobile app = new Mobile();
		int[] slist = tserver_service.findAllServerID();
		model.addAttribute("mobile", app);
		model.addAttribute("serverlist", slist);
		model.addAttribute("edit", false);
		return "mobile";
	}
	
	@RequestMapping(value = { "/newmobile" }, method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String saveMobile(@Valid Mobile mobile, BindingResult result,
			ModelMap model) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "theme";
		}
		
		String temp = new String(mobile.getName().getBytes("iso-8859-1"),"utf-8");
		mobile.setName(temp);
		mobile_service.saveMobile(mobile);
		return "success";
	}
	
	
	@RequestMapping(value = { "/edit-{id}-theme" }, method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public String editTheme(@PathVariable int id, ModelMap model) {
		Theme theme = theme_service.findById(id);
		model.addAttribute("theme", theme);
		model.addAttribute("edit", true);
		return "theme";
	}
	

	@RequestMapping(value = { "/edit-{id}-theme" }, method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public String updateTheme(@Valid Theme theme, BindingResult result,
			ModelMap model, @PathVariable int id) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "theme";
		}
	
		String temp = new String(theme.getName().getBytes("iso-8859-1"),"utf-8");
		theme.setName(temp);
		theme_service.updateTheme(theme);
		
		return "redirect:/themelist";
	}
	
	@RequestMapping(value = { "/delete-{id}-theme" }, method = RequestMethod.GET)
	public String deleteTheme(@PathVariable int id) {
		theme_service.deleteThemeByID(id);
		return "redirect:/themelist";
	}
	
	@RequestMapping(value = { "/themelist" }, method = RequestMethod.GET)
	public String listThemes(ModelMap model) {

		List<Theme> themes = theme_service.findAllTheme();
		model.addAttribute("themes", themes);
		return "allthemes";
	}
//	
//	@RequestMapping(value = { "/new_application" }, method = RequestMethod.GET)
//	public String newApplication(ModelMap model) {
//		Application app = new Application();
//		model.addAttribute("application", app);
//		model.addAttribute("edit", false);
//		return "application";
//	}
//
//	@RequestMapping(value = { "/new_application" }, method = RequestMethod.POST)
//	public String saveApplication(@Valid Application app, BindingResult result,
//			ModelMap model) {
//
//		if (result.hasErrors()) {
//			return "application";
//		}
//
//		app_service.saveApplication(app);
//		model.addAttribute("success", "Employee " + app.getName() + " registered successfully");
//		return "success";
//	}
} 
