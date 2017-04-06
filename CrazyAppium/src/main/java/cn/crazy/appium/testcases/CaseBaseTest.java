package cn.crazy.appium.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.AndroidSpecific;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.main.AppiumInit;
import cn.crazy.appium.page.HomePage;
import cn.crazy.appium.page.LoginPage;
import cn.crazy.appium.util.ProUtil;

public class CaseBaseTest {
    //实例化driver
	public AndroidDriverBase driverInit(String udid, String port)
			throws Exception {
		ProUtil p = new ProUtil(CrazyPath.globalPath);
		String server=p.getPro("server");
		String capsPath=CrazyPath.capsPath;
		AndroidSpecific as=new AndroidSpecific();
		String input=as.getDefaultInput(udid);
		System.out.println("连接"+udid+"端口"+port);
		System.out.println("开始创建server连接");
		//如果你要启动已经安装了的应用那么最后一个参数传true
		return new AndroidDriverBase(server, port, capsPath, udid, input,true);
		
	}
}
