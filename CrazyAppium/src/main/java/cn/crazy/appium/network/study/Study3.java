package cn.crazy.appium.network.study;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.page.HomePage;
import cn.crazy.appium.page.LoginPage;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ProUtil;
import cn.crazy.appium.util.SendMail;

public class Study3 {
	AndroidDriverBase driver;
	
	@Parameters({"udid","port"})
	@BeforeClass
	public void beforeClass(String udid,String port){
		DriverInit di=new DriverInit();
		SendMail sm=new SendMail();
		try {
			driver=di.driverInit(udid, port);
			driver.implicitlyWait(5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   	ProUtil p=new ProUtil(CrazyPath.globalPath);
	    	String[] to=p.getPro("tomail").split(",");
			sm.send("driver初始化失败",udid+"设备初始化driver失败", to);
		}
	}
	@Test
	public void login(){
		LoginPage lp=new LoginPage(driver);
		HomePage hp=lp.login("crazysand_001@163.com", "12345678");
		hp.clickIngroe();
		Assert.assertEquals(driver.getPageSouce().contains("首页"), true);
	}
	@Test(dependsOnMethods="login")
	public void addAnswer(){
		HomePage hp=new HomePage(driver);
		hp.addAnswer();
	}
	@AfterClass
	public void afterClass(){
		driver.quit();
	}
}
