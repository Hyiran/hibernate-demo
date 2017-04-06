package cn.crazy.appium.page;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.GetByLocator;
import io.appium.java_client.android.AndroidElement;

public class Login0716  extends BasePage{
				public AndroidElement loginBtn;
				public AndroidElement username;
				public AndroidElement password;
				public AndroidElement loginSubmit;
				
				public AndroidElement regBtn;
				public AndroidElement phoneNumber;
				public AndroidElement name;
				public AndroidElement regSubmit;
				public Login0716(AndroidDriverBase driver) {
				super(driver);//继承构造方法
	}
				//登录(返回homePage对象的)
//				public HomePage login(String username,String password){
//					super.click(GetByLocator.getLocator("allow"));//如果出现允许就点击
//					super.click(GetByLocator.getLocator("i"));
//					super.sendkeys(GetByLocator.getLocator("username"), username);
//					super.click(GetByLocator.getLocator("next"));
//					super.sendkeys(GetByLocator.getLocator("password"), password);
//					super.click(GetByLocator.getLocator("next"));
//					return new HomePage(driver);//登录之后就返回一个HomePage对象
//				}
				//登录不返回homePage
				public void login(String username,String password) throws Exception{
					//super.click(GetByLocator.getLocator("allow_button"));//如果出现允许就点击
					System.out.println("来到这里重新开始");
					if(driver.isElementExist(GetByLocator.getLocator("allowDialog"))){
						super.click(GetByLocator.getLocator("allow_button"));
					}
					if(!driver.isElementExist(GetByLocator.getLocator("i"))){
			           driver.pressBack();
			           wait(2000);
			           driver.startActivity("com.yjd.app", "com.yjd.app.activity.PageLoading");
					}
					
					super.click(GetByLocator.getLocator("i"));//点击"我"
					super.click(GetByLocator.getLocator("allow_button"));//如果出现允许，就点击
					super.sendkeys(GetByLocator.getLocator("username"), username);//输入用户名
					super.click(GetByLocator.getLocator("next"));//点击下一步按钮
					if(driver.isElementExist(GetByLocator.getLocator("password"))){
						super.sendkeys(GetByLocator.getLocator("password"), password);//输入密码
						super.click(GetByLocator.getLocator("next"));//点击下一步按钮
					}
				if(!driver.isElementExist(GetByLocator.getLocator("password"))){
					super.click(GetByLocator.getLocator("cancel"));//点击取消按钮
				}
				if(driver.isElementExist(GetByLocator.getLocator("mima"))){
					super.click(GetByLocator.getLocator("backOff"));//点击返回按钮
					super.click(GetByLocator.getLocator("cancel"));//点击取消按钮
				}
			}
				
		
				
				
//				public HomePage login(String username,String password){
//					super.click(GetByLocator.getLocator("allow"));//如果出现允许就点击
//					super.click(GetByLocator.getLocator("i"));
//					super.sendkeys(GetByLocator.getLocator("username"), username);
//					super.sendkeys(GetByLocator.getLocator("password"), password);
//					super.click(GetByLocator.getLocator("loginSubmit"));
//					return new HomePage(driver);//登录之后就返回一个HomePage对象
//				}
				
				
				
				
				
				/**
				 * 不返回homePage对象的
				 * @param username
				 * @param password
				 */
//				public void login(String username,String password){
//					super.click(GetByLocator.getLocator("loginOrReg"));
//					super.sendkeys(GetByLocator.getLocator("username"), username);
//					super.sendkeys(GetByLocator.getLocator("password"), password);
//					super.click(GetByLocator.getLocator("loginSubmit"));
//				}
				//注册
				public void reg(String number,String password,String name){
					super.click(GetByLocator.getLocator("reg"));
					super.sendkeys(GetByLocator.getLocator("username"), number);
					super.sendkeys(GetByLocator.getLocator("password"), password);
					super.sendkeys(GetByLocator.getLocator("fullname"),name );
					super.click(GetByLocator.getLocator("regbtn"));
					
				}
				
				
				
		
}
