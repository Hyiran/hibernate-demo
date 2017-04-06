package cn.crazy.appium.network.study;

import io.appium.java_client.android.AndroidElement;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.AndroidSpecific;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.testng.TestngRetry;
import cn.crazy.appium.util.ProUtil;

public class XiaoYueLi {
	public AndroidDriverBase driver;
	
	@BeforeClass
	public void startApp() throws Exception{
		String udid="192.168.56.101:5555";
		String input=AndroidSpecific.getDefaultInput(udid);
		ProUtil p=new ProUtil(CrazyPath.globalPath);
		String server=p.getPro("server");
		String port="4490";
		String capsPath=CrazyPath.capsPath;
		
		driver=new AndroidDriverBase(server, port, capsPath, udid, input, false);
		driver.implicitlyWait(10);
	}
	@Test(retryAnalyzer=TestngRetry.class)
	public void changeLanguage(){
		driver.findElement(By.id("com.popularapp.periodcalendar:id/bt_go_setting")).click();
		List<AndroidElement> commonElements=new ArrayList<AndroidElement>();
		List<String> elementsText=new ArrayList<String>();
		while(true){
			List<AndroidElement> currentElements=driver.findElements(By.id("com.popularapp.periodcalendar:id/item"));
			for(AndroidElement ae:currentElements){
				String text=ae.getText();
				if(!elementsText.contains(text)){
					System.out.println(text);
					elementsText.add(text);
					commonElements.add(ae);
				}else{
					List<AndroidElement> cList=driver.findElements(By.id("com.popularapp.periodcalendar:id/item_text"));
					if(cList.size()>0){
						elementsText.add(text);
						commonElements.add(ae);
					}
				}
			}
			System.out.println(commonElements.size());
			if(commonElements.size()>12){
				commonElements.get(11).click();
				break;
			}
			driver.swipe("up", 4000);
		}
		Assert.fail();
	}
	@AfterMethod
	public void retryApp(){
		driver.startActivity("com.zhihu.android", "com.zhihu.android.ui.activity.GuideActivity");
	}
	@AfterClass
	public void quit(){
		driver.quit();
	}

}
