package cn.crazy.appium.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.page.LoginPage;
import cn.crazy.appium.testng.TestngRetry;
import cn.crazy.appium.util.ProUtil;

public class RegTest extends CaseBaseTest{
	public AndroidDriverBase driver;
	@BeforeClass
	@Parameters({ "udid", "port" })
	public void beforeTest(String udid, String port) {
		try {
			System.out.println("读到的udid是："+udid+"读到的port是："+port);
			driver=driverInit(udid, port);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("执行测试testtest");
	}
	@AfterClass
	public void afterTest() {
		//System.out.println("结束测试testtest");
		driver.resetApp();
		driver.quit();
	}
	@Test(retryAnalyzer=TestngRetry.class)
	public void reg(){
		LoginPage lp=new LoginPage(driver);
		lp.regTest("crazysand_004@163.com", "123456", "xxxxx");
		driver.wait(3000);
		Assert.assertEquals(driver.getPageSouce().contains("优秀"), true);
	}
	@AfterMethod
	public void afterMethod(){
		ProUtil p=new ProUtil("configs/caps.properties");
		driver.startActivity(p.getPro("appPackage"), p.getPro("appActivity"));
	}
}
