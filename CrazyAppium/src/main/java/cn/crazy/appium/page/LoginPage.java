package cn.crazy.appium.page;

import org.openqa.selenium.By;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.server.Port;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.Log;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class LoginPage extends BasePage{
	private Log logger=Log.getLogger(LoginPage.class);
	private AndroidElement loginOrReg;
	private AndroidElement userName;
	private AndroidElement pwd;
	private AndroidElement loginBtn;
	private AndroidElement next;
	private AndroidElement reg;
	private AndroidElement name;
	//private AndroidElement next;
	private AndroidElement regBtn;
	private AndroidElement allowDialog;//允许对话框
	
	
	public LoginPage(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	//登录
	public   void loginPage(String username,String password){
		if(driver.getPageSouce().contains("允许")){
			driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		}
		System.out.println("测试时变为i");
		if(!driver.isElementExist(GetByLocator.getLocator("tomanager"))){
		driver.pressBack();
		}
		loginOrReg=driver.findElement(GetByLocator.getLocator("i"));
		if(loginOrReg!=null){
			System.out.println("测试到这");
			loginOrReg.click();//点击"我"按钮
			System.out.println("不是null");
		}
		allowDialog = driver.findElement(GetByLocator.getLocator("allowDialog"));
		if(allowDialog !=null){
			driver.findElement(GetByLocator.getLocator("allow_button")).click();//点击对话框的允许按钮
		}
		
		
		userName=driver.findElement(GetByLocator.getLocator("username"));//用户名
		if(userName!=null){
			userName.sendKeys(username);
		}
		next = driver.findElement(GetByLocator.getLocator("next"));
		if(name !=null){
			next.click();
		}
		pwd=driver.findElement(GetByLocator.getLocator("password"));//输入密码
		if(pwd!=null){
			pwd.sendKeys(password);
		}
		
		
		if(next!=null){
			next.click();
		}
		
		//return new HomePage(driver);
	}
	public HomePage login(String username,String pwd){
		//driver.findElement(GetByLocator.getLocator("loginOrReg"));
		super.click(GetByLocator.getLocator("loginOrReg"));
		logger.info("点击成功");
		super.sendkeys(GetByLocator.getLocator("username"), username);
		super.sendkeys(GetByLocator.getLocator("password"), pwd);
		
		super.click(GetByLocator.getLocator("loginbtn"));
		logger.info("点击登录成功");
		return new HomePage(driver); 
	}
	public void regTest(String username,String pwd,String name){
		super.click(GetByLocator.getLocator("loginOrReg"));
		super.sendkeys(GetByLocator.getLocator("username"),username);
		super.sendkeys(GetByLocator.getLocator("password"), pwd);
		super.click(GetByLocator.getLocator("reg"));
		super.sendkeys(GetByLocator.getLocator("name"), name);
		super.click(GetByLocator.getLocator("regbtn"));
	}
	
}
