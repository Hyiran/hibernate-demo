package cn.crazy.appium.network.study;

import java.io.File;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Cwork {
	
//	public static void main(String[] args) throws Exception {
//		AndroidDriver<AndroidElement> driver=Start.driverStart("com.seeyon.cwork", "com.seeyon.cwork.ui.LoadActivity");
//		Thread.sleep(10000);
//		Set<String> contents=driver.getContextHandles();
//		for(String s:contents){
//			System.out.println(s);
//		}
//		driver.context("WEBVIEW_com.seeyon.cwork");
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//		for(int i=0;i<5;i++){
//			AndroidElement username=driver.findElement(By.id("login_username"));
//			username.clear();
//			username.sendKeys("1356789000"+String.valueOf(i));
//			AndroidElement pwd=driver.findElement(By.id("login_password"));
//			pwd.clear();
//			pwd.sendKeys("123456");
//			driver.findElement(By.id("login_submit")).click();
//			driver.context("NATIVE_APP");
//			File image=driver.getScreenshotAs(OutputType.FILE);
//			FileUtils.copyFile(image,new File("images/error"+String.valueOf(i)+".jpg"));
//			driver.context("WEBVIEW_com.seeyon.cwork");
//			driver.findElement(By.linkText("确定")).click();
//			//tap swipe 
//		}
//
//		
//
//		//driver.findElement(By.id("addressIpt")).sendKeys("http://cwtest.awatad.net:54947");
//		//driver.findElement(By.linkText("保存")).click();
//		//driver.findElement(By.cssSelector(".align_right.app_built_type")).click();
//		//driver.executeScript("document.getElementById(\"save\").click()");
////		JavascriptExecutor jse = (JavascriptExecutor)driver;
////		jse.executeScript("window.document.getElementById('save').click()");
////		AndroidElement save=driver.findElement(By.id("save"));
////		//save.click();
////		int x=save.getSize().width;
////		int y=save.getSize().height;
////		int x1=save.getLocation().x;
////		int y1=save.getLocation().y;
////		System.out.println("x1:"+x1+"y1:"+y1+"x="+x+"y="+y);
////		System.out.println("click点击完成");
////		Thread.sleep(5000);
////		driver.context("NATIVE_APP");
////		driver.tap(1,2*x1+x/2,2*y1+y*2,100);
//		Thread.sleep(20000);
//		driver.quit();
//	}

}
