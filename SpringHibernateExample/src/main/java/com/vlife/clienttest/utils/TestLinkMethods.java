package com.vlife.clienttest.utils;

import java.net.MalformedURLException;
import java.net.URL;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.Build;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;

public class TestLinkMethods {
	TestLinkAPI api = null;
	URL testlinkURL = null;
	TestPlan plan = null;

	public TestLinkMethods(String url, String testLinkKey) {

		try {
			testlinkURL = new URL(url);
			api = new TestLinkAPI(testlinkURL, testLinkKey);
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	public void setPlan(String planName, String projectName) {
		plan = api.getTestPlanByName(planName, projectName);

	}

	// 获取所有的版本
	public Build[] getbuild() {
		return api.getBuildsForTestPlan(plan.getId());
	}

	// 获取CaeID
	public Integer getCaseID(String testCaseName, String testSuiteName, String projectName) {
		return api.getTestCaseIDByName(testCaseName, testSuiteName, projectName, null);
	}

	// 设置Case的结果 通过
	public void SetCaseResultPass(Integer caseID, Integer planID, String buildName, String postscript) {
		api.reportTCResult(caseID, null, planID, ExecutionStatus.PASSED, null, buildName, postscript, null, null, null,
				null, null, null);
	}

	// block
	public void SetCaseResultBlock(Integer caseID, Integer planID, String buildName, String postscript) {
		api.reportTCResult(caseID, null, planID, ExecutionStatus.BLOCKED, null, buildName, postscript, null, null, null,
				null, null, null);
	}

	// Fail
	public void SetCaseResultFail(Integer caseID, Integer planID, String buildName, String postscript) {
		api.reportTCResult(caseID, null, planID, ExecutionStatus.FAILED, null, buildName, postscript, null, null, null,
				null, null, null);
	}

	// NotRun
	public void SetCaseResultNotRun(Integer caseID, Integer planID, String buildName, String postscript) {
		api.reportTCResult(caseID, null, planID, ExecutionStatus.NOT_RUN, null, buildName, postscript, null, null, null,
				null, null, null);
	}

	//

}
