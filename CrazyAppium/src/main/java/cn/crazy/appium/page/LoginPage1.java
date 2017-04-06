package cn.crazy.appium.page;

import io.appium.java_client.android.AndroidElement;
import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.Log;

public class LoginPage1 extends BasePage {
	private Log logger=Log.getLogger(LoginPage1.class);
	public LoginPage1(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public AndroidElement getLoginOrReg() {
		AndroidElement loginOrReg=driver.findElement(GetByLocator.getLocator("loginOrReg"));
		return loginOrReg;
	}

	public AndroidElement getLoginbtn() {
		AndroidElement loginbtn=driver.findElement(GetByLocator.getLocator("loginbtn"));
		return loginbtn;
	}

	public AndroidElement getName() {
		AndroidElement name=driver.findElement(GetByLocator.getLocator("name"));
		return name;
	}

	public AndroidElement loginOrReg;
	public AndroidElement suibianbtn;
	public AndroidElement username;
	public AndroidElement password;
	public AndroidElement loginbtn;
	public AndroidElement reg;
	public AndroidElement name;
	public AndroidElement regbtn;
	
	public HomePage login(String username,String pwd){
		this.click(GetByLocator.getLocator("loginOrReg"));
		this.sendkeys(GetByLocator.getLocator("username"), username);
		this.sendkeys(GetByLocator.getLocator("password"), pwd);
		this.click(GetByLocator.getLocator("loginbtn"));
		this.click(GetByLocator.getLocator("ingroe"));
		return new HomePage(driver);
	}
	public HomePage reg(String username,String pwd,String name){
		this.click(GetByLocator.getLocator("loginOrReg"));
		//getLoginOrReg().click();
		this.sendkeys(GetByLocator.getLocator("username"), username);
		this.sendkeys(GetByLocator.getLocator("password"), pwd);
		this.click(GetByLocator.getLocator("reg"));
		this.sendkeys(GetByLocator.getLocator("name"), name);
		this.click(GetByLocator.getLocator("regbtn"));
		
		return new HomePage(driver);
	}
	public void suibiankankan(){
		this.click(GetByLocator.getLocator("suibiankankan"));
		this.click(GetByLocator.getLocator("ingroe"));
		for(int i=0;i<4;i++){
			driver.wait(500);
			driver.swipe("left", 500);
		}
	}
	

}
