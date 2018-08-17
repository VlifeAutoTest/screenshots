package com.vlife.clienttest.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.vlife.checkserver.mobilestatus.SshCopyFile;

/**
 * @author: 高亚轩
 * @date:2018年8月16日 下午6:09:28
 */
public class TestCaes {
	// **************TestLink的参数********************
	String testlinkURL = "http://testlink.vlife.com/lib/api/xmlrpc/v1/xmlrpc.php";
	String testlinkKEY = "b683e89cca796958b9481231f655261d";
	String projectName = "operation";
	String planName = "BigDataTest";
	String buildName = "aututest-test1";
	String testSuiteName = "总控";

	public String CaseOne(String device) throws InterruptedException {

		String packageName = "com.vlife.vivo.wallpaper";
		String packageNameAndActivity = " com.vlife.vivo.wallpaper/com.vlife.TService";
		String APPName[] = { "998_operation-b-1723.apk",
				"998_vlife-sdk-module-vivo-release_minify-unsigned-5.167-log.apk", "998_VLife_vivo_stage_5.102.7.apk" };
		String serverFilePath = "/diskb/clientTestApp";
		if (AdbMethods.isConnect(device)) {

			AdbMethods.unLockPhone(device, 237, 822, 256, 300, 200);

			TestLinkMethods linkMethods = new TestLinkMethods(testlinkURL, testlinkKEY);
			int planID = linkMethods.getPlanID(planName, projectName);
			String temppath = "";
			if (OtherMethods.isOSLinux()) {
				temppath = "/temp/clientfiles/";
			} else {
				temppath = "C:\\clientfiles\\";
			}
			File file = new File(temppath);
			if (!file.exists()) {
				file.mkdirs();
			}
			AdbMethods.uninstallAPP(device, packageName);
			SshCopyFile copyFile = new SshCopyFile("192.168.1.230", "root", "vlifeqa", 22);
			for (String name : APPName) {
				copyFile.downloadFile(serverFilePath, name, temppath);
				AdbMethods.installAPP(device, temppath + name);

				// AdbMethods.getJMXX(device, "/sdcard/autotest.xml");
				// AdbMethods.pull(device, "/sdcard/autotest.xml", temppath+"autotest.xml");
				// ResolverXml resolverXml =new ResolverXml();
				// Element element =resolverXml.assignXML(temppath+"autotest.xml");
				// resolverXml.getNeedElement(element, "", value)
				int aa[] = { 154, 922 };
				AdbMethods.tap(device, aa);
				System.out.println(name);
				Thread.sleep(20000);
			}
			copyFile.close();
			// 启动总控
			AdbMethods.shutdownAPP(device, packageName);
			AdbMethods.clearAPP(device, packageName);
			AdbMethods.startService(device, packageNameAndActivity);

			AndroidLog androidLog = new AndroidLog(device, 20);
			String uid = androidLog.getUid();
			if (uid.trim().length() == 0) {
				return "";
			}
			System.out.println(uid);
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(uid);
			// 正则表达式匹配是个数字证明服务启动成功,成功获取到uid

			// 第一个Case
			int caseID = linkMethods.getCaseID("客户端启动运营版本", testSuiteName, projectName);
			if (isNum.matches()) {
				linkMethods.setCaseResultPass(caseID, planID, buildName, "成功启动总控");
			} else {
				linkMethods.setCaseResultFail(caseID, planID, buildName, "uid获取失败");
				return "";
			}
			// 第二个Case
			int caseID2 = linkMethods.getCaseID("新运营C版本升级", testSuiteName, projectName);
			if (androidLog.assertCUpdateSuccess()) {
				linkMethods.setCaseResultPass(caseID2, planID, buildName, "C升级成功");
			} else {
				linkMethods.setCaseResultFail(caseID2, planID, buildName, "C升级失败");
			}

			Thread.sleep(120 * 1000);
			// 第三个Case
			int caseID3 = linkMethods.getCaseID("发送可运营信息", testSuiteName, projectName);
			JSONObject jsonObject = HttpMethods.serverAssert(uid);
			String adbNum = JsonMethds.getRequestAdbEnabled(jsonObject);
			String rootNum = JsonMethds.getRequestDeviceRootedt(jsonObject);
			String logNum = JsonMethds.getRequestSystemLogger(jsonObject);
			String time = JsonMethds.getRequesTtime(jsonObject);
			Boolean rooted = AdbMethods.isRooted(device);
			int rootedNum = 0;
			if (rooted) {
				rootedNum = 1;
			}

			if (adbNum.equals("1") && rootNum.equals(String.valueOf(rootNum)) && logNum.equals("0")) {
				linkMethods.setCaseResultPass(caseID3, planID, buildName, "对返回的json校验成功");
			} else {
				linkMethods.setCaseResultFail(caseID3, planID, buildName, "对返回的json校验失败");
			}

			// 校验下个联网周期
			int caseID4 = linkMethods.getCaseID("下个联网周期查看是否再次上发可运营信息", testSuiteName, projectName);
			try {
				Thread.sleep(5 * 60 * 1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			JSONObject jsonObject2 = HttpMethods.serverAssert(uid);
			String time2 = JsonMethds.getRequesTtime(jsonObject2);
			if (time.equals(time2)) {
				linkMethods.setCaseResultFail(caseID4, planID, buildName,
						"两次获取到的时间一样,第一次发送时间为:" + OtherMethods.getTimestamp() + "获取到的值为:" + time + "第二次发送时间为:"
								+ OtherMethods.getTimestamp() + "获取到的值为:" + time2);
			} else {
				linkMethods.setCaseResultPass(caseID4, planID, buildName, "两次获取到的time值不一致!!");
			}

		} else {

			System.out.println("没检测到手机!");
		}
		return device;

	}

}
