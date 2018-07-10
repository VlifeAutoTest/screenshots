package com.vlife.checkserver.mobilestatus;

import com.jcraft.jsch.Session;

public class DoMain {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

		
		
		Methods m =new Methods();
		Session session =m.getSession("10.0.11.0", 22, "lang", "963852");
		String [] str =m.getDevices(session);
		for(String aa :str) {
			System.out.println(aa);
		}
	}

}
