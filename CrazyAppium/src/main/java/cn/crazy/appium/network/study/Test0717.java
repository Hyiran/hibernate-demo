package cn.crazy.appium.network.study;

import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.util.ImageUtil;
import cn.crazy.appium.util.SendMail;

public class Test0717 {
	
	public AndroidDriverBase driver;
	
	@Parameters({"port","udid"})
	@BeforeClass
	public void initDriver(String port,String udid) throws Exception{
		String server="http://127.0.0.1";
		//String  port="4723";
		String capsPath=CrazyPath.capsPath;
		//String udid="127.0.0.1:62001";
		//com.meizu.flyme.input/com.meizu.input.MzInputService//获取到当前手机的默认输入法
		String input="com.meizu.flyme.input/com.meizu.input.MzInputService";
		//String input="com.example.android.softkeyboard/.SoftKeyboard";
		
		driver=new AndroidDriverBase(server, port, capsPath, udid, input, false);
	}
	@Parameters({"user","pwd"})
	@Test
	public void login(String user,String pwd) throws Exception{
		if(driver.isElementExist(By.name("允许"))){
			driver.findElement(By.name("允许")).click();
		}
		AndroidElement woBtn = driver.findElement(By.name("我"));
		if(woBtn!=null){
			woBtn.click();//点击我
		}
		AndroidElement username = driver.findElement(By.id("com.yjd.app:id/login_number"));
		if(username!=null){
			username.sendKeys(user);//输入用户名
		}
		
		AndroidElement next = driver.findElement(By.id("com.yjd.app:id/bt_login"));
		if(next!=null){
			next.click();//下一步按钮
		}
		Thread.sleep(2000);
		AndroidElement password = driver.findElement(By.id("com.yjd.app:id/login_pass"));
		if(password!=null){
			password.sendKeys(pwd);//输入密码
		}
		
		AndroidElement loginBtn = driver.findElement(By.id("com.yjd.app:id/bt_login"));
		if(loginBtn!=null){
			loginBtn.click();//点击登录按钮
		}
		if(woBtn!=null){
			woBtn.click();//点击我
		}
	 driver.wait(2000);
	try {
		Assert.assertEquals(driver.isElementExist(By.name("提现"), 30), true);//断言是否登录成功
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
