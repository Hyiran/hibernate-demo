package cn.crazy.appium.network.study;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ProUtil;
import cn.crazy.appium.util.RandomUtil;
import cn.crazy.appium.util.SendMail;
import io.appium.java_client.android.AndroidElement;

public class PersonInfoChange extends DriverInit {
	AndroidDriverBase driver;
	@Parameters({"udid","port"})
	@BeforeClass
	public void beforeClass(String udid,String port) throws Exception{
		try {
			driver=driverInit(udid, port);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			SendMail sm=new SendMail();
			ProUtil p=new ProUtil(CrazyPath.globalPath);
	    	String[] to=p.getPro("tomail").split(",");//如果有错发送一封邮件
	    	sm.send("driver初始化失败",udid+"driver初始化失败",to);//然后说driver初始化失败
		}
		
		System.out.println("study 1 driver is "+driver);
	}
	@Test
	public void personInfo(){
		driver.findElement(GetByLocator.getLocator("tab5")).click();//读取 element.properties文件中 tab5字段
		driver.findElement(GetByLocator.getLocator("persioninfo")).click();
		driver.findElement(GetByLocator.getLocator("edit")).click();
		//如果存在男那么就选女，否则就选男
		String genderVlaue="";
		AndroidElement gender = driver.findElement(GetByLocator.getLocator("gender"));
		if(driver.isElementExist(GetByLocator.getLocator("male"))){
			gender.click();
			driver.findElement(GetByLocator.getLocator("female")).click();
			genderVlaue="女";
		}else{
			gender.click();
			driver.findElement(GetByLocator.getLocator("male")).click();
			genderVlaue="男";
		}
		//一句话描述
		AndroidElement headline = driver.findElement(GetByLocator.getLocator("headline")) ;
		String headlineOld = headline.getText();
		String headlinNew = RandomUtil.getRndStrZhByLen(12);//调用老师写的RandomUtil随机中文字符长度为12个
		//这里要注意一下如果随机生成的值与旧的值一致，那么要做一下判断
		while(headlineOld.equals(headlinNew)){//如果随机生成的字符串的值和旧的值一样那么我就再生成一次
			headlinNew = RandomUtil.getRndStrZhByLen(12);
		}
		headline.sendKeys(headlinNew);
		
		//个人介绍
		AndroidElement description = driver.findElement(GetByLocator.getLocator("description")) ;
		String descriptionOld = headline.getText();
		String descriptionNew = RandomUtil.getRndStrZhByLen(20);//调用老师写的RandomUtil随机中文字符长度为12个
		//这里要注意一下如果随机生成的值与旧的值一致，那么要做一下判断
		while(descriptionOld.equals(descriptionNew)){//如果随机生成的字符串的值和旧的值一样那么我就再生成一次
			descriptionNew = RandomUtil.getRndStrZhByLen(20);
		}
		description.sendKeys(descriptionNew);
	
	//居住地
		AndroidElement location = driver.findElement(GetByLocator.getLocator("location")) ;
		String locationOld = headline.getText();
		String locationNew = RandomUtil.getRndStrZhByLen(15);//调用老师写的RandomUtil随机中文字符长度为12个
		//这里要注意一下如果随机生成的值与旧的值一致，那么要做一下判断
		while(locationOld.equals(locationNew)){//如果随机生成的字符串的值和旧的值一样那么我就再生成一次
			locationNew = RandomUtil.getRndStrZhByLen(15);
		}
		location.sendKeys(locationNew);
		
		//行业
		AndroidElement profession = driver.findElement(GetByLocator.getLocator("profession"));
		String professionOld = profession.getAttribute("text");
		profession.click();
		int swipeCount = RandomUtil.getExtentRandomNumber(3);//随机一个数字
		while(swipeCount>0){//如果数字大于0，执行下面的随机事件
			driver.swipeOnElement(GetByLocator.getLocator("listview"), "up", 1500);
		}
		List<AndroidElement> itemList = driver.findElements(GetByLocator.getLocator("item"));
		int index =Integer.valueOf( RandomUtil.randomInt(1, itemList.size()-1));//执行长度的最大和最小，为什么要从1开始
		AndroidElement item = itemList.get(index);
		while(item.getText().equals(professionOld)){//判断随机生成的行业名词是否和原来的一样，如果一样我的index就要重新生成
			index =Integer.valueOf( RandomUtil.randomInt(1, itemList.size()-1));//执行长度的最大和最小，为什么要从1开始
			item = itemList.get(index);
		}
		String professionNew = item.getText();
		item.click();
		
		List<String> oldInfoValue = new ArrayList<String>();//定义一个集合 来存放我们点击保存之前填写的各项的值
		//将内容加进来
		oldInfoValue.add(genderVlaue);//性别
		oldInfoValue.add(headlinNew);//一句话
		oldInfoValue.add(descriptionNew);//个人介绍
		oldInfoValue.add(locationNew);//居住地
		oldInfoValue.add(professionNew);//行业
		
		//点击保存
		driver.findElement(GetByLocator.getLocator("save")).click();
		//再次点击编辑按钮，进入编辑界面，获取各项信息
		driver.findElement(GetByLocator.getLocator("edit")).click();
		String genderResult  = driver.findElement(GetByLocator.getLocator("genderResult")).getAttribute("text");//获取性别的值
		String headlineResult = driver.findElement(GetByLocator.getLocator("headline")).getText();//获取一句话描述
		String descriptionResult = driver.findElement(GetByLocator.getLocator("description")).getText();//获取描述信息
		String locationResult = driver.findElement(GetByLocator.getLocator("location")).getText();//获取居住地
		String professionResult = driver.findElement(GetByLocator.getLocator("profession")).getText();//获取行业
		
		//再定义一个集合存放再次点击编辑按钮进来看到的值(把保存后的信息都保存在结果集合里)
		List<String> resultList = new ArrayList<String>();
		resultList.add(genderResult);
		resultList.add(headlineResult);
		resultList.add(descriptionResult);
		resultList.add(locationResult);
		resultList.add(professionResult);
		//断言两个集合里是否一致，一致表明保存成功
		Boolean result = driver.listStrEquals(oldInfoValue, resultList);
		try {
			Assert.assertTrue(result);	//对比两个集合的字符串是否一致 是不是true
		} catch (Error e) {
			SendMail sm = new SendMail();
			sm.send("个人资料测试用例", "个人资料测试用例修改失败", new String[] {"562068121@qq.com"});
			Assert.fail("个人资料修改失败", e);
		}
	
	}
	
	
	@AfterClass
	public void  afterClass(){
		
	}
	
		
}
