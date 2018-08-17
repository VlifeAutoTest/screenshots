package com.vlife.clienttest.utils;

/**
 * 本类只是用于不通过web方式调整业务逻辑
 * 
 * @author 高亚轩
 */
public class DoMain {

	public static void main(String[] args) throws Exception {
		String device = "e950b56a";
		TestCaes caes = new TestCaes();
		caes.CaseOne(device);

	}

}
