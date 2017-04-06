package cn.crazy.appium.network.study;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidKeyCode;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.webdriven.commands.GetLocation;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ProUtil;
import cn.crazy.appium.util.SendMail;

public class Study4 extends DriverInit {
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
	    	sm.send("driver初始化失败",udid+"driver初始化失败",to);
		}
		
		System.out.println("study 1 driver is "+driver);
	}
	@Test
	public void login(){
		TouchAction ta=new TouchAction(driver);
		ta.press(300,500).moveTo(-30, -50).moveTo(-100, 300).release().perform();
		driver.findElement(GetByLocator.getLocator("loginOrReg")).click();
		driver.findElement(GetByLocator.getLocator("username")).click();
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_7);
		driver.findElement(GetByLocator.getLocator("password")).sendKeys("12345678");
		driver.wait(10000);
		driver.findElement(GetByLocator.getLocator("loginbtn")).click();
		Assert.assertEquals(driver.isElementExist(GetByLocator.getLocator("ingroe"), 10), true);
	}
	@AfterClass
	public void afterClass(){
		System.out.println("study 1 quit");
		driver.quit();
	}
	

}
