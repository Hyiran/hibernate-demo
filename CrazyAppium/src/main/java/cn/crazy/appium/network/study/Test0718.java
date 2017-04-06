package cn.crazy.appium.network.study;




import io.appium.java_client.android.AndroidElement;

import org.apache.tools.ant.util.ProcessUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ImageUtil;
import cn.crazy.appium.util.ProUtil;
import cn.crazy.appium.util.SendMail;

public class Test0718 extends DriverInit {
	
	 AndroidDriverBase driver;
	
	@Parameters({"port","udid"})
	@BeforeClass
	public void beforeClass (String udid,String port) throws Exception{
		try {
			driver = driverInit(udid, port);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			SendMail sm = new SendMail();
			ProUtil p = new ProUtil(CrazyPath.globalPath);
			String [] to = p.getPro("tomail").split(",");
			sm.send("driver初始化失败", udid+"driver初始化失败", to);
		}
		System.out.println("stduy 1 driver is "+driver);
	}
	@Parameters({"user","pwd"})
	@Test
	public void login(String user,String pwd) throws Exception{
		if(driver.isElementExist(GetByLocator.getLocator("allow"))){
			driver.findElement(GetByLocator.getLocator("allow")).click();
		}
		AndroidElement iBtn = driver.findElement(GetByLocator.getLocator("i"));
		if( iBtn!=null){
			iBtn.click();
		}
		AndroidElement username = driver.findElement(GetByLocator.getLocator("username"));
		if(username!=null){
			username.sendKeys(user);
		}
		AndroidElement next = driver.findElement(GetByLocator.getLocator("next"));
		if(next !=null){
			next.click();
		}
		driver.wait(2000);
		AndroidElement password = driver.findElement(GetByLocator.getLocator("pwd"));
		if(password !=null){
			password.click();
		}
		AndroidElement loginBtn = driver.findElement(GetByLocator.getLocator("next"));
		if(loginBtn!=null){
			loginBtn.click();//点击登录按钮
		}
		if(iBtn!=null){
			iBtn.click();//点击我
		}
	 driver.wait(2000);
	try {
		Assert.assertEquals(driver.isElementExist(GetByLocator.getLocator("withDrawals"), 30), true);//断言是否登录成功
	} catch (Exception e) {
		SendMail sm = new SendMail();
		sm.send("用户登录用例", "测试用户登录失败", new String [] {"562068121@qq.com"});
	}
		
	}
	
	@AfterClass
	public void quit(){
		driver.quit();
	}
	
}
