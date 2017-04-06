package cn.crazy.appium.page;

import io.appium.java_client.android.AndroidElement;
import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.GetByLocator;

public class LxgPage extends BasePage {
	private AndroidElement loginOrReg;
	private AndroidElement userName;
	private AndroidElement btn;

	public LxgPage(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public void signIn(String username,String pwd){
		super.sendkeys(GetByLocator.getLocator("lxg.username"), username);
		super.sendkeys(GetByLocator.getLocator("lxg.password"), pwd);
		super.click(GetByLocator.getLocator("lxg.btn"));
	}

}
