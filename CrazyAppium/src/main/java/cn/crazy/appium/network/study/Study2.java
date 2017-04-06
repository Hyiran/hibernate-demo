package cn.crazy.appium.network.study;

import java.io.IOException;
import java.util.List;

import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;











import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.datadriver.ExcelUtil;
import cn.crazy.appium.page.HomePage;
import cn.crazy.appium.page.LoginPage;
import cn.crazy.appium.testng.Assertion;
import cn.crazy.appium.testng.TestngRetry;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ProUtil;
import cn.crazy.appium.util.RandomUtil;
import cn.crazy.appium.util.SendMail;

public class Study2 {
	AndroidDriverBase driver;
	
	@Parameters({"udid","port"})
	@BeforeClass
	public void beforeClass(String udid,String port){
		DriverInit di=new DriverInit();
		SendMail sm=new SendMail();
		try {
			driver=di.driverInit(udid, port);
			//driver.implicitlyWait(5);
			driver.wait(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   	ProUtil p=new ProUtil(CrazyPath.globalPath);
	    	String[] to=p.getPro("tomail").split(",");
			sm.send("driver初始化失败",udid+"设备初始化driver失败", to);
		}
	}
	@DataProvider
	public Object[][] loginData() throws Exception{
		Object[][] data={{"crazysand_001@163.com","123456","登录","pwdError.png"},{"crazysand_01@163.com","123456","登录","userError.png"},{"crazysand_001@163.com","12345678","首页","correct.png"}};
		//Object[][] data=ExcelUtil.getTestData("C:\\Users\\lixionggang\\Desktop/test.xlsx", "Sheet1");//{{"crazysand_001@163.com","123456","登录","pwdError.png"},{"crazysand_01@163.com","123456","登录","userError.png"},{"crazysand_001@163.com","12345678","首页","correct.png"}};
		return data;
	}
	//@Test(dataProvider="loginData")
	public void login(){
		int flag=3;
		while(flag>0){
			driver.findElement(GetByLocator.getLocator("loginOrReg")).click();
			driver.findElement(GetByLocator.getLocator("username")).sendKeys("crazysand_001@163.com");
			driver.findElement(GetByLocator.getLocator("password")).sendKeys("1234567");
			driver.findElement(GetByLocator.getLocator("loginbtn")).click();
			//driver.findElement(GetByLocator.getLocator("ingroe")).click();
			driver.wait(5000);
			Assertion at=new Assertion(driver);
			try {
				at.assertEquals(driver.getPageSouce().contains("首页"), true, "loginError.png", "登录失败啦");
				flag=-1;
			} catch (AssertionError e) {
				// TODO: handle exception
				flag--;
				if(flag==0){
					at.fail("loginError", "登录失败啦");
				}
				ProUtil p=new ProUtil(CrazyPath.capsPath);
				if(driver!=null){
					driver.startActivity(p.getPro("appPackage"), p.getPro("appActivity"));
				}else{
					System.out.println("driver is null");
				}
			}
		}
	}
	@Test(dataProvider="loginData")
	public void login(String casenumber,String casename,String username,String pwd,String assertValue,String fileName) throws Exception {
		Reporter.log("开始执行"+casename);
		if (driver != null) {
			LoginPage lp = new LoginPage(driver);
			HomePage hp = lp.login(username,pwd);
			driver.wait(3000);
			ExcelUtil eu=new ExcelUtil("C:\\Users\\lixionggang\\Desktop/test.xlsx", "Sheet1");
			Assertion at=new Assertion(driver);
			try {
				//Assert.assertEquals(hp.getPageSource().contains(assertValue), true);
				at.assertEquals(hp.getPageSource().contains(assertValue), true, fileName);
				eu.setCellData(Integer.valueOf(casenumber), eu.getLastColumnNum(), "测试执行成功");
				System.out.println("写入成功==============");
			} catch (AssertionError e) {
				eu.setCellData(Integer.valueOf(casenumber), eu.getLastColumnNum(), "测试执行失败");
				at.fail(fileName,"测试"+casename+"失败");
			}
		} else {
			System.out.println("driver是null");
		}
	}
//	@Test(dataProvider="loginData")
//	public void login(String username,String pwd,String assertValue,String fileName) throws Exception {
//		Reporter.log("开始执行");
//		if (driver != null) {
//			LoginPage lp = new LoginPage(driver);
//			HomePage hp = lp.login(username,pwd);
//			driver.wait(3000);
//			Assertion at=new Assertion(driver);
//			at.assertEquals(driver.getPageSouce().contains(assertValue), true, fileName);
//		} else {
//			System.out.println("driver是null");
//		}
//	}
	@Test(dependsOnMethods="login")
	public void addAnswer(){
		
		driver.wait(3000);
		List<AndroidElement> titleList=driver.findElements(GetByLocator.getLocator("article"));
		System.out.println(titleList.size());
		for(AndroidElement ae:titleList){
			ae.click();
			AndroidElement add=driver.findElement(GetByLocator.getLocator("addanswer"));
			if(add==null){
				
			}else{
				add.click();
				driver.findElement(GetByLocator.getLocator("sendtext")).sendKeys(RandomUtil.getRndStrZhByLen(5));
				driver.findElement(GetByLocator.getLocator("submit")).click();
			}
			driver.pressBack();
		}
	}
	@BeforeMethod
	public void afterMethod(){
		ProUtil p=new ProUtil(CrazyPath.capsPath);
		if(driver!=null){
			
			driver.startActivity(p.getPro("appPackage"), p.getPro("appActivity"));
		}else{
			System.out.println("driver is null");
		}
		
	}
	@AfterClass
	public void afterClass(){
		driver.quit();
	}
	

}
