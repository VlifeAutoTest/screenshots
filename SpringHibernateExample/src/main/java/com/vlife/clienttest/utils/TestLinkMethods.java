package com.vlife.clienttest.utils;

import java.net.MalformedURLException;
import java.net.URL;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.Build;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;

/**
 * 封装的连接TestLink的一些方法
 * 
 * @author 高亚轩
 *
 */
public class TestLinkMethods {
	TestLinkAPI api = null;
	URL testlinkURL = null;
	TestPlan plan = null;

	/**
	 * 构造函数
	 * 
	 * @param url         testlink的api的地址
	 * @param testLinkKey 需要的口令
	 */
	public TestLinkMethods(String url, String testLinkKey) {

		try {
			testlinkURL = new URL(url);
			api = new TestLinkAPI(testlinkURL, testLinkKey);
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 设置那个plan下的
	 * 
	 * @param planName    计划的名称
	 * @param projectName 产品名称
	 */
	public void setPlan(String planName, String projectName) {
		plan = api.getTestPlanByName(planName, projectName);

	}

	/**
	 * 此方法需要在setPlan后调用 ,用来获取设置的plan下的所有版本
	 * 
	 * @return 所有版本的对象的数组
	 */
	public Build[] getbuild() {
		return api.getBuildsForTestPlan(plan.getId());
	}

	public int getPlanID(String planName, String projectName) {
		TestPlan plan = api.getTestPlanByName(planName, projectName);
		return plan.getId();
	}

	/**
	 * 获取CaeID
	 * 
	 * @param testCaseName  Case的名称
	 * @param testSuiteName Suite的名称
	 * @param projectName   产品的名称
	 * @return 返回Case 的ID
	 */
	public Integer getCaseID(String testCaseName, String testSuiteName, String projectName) {
		return api.getTestCaseIDByName(testCaseName, testSuiteName, projectName, null);
	}

	/**
	 * 设置Case的结果 通过
	 * 
	 * @param caseID     case的ID
	 * @param planID     plan的ID
	 * @param buildName  build名称
	 * @param postscript 备注信息
	 */
	public void setCaseResultPass(Integer caseID, Integer planID, String buildName, String postscript) {
		api.reportTCResult(caseID, null, planID, ExecutionStatus.PASSED, null, buildName, postscript, null, null, null,
				null, null, null);
	}

	/**
	 * 设置Case的结果 BLOCKED
	 * 
	 * @param caseID     case的ID
	 * @param planID     plan的ID
	 * @param buildName  build名称
	 * @param postscript 备注信息
	 */
	public void setCaseResultBlock(Integer caseID, Integer planID, String buildName, String postscript) {
		api.reportTCResult(caseID, null, planID, ExecutionStatus.BLOCKED, null, buildName, postscript, null, null, null,
				null, null, null);
	}

	/**
	 * 设置Case的结果 FAILED
	 * 
	 * @param caseID     case的ID
	 * @param planID     plan的ID
	 * @param buildName  build名称
	 * @param postscript 备注信息
	 */
	public void setCaseResultFail(Integer caseID, Integer planID, String buildName, String postscript) {
		api.reportTCResult(caseID, null, planID, ExecutionStatus.FAILED, null, buildName, postscript, null, null, null,
				null, null, null);
	}

	/**
	 * 设置Case的结果 NOT_RUN
	 * 
	 * @param caseID     case的ID
	 * @param planID     plan的ID
	 * @param buildName  build名称
	 * @param postscript 备注信息
	 */
	public void setCaseResultNotRun(Integer caseID, Integer planID, String buildName, String postscript) {
		api.reportTCResult(caseID, null, planID, ExecutionStatus.NOT_RUN, null, buildName, postscript, null, null, null,
				null, null, null);
	}

}
