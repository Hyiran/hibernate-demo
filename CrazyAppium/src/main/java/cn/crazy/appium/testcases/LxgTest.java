package cn.crazy.appium.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.page.LxgPage;

public class LxgTest extends CaseBaseTest{
	public AndroidDriverBase driver;
	@Test
	public void signTest(){
		LxgPage lxgpage=new LxgPage(driver);
		lxgpage.signIn("xiaoxiao", "123456");
		Assert.assertEquals(driver.getPageSouce().concat("签到成功"), true);
	}
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
}
