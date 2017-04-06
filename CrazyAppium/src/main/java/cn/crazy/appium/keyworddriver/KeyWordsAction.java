package cn.crazy.appium.keyworddriver;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.page.BasePage;
import cn.crazy.appium.util.GetByLocator;

public class KeyWordsAction {
	 
    //声明静态 AndroidDriverBase 对象，用于在此类中相关 driver 的操作
	public static AndroidDriverBase driver;

	/* 此方法的名称对应 excel 文件中“关键字”列中的 input 关键字
	 * 读取 excel 文件中“操作值”列中的字符作为输入框的输入内容，
	 * 参数 locator 表示输入框的定位表达式。
	 */
	public static void input(String locator,String inputValue) {
		
		try {
			driver.findElement(GetByLocator.getLocator(locator))
					.sendKeys(inputValue);
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	
	/* 此方法名称对应 excel 文件中“关键字”列中的 click 关键字，
	 * 实现点击操作，参数locator 代表被点击元素的定位表达式，
	 * 参数 String为无实际值传入的参数，仅为了通过反射机制统一地使用两个
	 * 函数参数来调用此函数。
	 */
	public static void click(String locator,String string) {
		try {
			driver.findElement(GetByLocator.getLocator(locator)).click();
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	/**
	 * 通过坐标点击元素，对应关键字clickByCoordinate
	 * @param x
	 * @param y
	 */
	public static void clickByCoordinate(String locator,String coordinate){
		int x=0;
		int y=0;
		if(coordinate.contains("=")){
			x=Integer.valueOf(coordinate.split("=")[0]);
			y=Integer.valueOf(coordinate.split("=")[1]);
			try {
				driver.tap(1, x, y, 100);
			} catch (Exception e) {
				TestSuiteByExcel.testResult = false;
				e.printStackTrace();
			}
		}else{
			System.out.println("坐标无效");
		}

	}
	/* 此方法的名称对应 excel 文件中“关键字”列中的 WaitFor_Element 关键字，
	 * 用于显示等待页面元素出现在页面中。函数读取 excel 文件中“操作值”列中的表达
	 * 式作为函数参数，objectMap 对象的getLocator 方法会根据函数参数值在配置
	 * 文件中查找 key 值对应的定位表达式。参数 locatorExpression 表示等待出现
	 * 页面元素的定位表达式，参数 String为无实际值传入的参数，仅为了通过反射机
	 * 制统一地使用两个函数参数来调用此函数。
	 */
	public static void WaitFor_Element(String locator,String timeout) {
		try {
			//调用封装的 isElementExist 函数显示等待页面元素是否出现
			driver.isElementExist(GetByLocator.getLocator(locator),Integer.valueOf(timeout));
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	/* 此方法的名称对应 excel 文件中“关键字”列中的 sleep 关键字用于等待操作，
	 * 暂停几秒，函数参数是以毫秒为单位的等待时间。参数 sleepTime 表示暂停
	 * 的毫秒数，参数 String为无实际值传入的参数，仅为了通过反射机制统一地使用
	 * 两个函数参数来调用此函数。
	 */
	public static void sleep(String string,String sleepTime){
		try {
			driver.wait(Integer.valueOf(sleepTime));
		} catch (Exception e) {
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	/* 此方法的名称对应 excel 文件中“关键字”列中的 Assert_String 关键字，参数 assertString
	 * 为要断言的字符串内容，参数 string 为无实际值传入的参数，仅为了通过反射机制统一地使用两
	 * 个函数参数来调用此函数。
	 */
	
	public static void  Assert_String(String string,String assertString)  {
		try{ 
			 Assert.assertTrue(driver.getPageSource().contains(assertString));
		} catch (AssertionError e) {
			TestSuiteByExcel.testResult = false;
			System.out.println("断言失败");
		}
	}
	public static void  Assert_Element(String string,String assertString)  {
		try{ 
			 Assert.assertEquals(driver.isElementExist(GetByLocator.getLocator(string)), true);
		} catch (AssertionError e) {
			TestSuiteByExcel.testResult = false;
			System.out.println("断言失败");
		}
	}
	/**
	 * 滑动屏幕
	 * @param string
	 * @param direction
	 */
	public static void swipeScreen(String string,String direction){
		try {
			int count=Integer.valueOf(string);
			while(count>0){
				driver.swipe(direction, 500);
				count--;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	/**
	 * 在某元素上滑动
	 * @param locator
	 * @param direction
	 */
	public static void swipeOnElement(String locator,String direction){
		try {
			driver.swipeOnElement(GetByLocator.getLocator(locator), direction, 500);
		} catch (Exception e) {
			// TODO: handle exception
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	/**
	 * 长按某元素
	 * @param locator
	 * @param direction
	 */
	public static void longPress(String locator,String direction){
		try {
			driver.longPress(GetByLocator.getLocator(locator));
		} catch (Exception e) {
			// TODO: handle exception
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}
	/**
	 * 坐标点长按
	 * @param x
	 * @param y
	 */
	public static void longPressByCoordinate(String string,String coordinate){
		int x=0;
		int y=0;
		if(coordinate.contains("=")){
			x=Integer.valueOf(coordinate.split("=")[0]);
			y=Integer.valueOf(coordinate.split("=")[1]);
			try {
				driver.longPress(x,y);
			} catch (Exception e) {
				TestSuiteByExcel.testResult = false;
				e.printStackTrace();
			}
		}else{
			System.out.println("坐标无效");
		}
	}
	public static void wakeByGestures(String locator,String pwd){
		BasePage bp=new BasePage(driver);
		AndroidElement element=driver.findElement(GetByLocator.getLocator(locator));
		String[] indexsStr=pwd.split(",");
		int[] indexs=new int[indexsStr.length];
		for(int i=0;i<indexsStr.length;i++){
			indexs[i]=Integer.valueOf(indexsStr[i]);
		}
		bp.wakeByGestures(element, indexs);
	}
	/**
	 * 退出driver
	 */
	public static void quit(){
		driver.quit();
	}

}
