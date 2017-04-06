package cn.crazy.appium.page;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.GetByLocator;

public class Drawals extends BasePage {

	public Drawals(AndroidDriverBase driver) {
		super(driver);
	}
	//提现
	public  void withDrawals() throws Exception {
		super.click(GetByLocator.getLocator("i"));
		super.click(GetByLocator.getLocator("withDrawals"));
		super.sendkeys(GetByLocator.getLocator("drawmoney"), "3");
		super.sendkeys(GetByLocator.getLocator("psd"), "123456");
		super.click(GetByLocator.getLocator("sqButton"));
		wait(10000);
		
	}
	
	
	
	

}
