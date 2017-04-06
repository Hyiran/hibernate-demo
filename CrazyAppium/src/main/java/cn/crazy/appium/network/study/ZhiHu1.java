package cn.crazy.appium.network.study;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.testng.Assertion;
import cn.crazy.appium.testng.TestngRetry;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ProUtil;
import cn.crazy.appium.util.RandomUtil;
import cn.crazy.appium.util.SendMail;

public class ZhiHu1 extends	DriverInit{
	AndroidDriverBase driver;
	@Parameters({"udid","port"})
	@BeforeClass
	public void beforeClass(String udid,String port) throws Exception{
		try {
			driver=driverInit(udid, port);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			SendMail sm=new SendMail();
			ProUtil p=new ProUtil(CrazyPath.globalPath);
	    	String[] to=p.getPro("tomail").split(",");
	    	sm.send("driver初始化失败",udid+"driver初始化失败"+e.getMessage(),to);
		}
		
		System.out.println("study 1 driver is "+driver);
	}
	@AfterMethod
	public void afterMethod(){
		driver.startActivity(
				(String)driver.getCapabilities().getCapability("appPackage"), (String)driver.getCapabilities().getCapability("appActivity"));
	}
	@Test(retryAnalyzer=TestngRetry.class)
	public void login(){
//		TouchAction ta=new TouchAction(driver);
//		ta.press(300,500).moveTo(-30, -50).moveTo(-100, 300).release().perform();
		driver.findElement(GetByLocator.getLocator("loginOrReg")).click();
		driver.findElement(GetByLocator.getLocator("username")).sendKeys("crazysand_001@163.com");;
		//driver.pressKeyCode(AndroidKeyCode.KEYCODE_7);
		driver.findElement(GetByLocator.getLocator("password")).sendKeys("1234567");
		//driver.wait(10000);
		driver.findElement(GetByLocator.getLocator("loginbtn")).click();
		//Assert.assertEquals(driver.isElementExist(GetByLocator.getLocator("ingroe"), 10), true);
		Assertion as=new Assertion(driver);
		as.assertEquals(driver.isElementExist(GetByLocator.getLocator("ingroe"), 10), true, "login.error");
	}
	@Test
	public void modifyPersionInfo(){
		driver.findElement(GetByLocator.getLocator("ingroe")).click();
		driver.findElement(GetByLocator.getLocator("zhihu")).click();
		driver.findElement(GetByLocator.getLocator("uname")).click();
		driver.findElement(GetByLocator.getLocator("edit")).click();
		//com.zhihu.android:id/user_name
		driver.findElement(GetByLocator.getLocator("user_name")).click();
		AndroidElement content=driver.findElement(GetByLocator.getLocator("content"));
		String oldName=content.getText();
		String newName=RandomUtil.getRndStrAndNumberByLen(7);
		while(newName.equals(oldName)){
			newName=RandomUtil.getRndStrAndNumberByLen(7);
		}
		content.click();
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_MOVE_END);
		for(int i=0;i<oldName.length();i++){
			driver.pressBackspace();
		}
		content.sendKeys(newName);
		driver.findElement(GetByLocator.getLocator("finish")).click();
		
		//com.zhihu.android:id/male  com.zhihu.android:id/female
		if(driver.findElement(GetByLocator.getLocator("male")).getAttribute("checked").equals("true")){
			driver.findElement(GetByLocator.getLocator("female")).click();
		}else{
			driver.findElement(GetByLocator.getLocator("male")).click();
		}
		//com.zhihu.android:id/introduction
		driver.findElement(GetByLocator.getLocator("introduction")).click();
		String oldIntroduction=driver.findElement(GetByLocator.getLocator("content")).getText();
		String newIntroduction=RandomUtil.getRndStrZhByLen(7);
		while(newIntroduction.equals(oldIntroduction)){
			newName=RandomUtil.getRndStrZhByLen(7);
		}
		driver.findElement(GetByLocator.getLocator("content")).sendKeys(newIntroduction);
		driver.findElement(GetByLocator.getLocator("finish")).click();
		//com.zhihu.android:id/explanation
		
	}
}
