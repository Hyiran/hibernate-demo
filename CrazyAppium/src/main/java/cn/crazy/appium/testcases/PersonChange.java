package cn.crazy.appium.testcases;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.AndroidSpecific;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.page.PersionChange;
import cn.crazy.appium.testng.TestngRetry;

public class PersonChange {
	
public AndroidDriverBase driver;
	
	@Parameters({"udid","port"})
	@BeforeClass
	public void initDriver(String udid,String port){
		String server="http://127.0.0.1";
		//String port="4723";
		String capsPath=CrazyPath.capsPath;
		//String udid="127.0.0.1:62001";
		AndroidSpecific as=new AndroidSpecific();
		String input=as.getDefaultInput(udid);
		//String input="com.example.android.softkeyboard/.SoftKeyboard";
		try {
			driver=new AndroidDriverBase(server, port, capsPath, udid, input, false);
			driver.implicitlyWait(10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test(retryAnalyzer=TestngRetry.class)
	public void modify(){
		PersionChange pc=new PersionChange(driver);
		pc.modify();
	}


	@AfterMethod
	public void afterMethod(){
		driver.startActivity((String)driver.getCapabilities().getCapability("appPackage"), (String)driver.getCapabilities().getCapability("appActivity"));
	}
	@AfterClass
	public void quit(){
		driver.quit();
	}

}
