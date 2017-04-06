package cn.crazy.appium.network.study;


import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;

public class Study1 extends DriverInit{
	AndroidDriverBase driver;
	@Parameters({"udid","port"})
	@BeforeClass
	public void beforeClass(String udid,String port) throws Exception{
		try {
			driver=driverInit(udid, port,1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("study 1 driver is "+driver);
	}
	@AfterClass
	public void afterClass(){
		System.out.println("study 1 quit");
		driver.quit();
	}
	@Test(priority=2)
	public void attentionArticle() throws InterruptedException{
		System.out.println("driver is "+driver);
		Thread.sleep(5000);
		driver.findElement(By.name("忽略")).click();
		driver.findElement(By.id("com.zhihu.android:id/title")).click();
		AndroidElement iKnow=driver.findElement(By.name("我知道了"));
		if(null!=iKnow){
			iKnow.click();
		}
		AndroidElement element=driver.swipeUntilElement(By.name("关注"),"up",500,20);
		if(null!=element){
			element.click();
			Assert.assertEquals(driver.isElementExist(By.name("取消关注")), true);
		}else{
			System.out.println("这篇文章已经被关注");
		}
		driver.pressBack();
	}
}
