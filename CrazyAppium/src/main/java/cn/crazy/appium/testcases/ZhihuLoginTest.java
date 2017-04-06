package cn.crazy.appium.testcases;

import io.appium.java_client.android.AndroidElement;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.reporters.FailedReporter;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.datadriver.ExcelUtil;
import cn.crazy.appium.page.HomePage;
import cn.crazy.appium.page.LoginPage;
import cn.crazy.appium.testng.Assertion;
import cn.crazy.appium.testng.RetryListener;
import cn.crazy.appium.testng.TestngRetry;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ProUtil;

public class ZhihuLoginTest  extends CaseBaseTest{
	public AndroidDriverBase driver;
	//public AndroidDriverBase driver;
	@DataProvider
	public Object[][] getLoginData(){
		try {
			return ExcelUtil.getTestData("C:\\Users\\lixionggang\\Desktop/test.xlsx", "Sheet1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Test(dataProvider="getLoginData",priority=1)
	public void login(String casenumber,String casename,String username,String pwd,String assertValue,String fileName) throws Exception {
		Reporter.log("开始执行"+casename);
		if (driver != null) {
			LoginPage lp = new LoginPage(driver);
			HomePage hp = lp.login(username,pwd);
			driver.wait(3000);
			ExcelUtil eu=new ExcelUtil("C:\\Users\\lixionggang\\Desktop/test.xlsx", "Sheet1");
			Assertion as=new Assertion(driver);
			try {
				as.assertEquals(hp.getPageSource().contains(assertValue), true,fileName);
				eu.setCellData(Integer.valueOf(casenumber), eu.getLastColumnNum(), "测试执行成功");
				System.out.println("写入成功==============");
			} catch (AssertionError e) {
				eu.setCellData(Integer.valueOf(casenumber), eu.getLastColumnNum(), "测试执行失败");
				as.fail("测试"+casename+"失败");
			}
		} else {
			System.out.println("driver是null");
		}
	}
//	@Test(priority=2,dependsOnMethods="login")
//	public void article(){
//		HomePage hp=new HomePage(driver);
//		hp.article();
//	}
	@Test(priority=1)
	public void login() throws Exception{
		Reporter.log("开始执行测试");
		Boolean flag=true;
		int retryCount=3;
		while(retryCount>0){
			if (driver != null) {
				LoginPage lp = new LoginPage(driver);
				HomePage hp = lp.login("crazysand_001@163.com","12345678");
				driver.wait(5000);
				Assertion as=new Assertion(driver);
				try {
					as.assertEquals(driver.getPageSource().contains("首页"), true, "login.jpg");
					retryCount=-1;
				} catch (AssertionError e) {
					// TODO: handle exception
					retryCount--;
					if(retryCount==0){
						as.fail("login.jpg");
					}
					ProUtil p=new ProUtil("configs/caps.properties");
					driver.startActivity(p.getPro("appPackage"), p.getPro("appActivity"));
				}
			}else{
				retryCount=-1;
				System.out.println("driver is null");
			}
		}
		Reporter.log("登录执行测试");
	}
	@Test(priority=2)
	public void putQuestions(){
		HomePage hp=new HomePage(driver);
		hp.clickIngroe();
		driver.findElement(By.name("知乎")).click();
		driver.findElement(By.name("提问")).click();
		AndroidElement iKnow=driver.findElement(By.name("我知道了"));
		if(null!=iKnow){
			iKnow.click();
		}
		driver.findElement(By.id("com.zhihu.android:id/content")).sendKeys("sssssssssss");
		driver.findElement(By.name("发布")).click();
		AndroidElement confirm=driver.findElement(By.name("确定"));
		if(null!=confirm){
			confirm.click();
		}
		AndroidElement xx=driver.findElement(By.id("com.zhihu.android:id/content"));
		String text=xx.getText();
		driver.pressBack();
		//添加相关话题
		//Assert.assertEquals(text, "添加相关话题", "没有跳转到话题输入框");
		Assertion at=new Assertion(driver);
		at.assertEquals(text, "添加相关话题", "putquestions.jpg");
	}
	@Test(priority=3)
	public void attentionArticle() throws InterruptedException{
		HomePage hp=new HomePage(driver);
		hp.clickIngroe();
		driver.findElement(By.id("com.zhihu.android:id/title")).click();
		AndroidElement iKnow=driver.findElement(By.name("我知道了"));
		if(null!=iKnow){
			iKnow.click();
		}
		AndroidElement element=driver.swipeUntilElement(By.name("关注"),"up",500,20);
		if(null!=element){
			element.click();
			//Assert.assertEquals(driver.isElementExist(By.name("取消关注")), true);
			Assertion at=new Assertion(driver);
			at.assertEquals(driver.isElementExist(By.name("取消关注")), true, "article.jpg");
		}else{
			System.out.println("这篇文章已经被关注");
		}
		driver.pressBack();
	}
	@AfterMethod
	public void afterMethod(){
		ProUtil p=new ProUtil("configs/caps.properties");
		driver.startActivity(p.getPro("appPackage"), p.getPro("appActivity"));
	}
	@BeforeClass
	@Parameters({ "udid", "port" })
	public void beforeClass(String udid, String port) {
		try {
			System.out.println("读到的udid是："+udid+"读到的port是："+port);
			driver=driverInit(udid, port);
			System.out.println("连接创建成功");
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
