package com.vlife.springmvc.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vlife.springmvc.model.Theme;
import com.vlife.springmvc.model.Vendor;
import com.vlife.springmvc.service.ThemeService;
import com.vlife.springmvc.service.UploadFilesServices;
import com.vlife.springmvc.service.VendorService;
import com.vlife.springmvc.model.Application;
import com.vlife.springmvc.model.Mobile;
import com.vlife.springmvc.model.Runinfo;
import com.vlife.springmvc.service.ApplicationService;
import com.vlife.springmvc.service.MobileService;
import com.vlife.springmvc.service.MobileStatusService;
import com.vlife.springmvc.service.RuninfoService;
import com.vlife.springmvc.model.TestServer;
import com.vlife.springmvc.service.TestServerService;

@Controller
@RequestMapping("/")
public class AppController {

/*	@Autowired
	EmployeeService service;*/
	
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	ApplicationService app_service;
	
	@Autowired
	ThemeService theme_service;
	
	@Autowired
	MobileService mobile_service;
	
	@Autowired
	TestServerService tserver_service;
	
	@Autowired
	VendorService vendor_service;
	
	@Autowired
	UploadFilesServices upload_files;
	
	@Autowired
	MobileStatusService status_services;
	
	@Autowired
	RuninfoService runinfo_services;

	
    @ModelAttribute("vendors")
    public List<Vendor> initializeVendors() {
        return vendor_service.findAllVendor();
    }
 
    @ModelAttribute("mobiles")
    public List<Mobile> initializeMobiles() {
        return mobile_service.findAllMobile();
    }
    
    @ModelAttribute("servers")
    public List<TestServer> initializeServers() {
        return tserver_service.findAllTestServer();
    }
	
    public String getErrorString(BindingResult bindingResult){
    	

       
/*        List<ObjectError> ls=bindingResult.getAllErrors();  
        for (int i = 0; i < ls.size(); i++) {  
            System.out.println("error:"+ls.get(i));  
        } */
    	List<FieldError>  err= bindingResult.getFieldErrors();
        FieldError fe;
        String field;
        String errorMessage;
        StringBuffer buffer = new StringBuffer("");
        String temp;
        for (int i = 0; i < err.size(); i++) {
            fe=err.get(i);
            field=fe.getField();
            errorMessage=fe.getDefaultMessage();
            temp = field +" : "+errorMessage;
            buffer.append(temp);
        }
		    String errors = buffer.toString();
		return errors;
		}
    
    public String[] getDetailInfo(Runinfo rinfo) {
    	
    	String[] res = new String[4];
    	
    	// e_time, s_time
		Date day=new Date();  
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
        String time = simpleDateFormat.format(day).trim();
        res[2] = time;
        
        //get mobile uid and vendor name
        int id = rinfo.getMid();
        String uid = mobile_service.findById(id).getUid().trim();
        String name = mobile_service.findById(id).getName().trim();
        id = rinfo.getVid();
        String vname = vendor_service.findById(id).getName().trim();
        
        // image_path
        res[0] = "/picture" + "/" + vname + "/" + name + "_" + uid + "/" + time;
        //zip_file
        res[1] = vname + "_" + name + "_" + uid + "_" + time + ".zip";
        
        //log_file
        res[3] = vname + "_" + name + "_" + uid + "_" + time + ".html";
    	
    	return res;
    	
    }

	@RequestMapping(value = { "/check" }, method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String checkResource(ModelMap model)  {
		
		List<Vendor> vendors = vendor_service.findAllVendor();
		
		Runinfo runinfo = new Runinfo();
		
		model.addAttribute("runinfo", runinfo);
		model.addAttribute("vendors", vendors);
		
		return "check";
	}
    
	@RequestMapping(value = { "/check" }, method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public void saveRuninfo(@Valid Runinfo runinfo, BindingResult result,
			ModelMap model) throws UnsupportedEncodingException {
		
		String[] tmp = getDetailInfo(runinfo);
        runinfo.setStime(tmp[2]);
        runinfo.setEtime(tmp[2]);
        runinfo.setStatus("Running");
        runinfo.setImagepath(tmp[0]);
        runinfo.setZip(tmp[1]);
        runinfo.setLogFile(tmp[3]);
        
        // set sid
        List infos = status_services.getOriginStatusInfo();
        int mid = runinfo.getMid();
        String uid = mobile_service.findById(mid).getUid();
    	Iterator it = infos.iterator();
    		
    	while(it.hasNext()) {
    			
    		Object[] temp = (Object[]) it.next();
    		
    		// get mid
    		int cmid = Integer.parseInt(temp[1].toString());
    		
    		if (mid == cmid) {
    			int sid = Integer.parseInt(temp[0].toString());
    			runinfo.setSid(sid);
    		}
        }
        
		runinfo_services.saveRuninfo(runinfo);
		
		// need runid for python program 
		int runid = runinfo.getId();
		
		System.out.println(runid);
		
	}
    
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String showMobile(ModelMap model) {
		
		List<Object[]> status = status_services.findDeviceStatus();
		List<Object[]> devinfo = status_services.deviceStatusInfo();
		
		model.addAttribute("status", status);
		model.addAttribute("devinfo",devinfo);
		
		
		return "mobilestatus";
	}
	
	@RequestMapping(value = { "/newmobile" }, method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String newMobile(ModelMap model) {
		Mobile app = new Mobile();
		model.addAttribute("mobile", app);
		model.addAttribute("edit", false);
		return "mobile";
	}
	
	
	@RequestMapping(value = { "/mobilelist" }, method = RequestMethod.GET)
	public String listMobiles(ModelMap model) {

		List<Mobile> mobiles = mobile_service.findAllMobile();
		model.addAttribute("mobiles", mobiles);
		return "allmobiles";
	}
	
	@RequestMapping(value = { "/newmobile" }, method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String saveMobile(@Valid Mobile mobile, BindingResult result,
			ModelMap model) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "mobile";
		}
		
		if(!mobile_service.isMobileUidUnique(mobile.getId(), mobile.getUid())){
			FieldError ssnError =new FieldError("mobile","uid",messageSource.getMessage("non.unique.uid", new String[]{mobile.getUid()}, Locale.getDefault()));
		    result.addError(ssnError);
			return "mobile";
		}
		
		String temp = new String(mobile.getName().getBytes("iso-8859-1"),"utf-8");
		
		//delete spaces
		mobile.setName(temp.trim().replace(" ", ""));
		
		mobile_service.saveMobile(mobile);
		return "redirect:/mobilelist";
	}
	
	@RequestMapping(value = { "/edit-{uid}-mobile" }, method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String editmobile(@PathVariable String uid, ModelMap model) {
		Mobile mobile = mobile_service.findMobileByUid(uid);
		String vname = mobile.getVendor().getName();
		model.addAttribute("vname", vname);
		model.addAttribute("mobile", mobile);
		model.addAttribute("edit", true);
		return "mobile";
	}
	
	
	@RequestMapping(value = { "/edit-{uid}-mobile" }, method = RequestMethod.POST)
	public String updatemobile(@Valid Mobile mobile, BindingResult result,
			ModelMap model) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "mobile";
		}
		
		if(!mobile_service.isMobileUidUnique(mobile.getId(), mobile.getUid())){
			FieldError ssnError =new FieldError("mobile","uid",messageSource.getMessage("non.unique.uid", new String[]{mobile.getUid()}, Locale.getDefault()));
		    result.addError(ssnError);
			return "mobile";
		}
		
		String temp = new String(mobile.getName().getBytes("iso-8859-1"),"utf-8");
		mobile.setName(temp.trim().replace(" ", ""));
		
		mobile_service.updateMobile(mobile);

		return "redirect:/mobilelist";
	}
	
	@RequestMapping(value = { "/delete-{uid}-mobile" }, method = RequestMethod.GET)
	public String deleteMobile(@PathVariable String uid) {
		mobile_service.deleteMobileByUid(uid);
		return "redirect:/mobilelist";
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

	@Validated
	@RequestMapping(value = { "/servernew" }, method = RequestMethod.POST)
	public String saveServer(@Valid TestServer server, BindingResult result,
			ModelMap model) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			String errors = getErrorString(result);
			model.addAttribute("errorInfo",errors);
    		model.addAttribute("server", server);
    		model.addAttribute("edit", false);
			return "addserver";
		}
		
		if(!tserver_service.isTestServerSsnUnique(server.getId(), server.getSsn())){
			FieldError ssnError =new FieldError("server","ssn",messageSource.getMessage("non.unique.ssn", new String[]{server.getSsn()}, Locale.getDefault()));
		    result.addError(ssnError);
            model.addAttribute("errorInfo", ssnError.getDefaultMessage());
    		model.addAttribute("server", server);
    		model.addAttribute("edit", false);
			return "addserver";
		}
		String temp = new String(server.getSsn().getBytes("iso-8859-1"),"utf-8");
		server.setSsn(temp);
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
			ModelMap model) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "addserver";
		}
		
		if(!tserver_service.isTestServerSsnUnique(server.getId(), server.getSsn())){
			FieldError ssnError =new FieldError("server","ssn",messageSource.getMessage("non.unique.ssn", new String[]{server.getSsn()}, Locale.getDefault()));
		    result.addError(ssnError);
			model.addAttribute("server", server);
			model.addAttribute("edit", true);
			return "addserver";
		}
		
		String temp = new String(server.getSsn().getBytes("iso-8859-1"),"utf-8");
		server.setSsn(temp);
		
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
	
	@RequestMapping(value = { "/newtheme" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String saveTheme(@Valid Theme theme, BindingResult result, ModelMap model, HttpServletRequest request)
			throws UnsupportedEncodingException {

		// if (result.hasErrors()) {
		// return "theme";
		// }
		upload_files.doGet(request);

		theme.setName(upload_files.getName());
		theme.setPath(upload_files.getDirectory());
		upload_files.saveTheme(theme);
		return "redirect:/themelist";
	}
	
	
	@RequestMapping(value = { "/edit-{id}-theme" }, method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public String editTheme(@PathVariable int id, ModelMap model) {
		Theme theme = theme_service.findById(id);
		model.addAttribute("theme", theme);
		model.addAttribute("edit", true);
		return "theme";
	}

	@RequestMapping(value = { "/edit-{id}-theme" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String updateTheme(@Valid Theme theme, BindingResult result, ModelMap model, @PathVariable int id,
			HttpServletRequest request) {
		upload_files.doUpdate(request);

		theme.setName(upload_files.getUpdateName());
		theme.setPath(upload_files.getUpdatePath());
		upload_files.updateTheme(theme);
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
	
	@RequestMapping(value = { "/themelist" }, method = RequestMethod.POST)
	public String searchThemes(ModelMap model  ,HttpServletRequest request,@RequestParam(value="search" ,defaultValue="") String search) {
		String temp="";
		try {
			temp = new String(search.getBytes("iso-8859-1"), "utf-8").trim();
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			
		}
		System.out.println("sadasd"+temp);
		List<Theme> themes = theme_service.searchByName(temp);
		model.addAttribute("themes", themes);
		return "allthemes";
	}
	
	
	@RequestMapping(value = { "/applicationlist" }, method = RequestMethod.GET)
	public String listApplications(ModelMap model) {

		List<Application> applications = app_service.findAllApplication();
		model.addAttribute("applications", applications);
		return "allapplications";
	}

	
	@RequestMapping(value = { "/newapplication" }, method = RequestMethod.GET)
	public String newApplication(ModelMap model) {
		Application app = new Application();
		model.addAttribute("application", app);
		model.addAttribute("edit", false);
		return "application";
	}

	@RequestMapping(value = { "/newapplication" }, method = RequestMethod.POST)
	public String saveApplication(@Valid Application app, BindingResult result,
			ModelMap model) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "application";
		}
		String temp = new String(app.getName().getBytes("iso-8859-1"),"utf-8");
		app.setName(temp);
		app_service.saveApplication(app);
		return "redirect:/applicationlist";
	}
	
	@RequestMapping(value = { "/edit-{id}-application" }, method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public String editApplication(@PathVariable int id, ModelMap model) {
		Application app = app_service.findById(id);
		String vname = app.getVendor().getName();
		model.addAttribute("vname", vname);
		model.addAttribute("application", app);
		model.addAttribute("edit", true);
		return "application";
	}

	@RequestMapping(value = { "/edit-{id}-application" }, method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public String updateApplication(@Valid Application app, BindingResult result,
			ModelMap model, @PathVariable int id) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "application";
		}
	
		String temp = new String(app.getName().getBytes("iso-8859-1"),"utf-8");
		app.setName(temp);
		app_service.updateApplication(app);
		
		return "redirect:/applicationlist";
	}
	
	@RequestMapping(value = { "/delete-{id}-application" }, method = RequestMethod.GET)
	public String deleteApplication(@PathVariable int id) {
		app_service.deleteApplicationByID(id);
		return "redirect:/applicationlist";
	}
	
	@RequestMapping(value = { "/list-{vendorid}-application" }, method = RequestMethod.GET)
	public String listApplicationByVendor(@PathVariable int vendorid,ModelMap model) {

		Vendor vendor = vendor_service.findById(vendorid);
		List<Application>  apps= app_service.findApplicationByVendorID(vendor);
		List<Application> applications = app_service.findAllApplication();
		model.addAttribute("applications", apps);
		return "allapplications";
	}

	@RequestMapping(value = { "/uploadfiles" }, method = RequestMethod.GET)
	public String uploadFiles(ModelMap model) {

		return "uploadfile";
	}

	@Validated
	@RequestMapping(value = { "/uploadfiles" }, method = RequestMethod.POST)
	public String saveuploadFile(HttpServletRequest request, @Valid Theme theme, BindingResult result, ModelMap model)
			throws UnsupportedEncodingException {

		upload_files.doGet(request);

		String fileName = upload_files.getFilename();
		theme.setName(fileName);

		theme.setPath(upload_files.getDirectory());
		upload_files.saveTheme(theme);
		List<Theme> themes = theme_service.findAllTheme();

		model.addAttribute("themes", themes);

		return "allthemes";
	}
	
	@RequestMapping(value = { "/list-apps-by-{vendorid}" }, method = RequestMethod.GET)
    @ResponseBody
    public List<Application> listApps(@PathVariable int vendorid,ModelMap model){
		Vendor vendor = vendor_service.findById(vendorid);
		List<Application>  apps= app_service.findApplicationByVendorID(vendor);   
        return  apps;
    }
	
	@RequestMapping(value = { "/list-all-resources" }, method = RequestMethod.GET)
    @ResponseBody
    public List<Theme> listResources(ModelMap model){
		List<Theme>  themes= theme_service.findAllTheme();   
        return  themes;
    }
	
	@RequestMapping(value = { "/list-mobiles-by-{vendorid}" }, method = RequestMethod.GET)
    @ResponseBody
    public List<Mobile> listMobiles(@PathVariable int vendorid,ModelMap model){
		List<Mobile> res = new ArrayList<>();
		List<Mobile> conn = status_services.getFreeDevices();
		for (int i=0; i < conn.size(); i++) {
			
			int tmp = conn.get(i).getVendor().getId();
			
			if (tmp == vendorid) {
				
				res.add(conn.get(i));	
			}	
		}
        return  res;
    }
} 
