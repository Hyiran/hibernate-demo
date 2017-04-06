package cn.crazy.appium.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.page.PersonInofPage;

public class XinChangTaiTest extends CaseBaseTest {
	public AndroidDriverBase driver;
	@BeforeClass
	@Parameters({ "udid", "port" })
	public void beforeClass(String udid, String port) {
		try {
			System.out.println("读到的udid是："+udid+"读到的port是："+port);
			driver=driverInit(udid, port);
			driver.implicitlyWait(10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("执行测试testtest");
	}
	@Test
	public void personInfo(){
		System.out.println("执行资料修改");
		PersonInofPage xct=new PersonInofPage(driver);
		xct.personChange();
	}
	@AfterClass
	public void afterTest() {
		//System.out.println("结束测试testtest");
		//driver.resetApp();
		driver.quit();
	}
}
