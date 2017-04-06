package cn.crazy.appium.page;

import io.appium.java_client.android.AndroidElement;

import java.util.List;

import org.openqa.selenium.By;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.RandomUtil;

public class HomePage extends BasePage{
	private AndroidElement ingroe;
    public HomePage(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
    public void clickIngroe(){
    	super.click(GetByLocator.getLocator("ingroe"));
    }
    public void article(){
    	clickIngroe();
    	List<AndroidElement> elementList=super.driver.findElements(By.id("com.zhihu.android:id/title"));
    	for(AndroidElement ae:elementList){
    		System.out.println(ae.getText());
    		ae.click();
    		click(By.name("我知道了"));
    		driver.pressBack();
    	}
    	driver.wait(500);
    	driver.swipe("up", 500);
    	driver.wait(2000);
    	
    }
    public void addAnswer(){
    	clickIngroe();
    	List<AndroidElement> elementList=driver.findElements(GetByLocator.getLocator("article"));
    	for(AndroidElement ae:elementList){
    		ae.click();
    		AndroidElement add=driver.findElement(GetByLocator.getLocator("addanswer"));
    		if(add==null){
    			
    		}else{
    			add.click();
    			driver.findElement(GetByLocator.getLocator("sendtext")).sendKeys(RandomUtil.getRndStrZhByLen(5));
    			driver.findElement(GetByLocator.getLocator("submit")).click();
    		}
    		driver.pressBack();
    	}

    }
}
