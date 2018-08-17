package com.vlife.clienttest.utils;

/**
 * @author: 高亚轩
 * @date:2018年8月16日 上午10:48:28 本类是根据AdbMethods.java HttpMethods.java等类封装的一些业务方法
 */
public class AndroidLog {

	/**
	 * 实例化的时候会对device 截取second时间的log --请确认截取的这段时间的log包含你需要验证的信息
	 * 
	 * @param device device
	 * @param second 截取log的时长
	 */
	public AndroidLog(String device, int second) {
		log = AdbMethods.getLog(device, second);
	}

	/**
	 * 截取到的log
	 */
	private String[] log;

	/**
	 * 从log中过滤出uid
	 * 
	 * @return String类型的uid
	 */
	public String getUid() {
		String result = "";
		for (int i = 0; i < log.length; i++) {
			if (log[i].contains("key:uid") && !log[i].contains("value:null")) {
				result = log[i].substring(log[i].indexOf("value:") + 6);
				break;
			}

		}
		return result;
	}

	/**
	 * 判断C升级是否成功
	 * 
	 * @return true 完成C升级 false 没有完成
	 */
	public Boolean assertCUpdateSuccess() {
		String keyWord = "onFinish:/data/data/com.vlife.vivo.wallpaper/databases/systemp";
		Boolean status = false;
		for (String temp : log) {
			if (temp.contains(keyWord)) {
				status = true;
				break;
			}
		}

		return status;
	}

}
