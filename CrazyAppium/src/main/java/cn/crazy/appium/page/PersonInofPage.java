package cn.crazy.appium.page;

import io.appium.java_client.android.AndroidElement;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.testng.Assertion;
import cn.crazy.appium.util.RandomUtil;

public class PersonInofPage extends BasePage {

	public PersonInofPage(AndroidDriverBase driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public void personChange(){
		click(By.name("我"));
		click(By.id("com.sinoicity.health.patient:id/header"));
		click(By.name("我的资料"));
		driver.wait(1000);
		
		//姓名
		String name=RandomUtil.getRndStrZhByLen(3);
		sendkeys(By.id("com.sinoicity.health.patient:id/name_text"), name);
		//性别
		AndroidElement femail=driver.findElement(By.xpath("//android.widget.RadioButton[1]"));
		AndroidElement mail=driver.findElement(By.xpath("//android.widget.RadioButton[2]"));
		
		if(femail.getAttribute("checked").equals("true")){
			mail.click();
		}else{
			femail.click();
		}
		//身高
		String height=driver.findElement(By.id("com.sinoicity.health.patient:id/height_text")).getText();
		int heightOld=Integer.valueOf(height.split("C")[0]);
		int heightNew=heightOld;
		boolean flag=true;
		while(flag){
			heightNew=RandomUtil.randomInt(100,130);
			if(heightNew!=heightOld){
				flag=false;
			}
		}
		System.out.println(heightNew);
		String heightStr=String.valueOf(heightNew)+"CM";
		driver.findElement(By.id("com.sinoicity.health.patient:id/height_text")).click();
		while(true){
			
			try {
				driver.findElement(By.name(heightStr)).click();
				break;
			} catch (Exception e) {
				// TODO: handle exception
				driver.swipeOnElement(By.id("com.sinoicity.health.patient:id/choices_list"),"up",1000);
			}
			//driver.wait(500);
		}
		//体重
		String weight=driver.findElement(By.id("com.sinoicity.health.patient:id/weight_text")).getText();
		int weightOld=Integer.valueOf(weight.split("K")[0]);
		int weightNew=weightOld;
		boolean flag1=true;
		while(flag1){
			weightNew=RandomUtil.randomInt(37, 50);
			
			if(weightNew!=weightOld){
				flag1=false;
			}
		}
		System.out.println(weightNew);
		//
		String weightStr=String.valueOf(weightNew)+"KG";
		driver.findElement(By.id("com.sinoicity.health.patient:id/weight_text")).click();
		//driver.swipeUntilElement(By.name(weightStr), "down",1000,50).click();
		while(true){
			
			try {
				driver.findElement(By.name(weightStr)).click();
				break;
			} catch (Exception e) {
				// TODO: handle exception
				driver.swipeOnElement(By.id("com.sinoicity.health.patient:id/choices_list"),"up",1000);
			}
			//driver.wait(500);
		}
		
		//运动
		List<String> sportsList=new ArrayList<String>();
		sportsList.add("羽毛球");
		sportsList.add("乒乓球");
		sportsList.add("篮球");
		sportsList.add("足球");
		sportsList.add("慢跑");
		sportsList.add("骑行");
		sportsList.add("登山");
		sportsList.add("徒步");
		sportsList.add("游泳");
		String sports=driver.findElement(By.id("com.sinoicity.health.patient:id/sports_text")).getText();
		//足球, 慢跑
		String[] sts=sports.split(", ");
		sportsList.remove(sts[0]);
		sportsList.remove(sts[1]);
		
		int i1=RandomUtil.getExtentRandomNumber(sportsList.size());
		
		int i2=RandomUtil.getExtentRandomNumber(sportsList.size());
		while(true){
			if(i1==i2){
				i2=RandomUtil.getExtentRandomNumber(sportsList.size());
			}else{
				break;
			}
		}
		driver.findElement(By.id("com.sinoicity.health.patient:id/sports_text")).click();
		driver.findElement(By.xpath("//android.widget.TextView[@text='"+sts[0]+"']/../android.widget.CheckBox")).click();
		driver.findElement(By.xpath("//android.widget.TextView[@text='"+sts[1]+"']/../android.widget.CheckBox")).click();
		driver.findElement(By.xpath("//android.widget.TextView[@text='"+sportsList.get(i1)+"']/../android.widget.CheckBox")).click();
		driver.findElement(By.xpath("//android.widget.TextView[@text='"+sportsList.get(i2)+"']/../android.widget.CheckBox")).click();
		click(By.name("确定"));
		List<String> oldInfo=getInfo();
		click(By.id("com.sinoicity.health.patient:id/right_btn"));
		click(By.name("我的资料"));
		driver.wait(2000);
		List<String> infoNew=getInfo();
		Assertion at=new Assertion(driver);
		at.assertEquals(driver.listStrEquals(infoNew, oldInfo),true,"info.jpg");
	}
	public List<String> getInfo(){
		String name=driver.findElement(By.id("com.sinoicity.health.patient:id/name_text")).getText();
		List<AndroidElement> sexList=driver.findElements(By.className("android.widget.RadioButton"));
		String sex="女";
		for(AndroidElement ae:sexList){
			if(ae.getAttribute("checked").equals("true")){
				sex=ae.getText();
			}
		}
		String height=driver.findElement(By.id("com.sinoicity.health.patient:id/height_text")).getText();
		String weight=driver.findElement(By.id("com.sinoicity.health.patient:id/weight_text")).getText();
		String sports=driver.findElement(By.id("com.sinoicity.health.patient:id/sports_text")).getText();
		//String paint=driver.findElement(By.id("com.sinoicity.health.patient:id/diseases_text")).getText();
		List<String> infoList=new ArrayList<String>();
		infoList.add(name);
		infoList.add(sex);
		infoList.add(height);
		infoList.add(weight);
		infoList.add(sports);
		//infoList.add(paint);
		return infoList;
	}
	

}
