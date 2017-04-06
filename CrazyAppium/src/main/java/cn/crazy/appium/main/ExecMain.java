package cn.crazy.appium.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import cn.crazy.appium.server.Port;
import cn.crazy.appium.server.Servers;
import cn.crazy.appium.util.DosCmd;
import cn.crazy.appium.util.FileUtil;
import cn.crazy.appium.util.XmlUtil;

public class ExecMain {
	private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";  
	private static String path=System.getProperty("user.dir");

	public static void main(String[] args) throws Exception {
		System.setProperty(ESCAPE_PROPERTY, "false"); 
		AppiumInit.init();
        List<String> suites = new ArrayList<String>();
        suites.add(System.getProperty("user.dir")+"/testng.xml");
        TestNG tng = new TestNG();
        tng.setTestSuites(suites);
        tng.run();
//		   String encoding = System.getProperty("file.encoding");
//	        System.out.println("Default System Encoding: " + encoding);
		//tt.runSuitesLocally();
	}
}
