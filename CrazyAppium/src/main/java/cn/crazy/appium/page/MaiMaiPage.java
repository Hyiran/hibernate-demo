package cn.crazy.appium.page;

import io.appium.java_client.android.AndroidElement;

import org.apache.poi.ss.formula.functions.Roman;
import org.openqa.selenium.By;
import org.testng.Assert;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.RandomUtil;

public class MaiMaiPage extends BasePage{
	
	private AndroidDriverBase driver;

	public MaiMaiPage(AndroidDriverBase driver) {
		super(driver);
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}
	
	public void comment(){
		super.click(GetByLocator.getLocator("nmbg"));
		super.click(GetByLocator.getLocator("likecount"));
		super.click(GetByLocator.getLocator("commentcount"));
		String commentText=RandomUtil.getRndStrByLen(3);
		super.sendkeys(GetByLocator.getLocator("input"),commentText);
		super.click(GetByLocator.getLocator("send"));
		AndroidElement result=driver.selectElementFromList(driver.findElements(GetByLocator.getLocator("viewcount")), commentText);
		Assert.assertNotNull(result);
		
	}

}
