package cn.crazy.appium.network.study;

import cn.crazy.appium.server.Port;
import cn.crazy.appium.server.Servers;
import cn.crazy.appium.util.DosCmd;

public class TestMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Servers server=new Servers(new Port(new DosCmd()),new DosCmd());
		new DosCmd().killServer();
		server.startServers();

	}

}
