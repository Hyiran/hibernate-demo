package cn.crazy.appium.network.study;

import java.util.List;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;







import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.AndroidSpecific;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ProUtil;
import io.appium.java_client.android.AndroidElement;
/**
 * 测试用到断言、截图、元素为空的判断、返回、滑动
 * @author qinghua
 *
 */

public class Study extends DriverInit{
	AndroidDriverBase driver;
	@Parameters({"udid","port"})
	@BeforeClass
	public void beforeClass(String udid,String port) throws Exception{
		driver=driverInit(udid,port);
		System.out.println("study driver is "+driver);
	}
	@AfterClass
	public void afterClass(){
		System.out.println("study quit");
		driver.quit();
	}
	@Test(priority=1)
	public void login() throws Exception{
		AndroidElement username=driver.findElement(By.name("登录或注册"));
		if(username!=null){
			username.click();
		}
		driver.findElement(By.id("com.zhihu.android:id/email_or_phone")).sendKeys("crazysand_001@163.com");
		driver.findElement(By.id("com.zhihu.android:id/password")).sendKeys("12345678");
//		List<AndroidElement> btnList=driver.findElements(By.name("登录"));
//		btnList.get(1).click();
//		driver.findElement(By.name("忽略"), 10).click();
//		List<AndroidElement> loginNameList=driver.findElements(By.name("登录"));
//		driver.selectElementFromList(loginNameList, 1).click();
		driver.wait(10000);
		driver.findElement(By.id("com.zhihu.android:id/btn_confirm")).click();
		driver.findElement(By.name("忽略"), 10).click();
//		if(driver.isElementExist(By.name("首页"), 10)){
//			System.out.println("登录成功");
//		}else{
//			System.out.println("登录失败");
//		}
		Boolean flag=driver.isElementExist(By.name("首页"), 10);
		try {
			driver.takeScreen("images\\", "login.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(driver.isElementExist(By.name("首页"),10),true);
		driver.swipeUntilElement(By.name("哪个人美得让你心动？"), "up", 1000, 30).click();
		AndroidElement iKnow=driver.findElement(By.name("我知道了"));
		if(iKnow!=null){
			iKnow.click();
		}else{
			System.out.println("iKnow is: "+iKnow);
		}
		//driver.swipeUntilBoundary("up", By.id("com.zhihu.android:id/title"));
		AndroidElement element=driver.findElement(By.name("关注"));
		driver.takeScreenForElement(element, "images/", "zhihu");
	}
	//@Test(dependsOnMethods="login")
	public void putQuestions(){
		driver.findElement(By.name("知乎")).click();
		driver.findElement(By.name("提问")).click();
		AndroidElement iKnow=driver.findElement(By.name("我知道了"));
		if(null!=iKnow){
			iKnow.click();
		}
		driver.findElement(By.id("com.zhihu.android:id/content")).sendKeys("sssssssssss");
		driver.findElement(By.name("发布")).click();
		AndroidElement confirm=driver.findElement(By.name("确定"));
		if(null!=confirm){
			confirm.click();
		}
		AndroidElement xx=driver.findElement(By.id("com.zhihu.android:id/content"));
		String text=xx.getText();
		driver.pressBack();
		//添加相关话题
		Assert.assertEquals(text, "添加相关话题", "没有跳转到话题输入框");
	}
	//@Test(dependsOnMethods="login")
	public void attentionArticle() throws InterruptedException{
		driver.findElement(By.id("com.zhihu.android:id/title")).click();
		AndroidElement iKnow=driver.findElement(By.name("我知道了"));
		if(null!=iKnow){
			iKnow.click();
		}
		AndroidElement element=driver.swipeUntilElement(By.name("关注"),"up",500,20);
		if(null!=element){
			element.click();
			Assert.assertEquals(driver.isElementExist(By.name("取消关注")), true);
		}else{
			System.out.println("这篇文章已经被关注");
		}
		driver.pressBack();
	}
	//@Test(dependsOnMethods="putQuestions")
	public void logout(){
		driver.findElement(By.name("更多选项")).click();
		driver.findElement(By.name("登出")).click();
	}
//	@AfterClass
//	public void afterClass(){
//		driver.quit();
//	}
}
