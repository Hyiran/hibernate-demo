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

public class Test0716 {
	
	public AndroidDriverBase driver;
	
	@Parameters({"port","udid"})
	@BeforeClass
	public void initDriver(String port,String udid) throws Exception{
		String server="http://127.0.0.1";
		//String  port="4723";
		String capsPath=CrazyPath.capsPath;
		//String udid="127.0.0.1:62001";
		String input="com.example.android.softkeyboard/.SoftKeyboard";
		driver=new AndroidDriverBase(server, port, capsPath, udid, input, false);
	}
	@Parameters({"user"})
	@Test
	public void login(String user) throws Exception{
//		ZhiHu zhihu=new ZhiHu(driver);
//		driver.implicitlyWaitDefault();
//		zhihu.login("crazysand_001");
		if(driver.isElementExist(By.name("取消"))){
			driver.findElement(By.name("取消")).click();
		}
		AndroidElement loginBtn=driver.findElement(By.name("登录"));
		if(loginBtn!=null){
			loginBtn.click();
		}
		AndroidElement username=driver.findElement(By.id("com.zhihu.android:id/username"));
		if(username!=null){
			username.sendKeys(user);
		}
		AndroidElement password=driver.findElement(By.id("com.zhihu.android:id/password"));
		if(password!=null){
			password.sendKeys("12345678");
		}
//		driver.takeScreenForElement(By.id("com.zhihu.android:id/btn_progress"), "images/", "btn");
		AndroidElement btn_progress=driver.findElement(By.id("com.zhihu.android:id/btn_progress"));
		if(btn_progress!=null){
			btn_progress.click();
		}
//		driver.takeScreenForElement(By.id("com.zhihu.android:id/btn_progress"), "images/", "btn1");
//		if(ImageUtil.compareImg("images/btn.png", "images/btn1.png")){
//			System.out.println("断言成功");
//		}
		Assert.assertEquals(driver.isElementExist(By.name("搜索话题、问题或人"), 30), true);
	}
	
	@AfterClass
	public void quit(){
		driver.quit();
	}
	
}
