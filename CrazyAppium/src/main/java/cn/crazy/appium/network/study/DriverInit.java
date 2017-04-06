package cn.crazy.appium.network.study;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.AndroidSpecific;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.util.ProUtil;

public class DriverInit {
	private AndroidDriverBase driver=null;
	
	public void driverStart() throws Exception{
		String udid="192.168.56.101:5555";
		String server="http://127.0.0.1";
		String port="4490";
		String input="xxxx";
		String capsPath=CrazyPath.capsPath;
		
		AndroidDriverBase dr=new AndroidDriverBase(server, port, capsPath, udid, input, false);
	}
	
	public  AndroidDriverBase driverInit(String udid,String port) throws Exception{
		ProUtil p = new ProUtil(CrazyPath.globalPath);
		String server=p.getPro("server");
		System.out.println(server);
		String capsPath=CrazyPath.capsPath;
		AndroidSpecific as=new AndroidSpecific();
		String input=as.getDefaultInput(udid);
		System.out.println("连接"+udid+"端口"+port);
		driver=new AndroidDriverBase(server, port, capsPath, udid, input, false);
		//AndroidDriver dr=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4490/wd/hub"), new DesiredCapabilities());
		//隐式等待10秒中
		driver.implicitlyWait(10);
		//driver.resetApp();
		
		return driver;
	}
	public  AndroidDriverBase driverInit(String udid,String port,int waitFlag) throws Exception{
		ProUtil p = new ProUtil(CrazyPath.globalPath);
		String server=p.getPro("server");
		System.out.println(server);
		String capsPath=CrazyPath.capsPath;
		AndroidSpecific as=new AndroidSpecific();
		String input=as.getDefaultInput(udid);
		System.out.println("连接"+udid+"端口"+port);
		driver=new AndroidDriverBase(server, port, capsPath, udid, input,waitFlag);
		//AndroidDriver dr=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4490/wd/hub"), new DesiredCapabilities());
		//隐式等待10秒中
		driver.implicitlyWait(10);
		//driver.resetApp();
		
		return driver;
	}
//    private static class SingletonHolder{
//    	
//        private static AndroidDriverBase  instance = DriverInit(udid,port);
//    }
	protected
	 AndroidDriverBase getDriver(String udid,String port) throws Exception{
//		String udid="192.168.56.101:5555";
//		String port="4990";
		synchronized(DriverInit.class){
            if(driver == null){
            driver = driverInit(udid,port);
            }
		return driver;

		}
	}
}
