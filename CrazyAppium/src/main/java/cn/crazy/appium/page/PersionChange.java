package cn.crazy.appium.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import io.appium.java_client.android.AndroidElement;
import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.testng.Assertion;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.RandomUtil;

public class PersionChange extends BasePage {
	private AndroidDriverBase driver;
	
	public PersionChange(AndroidDriverBase driver) {
		super(driver);
		this.driver=driver;
		// TODO Auto-generated constructor stub
	}
	
	public void modify(){
//		int retryCount=3;
//		while(retryCount>0){
			super.click(GetByLocator.getLocator("tab5"));
			
			super.click(GetByLocator.getLocator("persioninfo"));
			
			super.click(GetByLocator.getLocator("edit"));
			//((AndroidElement)driver.findElements(By.className("xxx")).get(4)).click();
			//性别
			boolean flag=driver.isElementExist(GetByLocator.getLocator("male"));
			super.click(GetByLocator.getLocator("gender"));
			if(flag){
				super.click(GetByLocator.getLocator("female"));
			}else{
				super.click(GetByLocator.getLocator("male"));
			}
			
			//一句话介绍
			String headline=RandomUtil.getRndStrZhByLen(7);
			AndroidElement headlineElement=driver.findElement(GetByLocator.getLocator("headline"));
			String oldHeadline=headlineElement.getAttribute("text");
			while(headline.equals(oldHeadline)){
				headline=RandomUtil.getRndStrZhByLen(7);
			}
			super.clearOneByOne(headlineElement);
			headlineElement.sendKeys(headline);
			
			//详细描述
			String description=RandomUtil.getRndStrZhByLen(20);
			AndroidElement descriptionElement=driver.findElement(GetByLocator.getLocator("description"));
			String oldDescription=descriptionElement.getAttribute("text");
			while(description.equals(oldDescription)){
				description=RandomUtil.getRndStrZhByLen(7);
			}
			super.clearOneByOne(descriptionElement);
			descriptionElement.sendKeys(headline);
			
			//居住地
			String location=RandomUtil.getRndStrZhByLen(20);
			AndroidElement locationElement=driver.findElement(GetByLocator.getLocator("location"));
			String oldLocation=locationElement.getAttribute("text");
			while(location.equals(oldLocation)){
				location=RandomUtil.getRndStrZhByLen(7);
			}
			super.clearOneByOne(locationElement);
			locationElement.sendKeys(location);
			
			//行业
			int swipeCount=RandomUtil.randomInt(0, 4);
			String professionvalue=driver.findElement(GetByLocator.getLocator("professionvalue")).getText();
			super.click(GetByLocator.getLocator("profession"));
			while(swipeCount>0){
				driver.swipeOnElement(GetByLocator.getLocator("listview"), "up", 1000);
				swipeCount--;
			}
			List<AndroidElement> professionList=driver.findElements(GetByLocator.getLocator("item"));
			int randomIndex=RandomUtil.randomInt(0, professionList.size());
			while(professionList.get(randomIndex).equals(professionvalue)){
				randomIndex=RandomUtil.randomInt(0, professionList.size());
			}
			professionList.get(randomIndex).click();
			
			//最后获取各项的值存入一个集合里
			String genderResult=driver.findElement(GetByLocator.getLocator("genderReslut")).getText();
			String headlineResult=driver.findElement(GetByLocator.getLocator("headline")).getText();
			String descriptionResult=driver.findElement(GetByLocator.getLocator("description")).getText();
			String locationResult=driver.findElement(GetByLocator.getLocator("location")).getText();
			String professionResult=driver.findElement(GetByLocator.getLocator("professionvalue")).getText();
			List<String> expectResult=new ArrayList<String>();
			expectResult.add(genderResult);
			expectResult.add(headlineResult);
			expectResult.add(descriptionResult);
			expectResult.add(locationResult);
			expectResult.add(professionResult);
			//点击保存
			super.click(GetByLocator.getLocator("save"));
			//再次进入
			super.click(GetByLocator.getLocator("edit"));
			//获取保存后所有修改的值
			String genderActual=driver.findElement(GetByLocator.getLocator("genderReslut")).getText();
			String headlineActual=driver.findElement(GetByLocator.getLocator("headline")).getText();
			String descriptionActual=driver.findElement(GetByLocator.getLocator("description")).getText();
			String locationActual=driver.findElement(GetByLocator.getLocator("location")).getText();
			String professionActual=driver.findElement(GetByLocator.getLocator("professionvalue")).getText();
			List<String> expectActual=new ArrayList<String>();
			expectActual.add(genderActual);
			expectActual.add(headlineActual);
			expectActual.add(descriptionActual);
			expectActual.add(locationActual);
			expectActual.add(professionActual);
			
			boolean result=driver.listStrEquals(expectResult, expectActual);
			
			//Assert.assertEquals(result, true,"结果失败");
			Assertion as=new Assertion(driver);
			//try {
				as.assertEquals(result, true, "personerror.png");
//				break;
//			} catch (Error e) {
//				// TODO: handle exception
//				retryCount--;
			}	
		
		
		
	}

	
	



