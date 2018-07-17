package com.vlife.springmvc.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import com.vlife.springmvc.model.Theme;
import com.vlife.springmvc.model.User;
import com.vlife.springmvc.model.Vendor;
import com.vlife.springmvc.service.ThemeService;
import com.vlife.springmvc.service.UploadFilesServices;
import com.vlife.springmvc.service.UserService;
import com.vlife.springmvc.service.VendorService;
import com.jcraft.jsch.Session;
import com.vlife.checkserver.mobilestatus.CheckMobileSattus;
import com.vlife.checkserver.mobilestatus.Methods;
import com.vlife.checkserver.mobilestatus.SSHCopyFile;
import com.vlife.checkserver.mobilestatus.SendEmailMethods;
import com.vlife.springmvc.model.Application;
import com.vlife.springmvc.model.Mobile;
import com.vlife.springmvc.model.Resources;
import com.vlife.springmvc.model.Role;
import com.vlife.springmvc.model.Runinfo;
import com.vlife.springmvc.service.ApplicationService;
import com.vlife.springmvc.service.MobileService;
import com.vlife.springmvc.service.MobileStatusService;

import com.vlife.springmvc.service.ResourceService;
import com.vlife.springmvc.service.RoleService;
import com.vlife.springmvc.service.RuninfoService;
import com.vlife.springmvc.model.TestServer;
import com.vlife.springmvc.service.TestServerService;

@Controller
@RequestMapping("/")
@SessionAttributes(value = { "tvendorid", "searchValue", "pageType", "logSuccessUser" })
public class AppController {
	/*
	 * @Autowired EmployeeService service;
	 */
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

	@Autowired
	UserService user_services;

	@Autowired
	RoleService role_services;

	@Autowired
	ResourceService resource_service;

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

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String showMobile(ModelMap model) {

		List<Object[]> status = status_services.findDeviceStatus();
		List<Object[]> devinfo = status_services.deviceStatusInfo();

		model.addAttribute("status", status);
		model.addAttribute("devinfo", devinfo);

		return "mobilestatus";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(ModelMap model, @ModelAttribute("logSuccessUser") User user) {
		model.addAttribute("user", user);
		return "home";
	}

	@RequestMapping(value = { "/refresh" }, method = RequestMethod.GET)
	public String refreshMobileStatus(ModelMap model) {

		CheckMobileSattus cms = new CheckMobileSattus();
		cms.run();
		return "redirect:/list";
	}

	// 审核查询代码

	public String[] getDetailInfo(Runinfo rinfo) {

		String[] res = new String[4];

		// e_time, s_time
		Date curday = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = simpleDateFormat.format(curday).trim();
		res[2] = time;
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyyMMddHHmmss");
		String time2 = simpleDateFormat2.format(curday).trim();
		// get mobile uid and vendor name
		int id = Integer.parseInt(rinfo.getMid());
		String uid = mobile_service.findById(id).getUid().trim();
		String name = mobile_service.findById(id).getName().trim();
		id = rinfo.getVid();
		String vname = vendor_service.findById(id).getName().trim();

		// image_path
		res[0] = "/picture/" + vname + "/" + name + "_" + uid + "/" + time2;
		// zip_file
		res[1] = vname + "_" + name + "_" + uid + "_" + time2 + ".zip";
		// log_file
		res[3] = vname + "_" + name + "_" + uid + "_" + time2 + ".html";

		return res;

	}
	
	@RequestMapping(value = { "/userlist" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String userManage(ModelMap model) {

		List<User> users = user_services.findAllUser();
		model.addAttribute("users", users);
		return "allusers";
	}
	
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String newuser(ModelMap model) {
		User user = new User();
		Role role = new Role();
		List<Role> roles = role_services.findAllRole();
		model.addAttribute("roles", roles);
		model.addAttribute("user", user);
		model.addAttribute("role",role);
		return "user";
	}

	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String saveUser(@Valid User user, BindingResult result, ModelMap model) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "user";
		}

		String temp = new String(user.getName().getBytes("iso-8859-1"), "utf-8");
		user.setName(temp);
		
		Date curday = new Date();
		
		user.setJoined_date(curday);
		user.setLasted_update(curday);

		user_services.saveUser(user);
		return "redirect:/userlist";
	}
	
	@RequestMapping(value = { "/edit-{id}-user" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String editUser(@PathVariable int id, ModelMap model) {
		User user = user_services.findById(id);
		List<Role> roles = role_services.findAllRole();
		model.addAttribute("roles", roles);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		return "user";
	}

	@RequestMapping(value = { "/edit-{id}-user" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String updateUser(@Valid User user, BindingResult result, ModelMap model, @PathVariable int id)
			throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "user";
		}

		String temp = new String(user.getName().getBytes("iso-8859-1"), "utf-8");
		user.setName(temp);

//		user_services.removeRelRoles(user);
		user_services.updateUser(user);
		return "redirect:/userlist";
	}

	@RequestMapping(value = { "/delete-{id}-user" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable int id) {
		user_services.deleteUserByID(id);
		return "redirect:/userlist";
	}


	@RequestMapping(value = { "/role-permission" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String roleManage(ModelMap model) {

		List<Role> roles = role_services.findAllRole();
		model.addAttribute("roles", roles);
		return "rolepermission";
	}

	@RequestMapping(value = { "/newrole" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String newRole(ModelMap model) {
		Role role = new Role();
		List<Resources> resources = resource_service.findAllResource();
		model.addAttribute("resources", resources);
		model.addAttribute("role", role);
		;
		model.addAttribute("edit", false);
		return "role";
	}

	@RequestMapping(value = { "/newrole" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String saveRole(@Valid Role role, BindingResult result, ModelMap model) throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "role";
		}

		String temp = new String(role.getName().getBytes("iso-8859-1"), "utf-8");
		role.setName(temp);
		temp = new String(role.getDescription().getBytes("iso-8859-1"), "utf-8");
		role.setDescription(temp);
		role_services.saveRole(role);
		return "redirect:/role-permission";
	}

	@RequestMapping(value = { "/edit-{id}-role" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String editRole(@PathVariable int id, ModelMap model) {
		Role role = role_services.findById(id);
		Set<Resources> rlist = role.getRelresources();
		Iterator<Resources> it = rlist.iterator();
		List<Integer> relID = new ArrayList<Integer>();
		while (it.hasNext()) {
			Resources rese = it.next();
			relID.add(rese.getId());
		}
		List<Resources> resources = resource_service.findAllResource();
		model.addAttribute("resources", resources);
		model.addAttribute("role", role);
		;
		model.addAttribute("edit", true);
		model.addAttribute("relID", relID);
		return "role";
	}

	@RequestMapping(value = { "/edit-{id}-role" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String updateRole(@Valid Role role, BindingResult result, ModelMap model, @PathVariable int id)
			throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "role";
		}

		String temp = new String(role.getName().getBytes("iso-8859-1"), "utf-8");
		role.setName(temp);
		temp = new String(role.getDescription().getBytes("iso-8859-1"), "utf-8");
		role.setDescription(temp);
		role_services.removeResources(role);
		role_services.updateRole(role);
		return "redirect:/role-permission";
	}

	@RequestMapping(value = { "/delete-{id}-role" }, method = RequestMethod.GET)
	public String deleteRole(@PathVariable int id) {
		role_services.deleteRoleByID(id);
		return "redirect:/role-permission";
	}

	@RequestMapping(value = { "/query" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String queryResult(ModelMap model) {

		List<Vendor> vendors = vendor_service.findAllVendor();
		Runinfo runinfo = new Runinfo();
		model.addAttribute("runinfo", runinfo);
		model.addAttribute("vendors", vendors);
		model.addAttribute("queryflag", false);
		return "query";
	}

	@RequestMapping(value = { "/query" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String queryResult2(@Valid Runinfo runinfo, BindingResult result, ModelMap model)
			throws UnsupportedEncodingException {

		List<Vendor> vendors = vendor_service.findAllVendor();
		Map<String, String> cmap = new HashMap<String, String>();
		cmap.put("vid", Integer.toString(runinfo.getVid()));
		cmap.put("mid", runinfo.getMid());
		cmap.put("resource", runinfo.getResource());
		cmap.put("app", runinfo.getApp());

		Date[] mytime = new Date[2];

		if (runinfo.getStime() != null && runinfo.getEtime() != null) {
			mytime[0] = runinfo.getStime();
			mytime[1] = runinfo.getEtime();
		} else {
			mytime[0] = null;
			mytime[1] = null;
		}

		List<Runinfo> qresult = runinfo_services.queryData(cmap, mytime);

		if (qresult.size() > 0) {
			List<Object[]> detail = runinfo_services.translaterinfo(qresult);
			for (Object str[] : detail) {
				String path = (String) str[7];
				String bb = path.replaceAll("/", "\\\\");
				str[7] = bb;
			}

			for (int i = 0; i < detail.size(); i++) {
				Object obj[] = detail.get(i);
				String str = (String) obj[4];

				String str2[] = str.split(",");
				String res = "";
				for (int j = 0; j < str2.length; j++) {

					if (j != 0 && j % 8 == 0) {

						res = res + str2[j] + "，" + "<br/>";
					} else {
						res = res + str2[j] + "，";
					}

				}
				obj[4] = res;

				Object obj2[] = detail.get(i);
				String str3 = (String) obj[3];

				String str4[] = str3.split(",");
				String res2 = "";
				for (int j = 0; j < str4.length; j++) {

					if (j != 0 && j % 2 != 0) {
						res2 = res2 + str4[j] + "，" + "<br/>";
					} else {
						res2 = res2 + str4[j] + "，";
					}

				}
				obj2[3] = res2;

			}
			for (int i = 0; i < detail.size(); i++) {

			}

			model.addAttribute("detail", detail);
			model.addAttribute("queryflag", true);
			model.addAttribute("message", "");
		} else {
			model.addAttribute("detail", "");
			model.addAttribute("queryflag", false);
			model.addAttribute("message", "没有符合条件的记录!");
		}
		runinfo = new Runinfo();
		model.addAttribute("runinfo", runinfo);
		model.addAttribute("vendors", vendors);

		return "query";
	}

	@RequestMapping(value = { "/check" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String checkResource(ModelMap model) {

		List<Vendor> vendors = vendor_service.findAllVendor();
		Runinfo runinfo = new Runinfo();
		model.addAttribute("runinfo", runinfo);
		model.addAttribute("vendors", vendors);

		return "check";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = { "/check" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String saveRuninfo(@Valid Runinfo runinfo, BindingResult result, ModelMap model)
			throws UnsupportedEncodingException, ParseException {

		if (runinfo.getVid() == 0 || runinfo.getMid() == null || runinfo.getApp() == null
				|| runinfo.getResource() == null) {

			List<Vendor> vendors = vendor_service.findAllVendor();
			Runinfo runinfo2 = new Runinfo();
			model.addAttribute("runinfo", runinfo2);
			model.addAttribute("vendors", vendors);
			model.addAttribute("message", "本页全部为必选项,请重新选择!");
			return "check";
		} else {
			String[] tmp = getDetailInfo(runinfo);
			runinfo.setStime(tmp[2]);
			runinfo.setEtime(tmp[2]);
			runinfo.setStatus("Running");
			runinfo.setImagepath(tmp[0]);
			runinfo.setZip(tmp[1]);
			runinfo.setLogFile(tmp[3]);

			// set sid
			List infos = status_services.getOriginStatusInfo();
			int mid = Integer.parseInt(runinfo.getMid());
			Iterator it = infos.iterator();

			while (it.hasNext()) {

				Object[] temp = (Object[]) it.next();

				// get mid
				int cmid = Integer.parseInt(temp[1].toString());
				String status = temp[2].toString();
				if (mid == cmid && status.trim().equals("free")) {
					int sid = Integer.parseInt(temp[0].toString());
					runinfo.setSid(sid);
				}
			}

			runinfo_services.saveRuninfo(runinfo);

			// need runid for python program
			int runid = runinfo.getId();
			int sid = runinfo.getSid();
			TestServer server = runinfo_services.getTestServer(sid);
			Session session = runinfo_services.getSession(server.getAddress().trim(), 22, server.getUname().trim(),
					server.getPasswd().trim());
			// 执行脚本会返回执行时的信息
			String command = "nohup  python  " + Methods.getProperty("execute.python.path") + "  -n   "
					+ String.valueOf(runid) + "  &";
			runinfo_services.execShellCommand(session, command);
			// 结束本次的ssh连接
			runinfo_services.endSSH();
			model.addAttribute("istrue", true);
			String aa = runinfo.getImagepath();
			String bb = aa.replaceAll("/", "\\\\");
			runinfo.setImagepath(bb);
			model.addAttribute("runinfo", runinfo);

			return "check";
		}

	}

	@RequestMapping(value = { "/list-apps-with-{para}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Application> listAppsByStyle(@PathVariable String para, ModelMap model) {
		String[] temp = para.split("-");

		Vendor vendor = vendor_service.findById(Integer.parseInt(temp[0]));
		List<Application> apps = app_service.findApplicationByVendorIDAndStyle(vendor, temp[1]);
		return apps;
	}

	@RequestMapping(value = { "/list-mobiles-by-{vendorid}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Mobile> listMobiles(@PathVariable int vendorid, ModelMap model) {
		List<Mobile> res = new ArrayList<>();
		List<Mobile> conn = status_services.getFreeDevices();
		for (int i = 0; i < conn.size(); i++) {

			int tmp = conn.get(i).getVendor().getId();

			if (tmp == vendorid) {

				res.add(conn.get(i));
			}
		}
		return res;
	}

	@RequestMapping(value = { "/list-all-resources" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Theme> listResources(ModelMap model) {
		List<Theme> themes = theme_service.findAllTheme();
		return themes;
	}

	@RequestMapping(value = { "/list-apps-by-{vendorid}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Application> listApps(@PathVariable int vendorid, ModelMap model) {
		Vendor vendor = vendor_service.findById(vendorid);
		List<Application> apps = app_service.findApplicationByVendorID(vendor);
		return apps;
	}

	@RequestMapping(value = { "/list-all-mobiles-{vendorid}" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Mobile> listAllMobiles(@PathVariable int vendorid, ModelMap model) {
		Vendor vendor = vendor_service.findById(vendorid);
		List<Mobile> res = mobile_service.findMobileByVendor(vendor);
		return res;
	}

	// 资源管理

	@RequestMapping(value = { "/themelist-{page}" }, method = RequestMethod.GET)
	public String listThemes(ModelMap model, @PathVariable() int page,
			@ModelAttribute("searchValue") String searchValue) {
		// 每页显示多少条数据
		int pageSize = Integer.parseInt(Methods.getProperty("resource.list.page.size"));
		if (page == 0) {
			model.addAttribute("searchValue", "");
			searchValue = "";
		}

		if (searchValue.length() == 0) {
			List<Theme> allThemes = theme_service.findAllTheme();
			int totalPages = allThemes.size() % pageSize == 0 ? allThemes.size() / pageSize
					: allThemes.size() / pageSize + 1;
			int offset = 1;
			if (page <= 0) {
				offset = 0;
			} else if (page > totalPages) {
				offset = (totalPages - 1) * pageSize;
			} else {
				offset = (page - 1) * pageSize;
			}
			if (totalPages == 0) {
				totalPages = 1;
			}
			List<Theme> themes = theme_service.findThemeByPage(offset, pageSize);
			model.addAttribute("themes", themes);
			// 总页数
			model.addAttribute("totalPages", totalPages);
			// 当前的页数
			model.addAttribute("page", page);
			return "allthemes";
		}

		else {
			List<Theme> allThemes = theme_service.searchByName(searchValue);
			int totalPages = allThemes.size() % pageSize == 0 ? allThemes.size() / pageSize
					: allThemes.size() / pageSize + 1;
			int offset = 1;
			if (page <= 0) {
				offset = 0;
			} else if (page > totalPages) {
				offset = (totalPages - 1) * pageSize;
			} else {
				offset = (page - 1) * pageSize;
			}
			List<Theme> Themes = theme_service.findThemeByNameAndPage(searchValue, offset, pageSize);
			if (totalPages == 0) {
				totalPages = 1;
			}

			model.addAttribute("themes", Themes);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("page", page);
			return "allthemes";
		}

	}

	@RequestMapping(value = { "/themelist-{page}" }, method = RequestMethod.POST)
	public String searchThemes(ModelMap model, @RequestParam(value = "search", defaultValue = "") String search) {

		try {
			search = new String(search.getBytes("iso-8859-1"), "utf-8").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		}
		if (search.length() != 0) {
			model.addAttribute("searchValue", search);
			return "redirect:/themelist-1";
		} else {
			model.addAttribute("searchValue", "");
			return "redirect:/themelist-1";

		}

	}

	@RequestMapping(value = { "/newtheme-{type}" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String newTheme(ModelMap model, @PathVariable String type) {

		Theme app = new Theme();
		model.addAttribute("theme", app);
		model.addAttribute("edit", false);
		if (type.length() != 0) {
			model.addAttribute("pageType", type);
		}

		return "theme";
	}

	@RequestMapping(value = { "/newtheme" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String saveTheme(@Valid Theme theme, BindingResult result, ModelMap model, HttpServletRequest request,
			@RequestParam(value = "file", required = true) MultipartFile attach,
			@ModelAttribute("pageType") String pageType) {

		// if(type.equals("0")) {
		String name = theme.getName();
		name = name.trim();
		if (name.length() == 0 || name == null) {
			Theme app = new Theme();
			model.addAttribute("theme", app);
			model.addAttribute("edit", false);
			model.addAttribute("message", "请输入名称!");
			return "theme";
		} else {
			// 存储图片过程
			if (!attach.isEmpty()) {
				String path = Methods.getProperty("files.upload.temp.path");
				File file = new File(path);
				if (!file.exists()) {
					file.mkdirs();
				}
				String oldFileName = attach.getOriginalFilename();
				// 上传文件后缀
				// String prefix = FilenameUtils.getExtension(oldFileName);
				// 可以限制上传那文件的大小
				// int filesize = 4000000;
				// if (attach.getSize() > filesize) {
				// return false;
				// }
				Date date = new Date();
				DateFormat dateformat = new SimpleDateFormat("yyyyMMdd-HHmmss");
				String date1 = dateformat.format(date);
				oldFileName = date1 + oldFileName;

				File targetFile = new File(path, oldFileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					model.addAttribute("messagetwo", "文件上传失败!");
					Theme app = new Theme();
					model.addAttribute("theme", app);
					model.addAttribute("edit", false);
					return "theme";
				}
				// 拷贝文件到230服务器
				SSHCopyFile sshcf = new SSHCopyFile(Methods.getProperty("file.server.ip"),
						Methods.getProperty("file.server.uname"), Methods.getProperty("file.server.pwd"),
						Integer.parseInt(Methods.getProperty("file.server.port")));
				try {
					sshcf.putFile(path, oldFileName, Methods.getProperty("file.server.filepath"));
				} catch (Exception e) {
					if (sshcf != null) {
						try {
							sshcf.close();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					model.addAttribute("messagetwo", "文件上传失败!");
					Theme app = new Theme();
					model.addAttribute("theme", app);
					model.addAttribute("edit", false);
					return "theme";
				} finally {
					if (sshcf != null) {
						try {
							sshcf.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				String filePath = Methods.getProperty("file.server.filepath") + oldFileName;
				String tempFile = path + "/" + oldFileName;
				File file2 = new File(tempFile);
				file2.deleteOnExit();
				Integer maxNum = theme_service.getMaxCheckNumberByName(name);
				if (maxNum == null) {
					maxNum = 1;
				}
				theme.setChecknumber(maxNum + 1);
				theme.setName(name);
				theme.setPath(filePath);
				upload_files.saveTheme(theme);
				if (pageType.trim().equals("0")) {
					return "redirect:/themelist-0";

				} else {
					return "redirect:/check";
				}

			} else {
				Theme app = new Theme();
				model.addAttribute("theme", app);
				model.addAttribute("edit", false);
				model.addAttribute("messagetwo", "文件上传失败!");
				return "theme";
			}

		}

	}

	@RequestMapping(value = { "/edit-{id}-theme" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
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
		return "redirect:/themelist-1";
	}

	@RequestMapping(value = { "/delete-{id}-theme-{page}" }, method = RequestMethod.GET)
	public String deleteTheme(@PathVariable int id, @PathVariable String page) {
		// 此方法用于删除文件服务器上的对应文件--暂时保留
//		SSHCopyFile sshcf = new SSHCopyFile(Methods.getProperty("file.server.ip"),
//				Methods.getProperty("file.server.uname"), Methods.getProperty("file.server.pwd"),
//				Integer.parseInt(Methods.getProperty("file.server.port")));
//		Theme theme = theme_service.findById(id);
//		try {
//			sshcf.deleteFileOrDirector(theme.getPath());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (sshcf != null) {
//				try {
//					sshcf.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}

		theme_service.deleteThemeByID(id);
		return "redirect:/themelist-" + page;
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

	// 厂商管理

	@RequestMapping(value = { "/vendorlist" }, method = RequestMethod.GET)
	public String listVendors(ModelMap model) {

		List<Vendor> vendors = vendor_service.findAllVendor();
		model.addAttribute("vendors", vendors);
		return "allvendors";
	}

	@RequestMapping(value = { "/newvendor" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String newvendor(ModelMap model) {
		Vendor vendor = new Vendor();
		model.addAttribute("vendor", vendor);
		model.addAttribute("edit", false);
		return "vendor";
	}

	@RequestMapping(value = { "/newvendor" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String saveVendor(@Valid Vendor vendor, BindingResult result, ModelMap model)
			throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "vendor";
		}

		String temp = new String(vendor.getName().getBytes("iso-8859-1"), "utf-8");
		vendor.setName(temp);
		vendor_service.saveVendor(vendor);
		return "redirect:/vendorlist";
	}

	@RequestMapping(value = { "/edit-{id}-vendor" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String editVendor(@PathVariable int id, ModelMap model) {
		Vendor vendor = vendor_service.findById(id);
		model.addAttribute("vendor", vendor);
		model.addAttribute("edit", true);
		return "vendor";
	}

	@RequestMapping(value = { "/edit-{id}-vendor" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String updateVendor(@Valid Vendor vendor, BindingResult result, ModelMap model, @PathVariable int id)
			throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "vendor";
		}

		String temp = new String(vendor.getName().getBytes("iso-8859-1"), "utf-8");
		vendor.setName(temp);
		vendor_service.updateVendor(vendor);
		return "redirect:/vendorlist";
	}

	@RequestMapping(value = { "/delete-{id}-vendor" }, method = RequestMethod.GET)
	public String deleteVendor(@PathVariable int id) {
		vendor_service.deleteVendorByID(id);
		return "redirect:/vendorlist";
	}

	// 手机管理

	@RequestMapping(value = { "/newmobile" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String newMobile(ModelMap model) {
		Mobile app = new Mobile();
		model.addAttribute("mobile", app);
		model.addAttribute("edit", false);
		return "mobile";
	}

	@RequestMapping(value = { "/mobilelist-{page}" }, method = RequestMethod.GET)
	public String listMobiles(ModelMap model, @PathVariable() int page) {
		// 每页显示多少个手机
		int pageSize = Integer.parseInt(Methods.getProperty("mobile.management.page.size"));
		List<Mobile> allMobiles = mobile_service.findAllMobile();
		int totalPages = allMobiles.size() % pageSize == 0 ? allMobiles.size() / pageSize
				: allMobiles.size() / pageSize + 1;
		int offset = 1;
		if (page <= 0) {
			offset = 0;
		} else if (page > totalPages) {
			offset = (totalPages - 1) * pageSize;
		} else {
			offset = (page - 1) * pageSize;
		}
		List<Mobile> mobiles = mobile_service.findMobileByPage(offset, pageSize);
		model.addAttribute("mobiles", mobiles);
		// 总页数
		if (totalPages == 0) {
			totalPages = 1;
		}
		model.addAttribute("totalPages", totalPages);
		// 当前的页数
		model.addAttribute("page", page);

		return "allmobiles";
	}

	@RequestMapping(value = { "/newmobile" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String saveMobile(@Valid Mobile mobile, BindingResult result, ModelMap model)
			throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "mobile";
		}

		if (!mobile_service.isMobileUidUnique(mobile.getId(), mobile.getUid())) {
			FieldError ssnError = new FieldError("mobile", "uid",
					messageSource.getMessage("non.unique.uid", new String[] { mobile.getUid() }, Locale.getDefault()));
			result.addError(ssnError);
			return "mobile";
		}

		String temp = new String(mobile.getName().getBytes("iso-8859-1"), "utf-8");
		// delete spaces
		mobile.setName(temp.trim().replace(" ", ""));
		mobile_service.saveMobile(mobile);
		return "redirect:/mobilelist-1";
	}

	@RequestMapping(value = { "/edit-{uid}-mobile" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String editmobile(@PathVariable String uid, ModelMap model) {
		Mobile mobile = mobile_service.findMobileByUid(uid);
		String vname = mobile.getVendor().getName();
		model.addAttribute("vname", vname);
		model.addAttribute("mobile", mobile);
		model.addAttribute("edit", true);
		return "mobile";
	}

	@RequestMapping(value = { "/edit-{uid}-mobile" }, method = RequestMethod.POST)
	public String updatemobile(@Valid Mobile mobile, BindingResult result, ModelMap model)
			throws UnsupportedEncodingException {
		if (result.hasErrors()) {
			return "mobile";
		}

		if (!mobile_service.isMobileUidUnique(mobile.getId(), mobile.getUid())) {
			FieldError ssnError = new FieldError("mobile", "uid",
					messageSource.getMessage("non.unique.uid", new String[] { mobile.getUid() }, Locale.getDefault()));
			result.addError(ssnError);
			return "mobile";
		}

		String temp = new String(mobile.getName().getBytes("iso-8859-1"), "utf-8");
		mobile.setName(temp.trim().replace(" ", ""));

		mobile_service.updateMobile(mobile);

		return "redirect:/mobilelist-1";
	}

	@RequestMapping(value = { "/delete-{uid}-mobile-{page}" }, method = RequestMethod.GET)
	public String deleteMobile(@PathVariable String uid, @PathVariable String page) {
		mobile_service.deleteMobileByUid(uid);
		return "redirect:/mobilelist-" + page;
	}

	// 服务器管理

	public String getErrorString(BindingResult bindingResult) {

		List<FieldError> err = bindingResult.getFieldErrors();
		FieldError fe;
		String field;
		String errorMessage;
		StringBuffer buffer = new StringBuffer("");
		String temp;
		for (int i = 0; i < err.size(); i++) {
			fe = err.get(i);
			field = fe.getField();
			errorMessage = fe.getDefaultMessage();
			temp = field + " : " + errorMessage;
			buffer.append(temp);
		}
		String errors = buffer.toString();
		return errors;
	}

	@RequestMapping(value = { "/serverlist" }, method = RequestMethod.GET)
	public String listServers(ModelMap model) {

		List<TestServer> servers = tserver_service.findAllTestServer();
		model.addAttribute("servers", servers);
		return "allservers";
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
	public String saveServer(@Valid TestServer server, BindingResult result, ModelMap model)
			throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			String errors = getErrorString(result);
			model.addAttribute("errorInfo", errors);
			model.addAttribute("server", server);
			model.addAttribute("edit", false);
			return "addserver";
		}

		if (!tserver_service.isTestServerSsnUnique(server.getId(), server.getSsn())) {
			FieldError ssnError = new FieldError("server", "ssn",
					messageSource.getMessage("non.unique.ssn", new String[] { server.getSsn() }, Locale.getDefault()));
			result.addError(ssnError);
			model.addAttribute("errorInfo", ssnError.getDefaultMessage());
			model.addAttribute("server", server);
			model.addAttribute("edit", false);
			return "addserver";
		}
		String temp = new String(server.getSsn().getBytes("iso-8859-1"), "utf-8");
		server.setSsn(temp);
		tserver_service.saveTestServer(server);

		return "redirect:/serverlist";
	}

	@RequestMapping(value = {
			"/edit-{ssn}-testserver" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String editServer(@PathVariable String ssn, ModelMap model) {
		TestServer server = tserver_service.findTestServerBySsn(ssn);
		model.addAttribute("server", server);
		model.addAttribute("edit", true);
		return "addserver";
	}

	@RequestMapping(value = { "/edit-{ssn}-testserver" }, method = RequestMethod.POST)
	public String updateServer(@Valid TestServer server, BindingResult result, ModelMap model)
			throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "addserver";
		}

		if (!tserver_service.isTestServerSsnUnique(server.getId(), server.getSsn())) {
			FieldError ssnError = new FieldError("server", "ssn",
					messageSource.getMessage("non.unique.ssn", new String[] { server.getSsn() }, Locale.getDefault()));
			result.addError(ssnError);
			model.addAttribute("server", server);
			model.addAttribute("edit", true);
			return "addserver";
		}

		String temp = new String(server.getSsn().getBytes("iso-8859-1"), "utf-8");
		server.setSsn(temp);

		tserver_service.updateTestServer(server);

		return "redirect:/serverlist";
	}

	@RequestMapping(value = { "/delete-{ssn}-testserver" }, method = RequestMethod.GET)
	public String deleteTestServer(@PathVariable String ssn) {
		tserver_service.deleteTestServerBySsn(ssn);
		return "redirect:/serverlist";
	}

	// 应用管理

	@RequestMapping(value = { "/applicationlist-{page}-{vendorid}" }, method = RequestMethod.GET)
	public String listApplications(ModelMap model, @PathVariable int page, @PathVariable String vendorid,
			@ModelAttribute("tvendorid") String tvendorid) {
		// 每页显示的数量
		int pageSize = Integer.parseInt(Methods.getProperty("application.management.page.size"));
		if (page == 0) {
			model.addAttribute("tvendorid", "0");
			tvendorid = "0";
		}
		if (!vendorid.equals("0")) {
			model.addAttribute("tvendorid", vendorid);
			tvendorid = vendorid;
		}

		if (tvendorid.equals("0")) {
			List<Application> applications = app_service.findAllApplication();
			int totalPages = applications.size() % pageSize == 0 ? applications.size() / pageSize
					: applications.size() / pageSize + 1;
			int offset = 1;
			if (page <= 0) {
				offset = 0;
			} else if (page > totalPages) {
				offset = (totalPages - 1) * pageSize;
			} else {
				offset = (page - 1) * pageSize;
			}
			List<Application> application = app_service.findApplicationByPage(offset, pageSize);
			model.addAttribute("applications", application);
			if (totalPages == 0) {
				totalPages = 1;
			}
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("page", page);
			return "allapplications";
		} else {

			int venid = Integer.parseInt(tvendorid.trim());
			Vendor vendor = vendor_service.findById(venid);
			List<Application> apps = app_service.findApplicationByVendorID(vendor);
			int totalPages = apps.size() % pageSize == 0 ? apps.size() / pageSize : apps.size() / pageSize + 1;
			int offset = 1;
			if (page <= 0) {
				offset = 0;
			} else if (page > totalPages) {
				offset = (totalPages - 1) * pageSize;
			} else {
				offset = (page - 1) * pageSize;
			}
			List<Application> applica = app_service.findApplicationByVendorIDaAndPage(vendor, offset, pageSize);
			if (totalPages == 0) {
				totalPages = 1;
			}
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("page", page);
			model.addAttribute("applications", applica);
			return "allapplications";

		}

	}

	@RequestMapping(value = { "/newapplication" }, method = RequestMethod.GET)
	public String newApplication(ModelMap model) {
		Application app = new Application();
		model.addAttribute("application", app);
		model.addAttribute("edit", false);
		return "application";
	}

	@RequestMapping(value = { "/newapplication" }, method = RequestMethod.POST)
	public String saveApplication(@Valid Application app, BindingResult result, ModelMap model)
			throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "application";
		}
		String temp = new String(app.getName().getBytes("iso-8859-1"), "utf-8");
		app.setName(temp);
		app_service.saveApplication(app);
		return "redirect:/applicationlist-0-0";
	}

	@RequestMapping(value = {
			"/edit-{id}-application" }, method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String editApplication(@PathVariable int id, ModelMap model) {
		Application app = app_service.findById(id);
		String vname = app.getVendor().getName();
		model.addAttribute("vname", vname);
		model.addAttribute("application", app);
		model.addAttribute("edit", true);
		return "application";
	}

	@RequestMapping(value = {
			"/edit-{id}-application" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String updateApplication(@Valid Application app, BindingResult result, ModelMap model, @PathVariable int id)
			throws UnsupportedEncodingException {

		if (result.hasErrors()) {
			return "application";
		}

		String temp = new String(app.getName().getBytes("iso-8859-1"), "utf-8");
		app.setName(temp);
		app_service.updateApplication(app);

		return "redirect:/applicationlist-0-0";
	}

	@RequestMapping(value = { "/delete-{id}-application-{page}" }, method = RequestMethod.GET)
	public String deleteApplication(@PathVariable int id, @PathVariable String page) {
		app_service.deleteApplicationByID(id);
		return "redirect:/applicationlist-" + page + "-0";
	}

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("logSuccessUser");
		if (user != null) {
			return "redirect:/home";
		}
		return "login";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String assertLogIn(ModelMap model, String logname, String logpass) {
		if (logname.trim().length() != 0 && user_services.findByName(logname)) {

			List<User> list = user_services.findUserByName(logname);
			User user2 = list.get(0);
			if (user2.getPasswd().trim().equals(logpass)) {
				user2.setLasted_update(new Date());
				user_services.updateUserLastLogin(user2);
				;
				model.addAttribute("logSuccessUser", user2);
				model.addAttribute("searchValue", "");
				model.addAttribute("tvendorid", "0");
				model.addAttribute("pageType", "");

				return "redirect:/home";
			} else {
				model.addAttribute("message", "帐号或密码错误，请重新输入");
				return "login";
			}

		} else {
			model.addAttribute("message", "帐号或密码错误，请重新输入");
			return "login";
		}

	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logOut(HttpSession session, SessionStatus sessionStatus) {
		session.removeAttribute("logSuccessUser");
		session.removeAttribute("pageType");
		session.removeAttribute("searchValue");
		session.removeAttribute("tvendorid");
		session.invalidate();
		sessionStatus.setComplete();

		return "redirect:/login";
	}

	// 注册
	@RequestMapping(value = { "/signin" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String signIn(ModelMap model, String signinemail, String signinname, String signpasswd) {

		if (signinname.trim().length() != 0 && signpasswd.trim().length() != 0 && signinemail.trim().length() != 0
				&& !user_services.findByName(signinname)) {
			User user = new User();
			user.setEmail(signinemail);
			user.setIs_active(0);
			user.setName(signinname);
			user.setPasswd(signpasswd);
			Date date = new Date();
			user.setJoined_date(date);
			user.setLasted_update(date);
			user_services.saveUser(user);

			if (user_services.findByName(signinname)) {
				return "恭喜!注册成功,请登录!";
			} else {
				return "注册失败,请重试!";
			}
		} else {
			return "用户名已存在,注册失败。";
		}

	}

	@RequestMapping(value = { "/findpwd" }, method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String findPasswd(ModelMap model, String findpasswdname, String findpasswdemail) {
		if (findpasswdname.trim().length() != 0 && user_services.findByName(findpasswdname)) {

			List<User> list = user_services.findUserByName(findpasswdname);
			User user = list.get(0);
			String email = user.getEmail().trim();
			if (email.equals(findpasswdemail.trim())) {

				String emailSubject = "自动化测试平台密码找回邮件";
				String messageBody = "您的登录密码为: " + user.getPasswd() + "    (系统自动发送,切勿回复!)";
				try {
					SendEmailMethods.sendEmail(email, emailSubject, messageBody, null);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return "邮件发送失败,请联系管理人员";
				}
				return "密码找回成功,登录密码已发送到您的邮箱,注意查收";

			} else {
				return "抱歉,输入的邮箱地址与账号绑定的不一致";
			}

		}

		else {
			return "抱歉,密码找回失败,用户名不存在.";
		}

	}

}
