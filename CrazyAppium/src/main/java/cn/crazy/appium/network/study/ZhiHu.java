package cn.crazy.appium.network.study;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;


import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.mobile.NetworkConnection;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



import io.appium.java_client.MobileBy.ByAndroidUIAutomator;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;

public class ZhiHu {
	
	public AndroidDriver<AndroidElement> driver;
	
	//构造方法，用来给driver赋值
	public ZhiHu(AndroidDriver<AndroidElement> driver){
		this.driver=driver;
	}
	
	public void login(String user){
		driver.findElement(By.name("取消")).click();
		//name定位方式对应两个属性，一个是text属性，一个是content-desc属性
		driver.findElementByName("登录").click();
		//driver.findElementByAccessibilityId("登录").click();
		//id定位方式对应的是resource-id属性全部的值
		driver.findElementById("com.zhihu.android:id/username").sendKeys(user+"@163.com");
		driver.findElementById("com.zhihu.android:id/password").sendKeys("12345678");
		driver.findElementById("com.zhihu.android:id/btn_progress").click();
		WebDriverWait wait=new WebDriverWait(driver,30);
			//找元素，如果元素存在则返回改对象，不存在则报错
		try {
			AndroidElement suibian=(AndroidElement) 
					wait.until(ExpectedConditions.presenceOfElementLocated(By.name("搜索话题、问题或人")));
			System.out.println("成功");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("失败");
		}
		
//		AndroidElement suibian1=(AndroidElement) wait.until(ExpectedConditions.elementToBeClickable(By.id("com.zhihu.android:id/btn_confirm")));
		
//		try {
//			driver.findElementByName("搜索话题、问题或人");
//			System.out.println("登录成功");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			System.out.println("登录失败");
//		}
	}
	
	public void loginWithSelendroid() throws Exception{
		driver.findElementByLinkText("登录").click();
		//driver.findElementByAccessibilityId("登录").click();
		//id定位方式对应的是resource-id属性斜杠后面的值
		driver.findElementById("username").sendKeys("crazysand_001@163.com");
		driver.findElementById("password").sendKeys("12345678");
		driver.findElementById("btn_progress").click();
		try {
			driver.findElementByLinkText("搜索话题、问题或人");
			System.out.println("登录成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("登录失败");
		}
		
//		//CrazySwipe swipe=new CrazySwipe(driver);
//		int i=10;
//		while(i>0){
//			Thread.sleep(500);
//			swipe.swipe("up", 1000);
//			i--;
//		}
	}
	
/*	//关注首页所有文章
	public void attetionWithXpath() throws Exception{
		Thread.sleep(10000);
		CrazySwipe	swipe=new CrazySwipe(driver);
		//android.widget.TextView[@resource-id='com.zhihu.android:id/title']
		int i=5;
		while(i>0){
			List<AndroidElement> articleList=driver.findElements(By.xpath("//android.widget.TextView[@resource-id='com.zhihu.android:id/title']"));
			for(AndroidElement ae:articleList){
				ae.click();
				//android.widget.TextView[@text='关注']
				Thread.sleep(3000);
				try {
					driver.findElement(By.xpath("//android.widget.TextView[@text='关注']")).click();
					if(driver.getPageSource().contains("已关注")){
						System.out.println("关注成功");
					}else{
						System.out.println("关注失败");
					}
				
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("已经关注了，找下一个吧");
				}
				Thread.sleep(500);
				driver.pressKeyCode(AndroidKeyCode.BACK);
			}
			Thread.sleep(1000);
			swipe.swipe("up", 3000);
			Thread.sleep(2000);
			i--;
		}
	}*/
/*	public void logout(){
//		List<AndroidElement> caidan=driver.findElementsByClassName("android.support.v7.app.a$c");
//		caidan.get(4).click();
		//android.widget.HorizontalScrollView/android.widget.LinearLayout/*[@index='4']
		driver.findElementByXPath("//android.widget.HorizontalScrollView/android.widget.LinearLayout/*[@index='4']").click();
		//driver.findElementByName("设置").click();
		CrazySwipe	cs=new CrazySwipe(driver);
		cs.swipeToUp(500);
		String setXpath="//android.widget.ScrollView/android.support.v7.widget.LinearLayoutCompat/android.support.v7.widget.LinearLayoutCompat[2]/*[3]";
		driver.findElement(By.xpath(setXpath)).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//driver.swipe(540, 1700, 540, 300, 500);
		CrazySwipe swipe=new CrazySwipe(driver);
		swipe.swipe("up", 500);
		driver.findElementByName("退出帐号").click();
		driver.findElementByName("确定").click();
		
	}*/
	
	public void settings() throws Exception{
		driver.findElementByAndroidUIAutomator("new UiSelector().className(\"android.support.v7.app.a$c\").instance(4)").click();
		driver.findElementByAndroidUIAutomator("new UiSelector().text(\"设置\")").click();
		//driver.findElement(ByAndroidUIAutomator.name("开启")).click();
		AndroidElement switchBtn=driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.zhihu.android:id/switchWidget\").instance(2)");
		//AndroidElement switchBtn=driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.zhihu.android:id/switchWidget\").checked(\"false\").instance(0)");
		String oldChecked=switchBtn.getAttribute("checked");
		String oldText=switchBtn.getAttribute("text");//getText()
		switchBtn.click();
		Thread.sleep(3000);
		driver.findElement(By.className("android.widget.ImageButton")).click();
		driver.findElementByAndroidUIAutomator("new UiSelector().text(\"设置\")").click();
		AndroidElement newSwitchBtn=driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.zhihu.android:id/switchWidget\").instance(2)");
		String newChecked=newSwitchBtn.getAttribute("checked");
		String newText=newSwitchBtn.getText();
		if(!oldChecked.equals(newChecked)){
			System.out.println("操作成功");
			if(oldChecked.equals("true")){
				System.out.println("关闭成功");
			}else{
				System.out.println("开启成功");
			}
			
		}else{
			System.out.println("操作失败");
		}
		System.out.println(oldChecked+"==="+newChecked);
		if(!oldText.equals(newText)){
			System.out.println("操作成功");
			if(oldText.equals("开启")){
				System.out.println("关闭成功");
			}else{
				System.out.println("开启成功");
			}
			
		}else{
			System.out.println("操作失败");
		}
		
	}
	public void search(String searchWord) throws Exception{
		AndroidElement searchInput=driver.findElement(By.id("com.zhihu.android:id/input"));
		getAttr(searchInput);
		searchInput.replaceValue(searchWord);
		searchInput.tap(1, 50);
		int end_x=getElementEnd(searchInput).x;
		int end_y=getElementEnd(searchInput).y;
		searchInput.sendKeys(searchWord);
		Thread.sleep(5000);
//		int resultCounts=driver.findElements(By.id("com.zhihu.android:id/title")).size()
//				+driver.findElements(By.id("com.zhihu.android:id/name")).size();
//		
//		int regexCounts=driver.findElementsByAndroidUIAutomator("new UiSelector().textContains(\"test\")").size()-1;
//		System.out.println(resultCounts+"====="+regexCounts);
//		if(resultCounts==regexCounts){
//			System.out.println("搜索成功");
//		}else{
//			System.out.println("搜索失败");
//		}
		//每次判断当前屏幕的每一个title是否包含test，如果都包含继续下一屏幕，如果有任意一个不包含，那么就结束测试，因为结果已经失败了
		//如何确保滑动到底部？
		//如何确保滑动屏幕正好滑动一屏幕？没有办法确保，只能确保尽量少的重复，需要通过调整滑动坐标点的距离及持续时间
	//	CrazySwipe	cs=new CrazySwipe(driver);
		Boolean flag=driver.findElement(By.id("com.zhihu.android:id/name")).getText().toLowerCase().contains(searchWord);
		List<String> resultTexts=new ArrayList<String>();
		while(flag){
			List<AndroidElement> titleList=driver.findElements(By.id("com.zhihu.android:id/title"));
			for(AndroidElement ae:titleList){
				String text=ae.getAttribute("text");//ae.getText();
				if(!resultTexts.contains(text)){
					resultTexts.add(text);
				}else{
					System.out.println(text+">>已经被加过了");
				}
				
				if(text.toLowerCase().contains(searchWord)){
					continue;
				}else{
					flag=false;
					break;
				}
			}
			if(flag){
				try {
					driver.findElement(By.name("没有更多内容")).isDisplayed();
					break;
				} catch (Exception e) {
					// TODO: handle exception
		//			cs.swipe("up", 3000);
				}
				
			}else{
				break;
			}
		}
		System.out.println(flag);
		if(resultTexts.size()>0){
			System.out.println("数据条数是:"+(resultTexts.size()+1));
		}else{
			System.out.println(resultTexts.size());
		}
		
	}
	//逐个清除输入框，用在senkeys默认无法清除干净输入框原有内容时，或者clear方法无法彻底清除输入框原有内容时
	//此方法对密码输入框无效
	public void clearOneByOne(AndroidElement element){
		element.click();
		String text=element.getText();
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_MOVE_END);
		for(int i=0;i<text.length();i++){
			driver.pressKeyCode(AndroidKeyCode.BACKSPACE);
		}
	}
	//针对密码输入框清除不彻底时使用
	public void clearPwd(AndroidElement element){
		element.click();
		//String text=element.getText();
		driver.pressKeyCode(AndroidKeyCode.KEYCODE_MOVE_END);
		for(int i=0;i<20;i++){
			driver.pressKeyCode(AndroidKeyCode.BACKSPACE);
		}
	}
	public void getAttr(AndroidElement element){
		System.out.println("元素的text"+element.getAttribute("name"));//默认获取content-desc的值，如果content-desc属性的值为空，则获取属性text的值
		System.out.println("元素的text"+element.getText());
		System.out.println("元素的text"+element.getAttribute("text"));
		System.out.println("元素的resouce-id"+element.getAttribute("resourceId"));
		System.out.println("元素的class"+element.getAttribute("className"));
		//System.out.println("元素的content-desc"+element.getAttribute("content-desc"));//这是错误的
		System.out.println("元素的checkable"+element.getAttribute("checkable"));
		System.out.println("元素的checked"+element.getAttribute("checked"));
		System.out.println("元素的clickable"+element.getAttribute("clickable"));
		System.out.println("元素的enabled"+element.getAttribute("enabled"));
		System.out.println("元素的focusable"+element.getAttribute("focusable"));
		System.out.println("元素的focused"+element.getAttribute("focused"));
		System.out.println("元素的scrollable"+element.getAttribute("scrollable"));
		System.out.println("元素的long-clickable"+element.getAttribute("longClickable"));
		System.out.println("元素的selected"+element.getAttribute("selected"));
	}
	public Point getElementEnd(AndroidElement element){
		//元素的中心点坐标
//		int center_x=element.getCenter().x;
//		int center_y=element.getCenter().y;
		//元素的起始点坐标
		int start_x=element.getLocation().x;
		int start_y=element.getLocation().y;
		//元素的大小，即宽和高
		int width=element.getSize().width;
		int height=element.getSize().height;
		//结束点坐标
		int end_x=start_x+width;
		int end_y=start_y+height;
		Point p=new Point(end_x, end_y);
		return p;
	}
	//微信通讯录快捷筛选条
	public List<Point> splitElement(AndroidElement element){
		List<Point> charList=new ArrayList<Point>();
		//元素的起始点坐标
		int start_x=element.getLocation().x;
		int start_y=element.getLocation().y;
		//元素的大小，即宽和高
		int width=element.getSize().width;
		int height=element.getSize().height;
		
		for(int i=0;i<29;i++){
			int center_x=start_x+width/2;
			int center_y=start_y+(2*i+1)*height/(29*2);
			Point p=new Point(center_x, center_y);
			charList.add(p);
			
		}
		return charList;
	}
	
	public void clickQuikSearch(AndroidElement element,String ch){
		List<Point> charList=splitElement(element);
		Map<String,Integer> charMap=new HashMap<String,Integer>();
		charMap.put("A", 2);
		charMap.put("B", 3);
		charMap.put("C", 4);
		charMap.put("D", 5);
		charMap.put("E", 6);
		charMap.put("F", 7);
		charMap.put("G", 8);
		charMap.put("H", 9);
		charMap.put("I", 10);
		charMap.put("J", 11);
		driver.tap(1, charList.get(charMap.get(ch)).x, charList.get(charMap.get(ch)).y, 100);
	}
	public void swipeOnElement(AndroidElement element){
		element.swipe(SwipeElementDirection.LEFT, 500);
		element.swipe(SwipeElementDirection.RIGHT, 500);
		element.swipe(SwipeElementDirection.UP, 500);
		element.swipe(SwipeElementDirection.DOWN, 500);
		element.swipe(SwipeElementDirection.LEFT,20,30,500);
	}
	
	public void touchActionTest(AndroidElement element){
		TouchAction ta=new TouchAction(driver);
		//长按元素
		ta.longPress(element).release().perform();//长按元素对象，前提是先定位到这个对象
		ta.longPress(element,20,20).release().perform();//长按元素左上脚，偏移量都是20，意味着长按的位置是元素的起始x+20，y+20
		ta.longPress(element, 5000).release().perform();
		ta.longPress(element, 30, 30, 3000).release().perform();
		//长按某个坐标点
		ta.longPress(300, 500).release().perform();
		ta.longPress(300, 500, 3000).release().perform();
		
		//按某元素
		ta.press(element).release().perform();
		ta.press(element, 40, 50).release().perform();
		ta.press(300, 500).release().perform();
		element.zoom();//放大
		element.pinch();//缩小
		element.tap(1, 50);
		//点元素或者坐标
		ta.tap(element).release().perform();
		ta.tap(element, 30, 30).release().perform();
		ta.tap(300, 500).release().perform();
		
		//拖动某元素到某个点,moveto里的坐标是相对坐标，相对于前一个操作的点的
		ta.longPress(element).moveTo(30, 50).release().perform();
		ta.longPress(300, 500).moveTo(30, 50).release().perform();
		ta.longPress(element, 40, 30).moveTo(element).release().perform();
		
		//按住移动
		ta.press(element).moveTo(element).moveTo(element).moveTo(element).release().perform();
		ta.press(300, 500).moveTo(30, 50).moveTo(-40, 40).moveTo(39, 50).release().perform();
		ta.press(30, 40).waitAction(500).moveTo(30, 40).waitAction(500).moveTo(30, 30).release().perform();
		
	}
	
	public void comment(){
		int count=Integer.valueOf(driver.findElements(By.id("com.zhihu.android:id/comment_count")).get(2).getAttribute("text").split(" 评")[0]);
		System.out.println("评论数是："+count);
		driver.findElements(By.id("com.zhihu.android:id/title")).get(2).click();
		driver.findElement(By.id("com.zhihu.android:id/comment_btn")).click();
		
		
	}
	public void startMaiMai() throws Exception{
		driver.startActivity("com.taou.maimai", "com.taou.maimai.MainActivity");
		//driver.findElement(By.name("下次再说")).click();
		driver.findElement(By.name("匿名八卦")).click();
		AndroidElement comment=driver.findElements(By.id("com.taou.maimai:id/gossip_comment_count")).get(2);
		int count=Integer.valueOf(comment.getAttribute("text"));
		
		comment.click();
		AndroidElement list=driver.findElement(By.id("android:id/list"));
		int countResult=0;
		List<String> conntextanduser=new ArrayList<String>();
		while(true){
			Thread.sleep(3000);
			
			List<AndroidElement> user=driver.findElements(By.id("com.taou.maimai:id/gossip_comment_view_name"));
			List<AndroidElement> context=driver.findElements(By.id("com.taou.maimai:id/gossip_comment_view_content"));
			List<String> contextTextOld=new ArrayList<String>();
			List<String> contextTextNew=new ArrayList<String>();
			for(AndroidElement ae:context){
				String text=ae.getText();

				contextTextOld.add(ae.getText());
			}
			int c=0;
//			if(!conntextanduser.isEmpty()){
//				if(conntextanduser.get(conntextanduser.size()-1).equals(contextTextOld.get(0))){
//					c=1;
//				}
//			}

			for(;c<contextTextOld.size();c++){
				if(!conntextanduser.contains(contextTextOld.get(c))){
					conntextanduser.add(contextTextOld.get(c));
				}else{
					System.out.println(contextTextOld.get(c)+"========重复了");
				}
				
			}
		
			//countResult=countResult+contextTextOld.size();
			list.swipe(SwipeElementDirection.UP, 30, 30, 3500);
			Thread.sleep(1000);
			List<AndroidElement> contextNew=driver.findElements(By.id("com.taou.maimai:id/gossip_comment_view_content"));
			for(AndroidElement ae:contextNew){
				contextTextNew.add(ae.getText());
			}
			if(equlasList(contextTextOld,contextTextNew)){
				System.out.println("滑动到底部了结束");
				break;
			}else{
				System.out.println("不一样继续滑动");
			}
			
		}
			
	
		System.out.println("期望是："+count+"=====实际是："+conntextanduser.size());
	}
	
	public static boolean equlasList(List<String> oldList,List<String> newList){
		boolean flag=false;
		if(!oldList.isEmpty()&&!newList.isEmpty()){
			if(oldList.size()==newList.size()){
				for(int i=0;i<newList.size();i++){
					System.out.println(oldList.get(i)+"======="+newList.get(i));
					if(oldList.get(i).equals(newList.get(i))){
						flag=true;
						continue;
					}
					else{
						flag=false;
						break;
					}
				}
			}else{
				System.out.println("大小不一样");
			}
		}else{
			System.out.println("有一个为空了");
		}
		return flag;
	}
	
	public void lanuage() throws Exception{
		//String settingXpath="//android.widget.FrameLayout[@reouce-id='com.vphone.launcher:id/hotseat']\android.view.View[1]\android.view.View[1]\android.widget.TextView[5]";
		//driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id='com.vphone.launcher:id/hotseat']/android.view.View[1]/android.view.View[1]/android.widget.TextView[5]")).click();
		driver.startActivity("com.android.settings", "com.android.settings.Settings");
		System.out.println(driver.currentActivity());
		Thread.sleep(2000);
		List<AndroidElement> titleList1=driver.findElements(By.id("android:id/title"));
		List<String> titleTextList=new ArrayList<String>();
		List<AndroidElement> totalList=new ArrayList<AndroidElement>();
		if(titleList1.size()>=18){
			titleList1.get(17).click();
		}else{
			while(true){
				Thread.sleep(2000);
				List<AndroidElement> titleList2=driver.findElements(By.id("android:id/title"));
				for(AndroidElement ae:titleList2){
					if(!titleTextList.contains(ae.getAttribute("text"))){
						titleTextList.add(ae.getText());
						totalList.add(ae);
					}
				}
				
				if(totalList.size()>=18){
					totalList.get(17).click();
					break;
				}
				AndroidElement list=driver.findElement(By.id("android:id/list"));
				list.swipe(SwipeElementDirection.UP, 30, 30, 1000);
				
			}

			
		}

		
	}
	
	public void network(){
		NetworkConnectionSetting netword=new NetworkConnectionSetting(1);
		driver.setNetworkConnection(netword);
		System.out.println(driver.getNetworkConnection().value);
	}
	
	public void xianshidengdai(){
		//指定时间内返回查找的元素
		WebDriverWait wait=new WebDriverWait(driver,50);
		AndroidElement element=wait.until(new ExpectedCondition<AndroidElement>() {
			@Override
			public AndroidElement apply(WebDriver driver) {
			return (AndroidElement) driver.findElement(By.id("com.zhihu.android:id/login_and_register"));
			}
		});
		
		
		//指定时间内判断元素文本是否包含指定文本
		WebDriverWait wait1=new WebDriverWait(driver,40);

		Boolean flag=wait1.until(new ExpectedCondition<Boolean>() {

					@Override
					public Boolean apply(WebDriver arg0) {
						// TODO Auto-generated method stub
						return driver.findElement(By.id("com.zhihu.android:id/login_and_register")).getText().contains("登录或注册");
					}
				});
		
		//判断元素在指定时间内是否被选中		
		WebDriverWait wait2=new WebDriverWait(driver,40);

		Boolean flag1=wait2.until(new ExpectedCondition<Boolean>() {

					@Override
					public Boolean apply(WebDriver arg0) {
						// TODO Auto-generated method stub
						return driver.findElement(By.id("android:id/checkbox")).getAttribute("checked").equals("true");
					}
				});
		
	}
	
	/*public static void main(String[] args) throws Exception {
		AndroidDriver<AndroidElement> driver=InitDriver.initDriver("4723","127.0.0.1:62001","com.zhihu.android","com.zhihu.android.app.ui.activity.MainActivity");
		ZhiHu zhihu=new ZhiHu(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(6000);
		//zhihu.login();
		//Thread.sleep(10000);
//		driver.pressKeyCode(AndroidKeyCode.HOME);
//		driver.pressKeyCode(AndroidKeyCode.HOME);
//		Thread.sleep(3000);
//		zhihu.lanuage();
		System.out.println(driver.getPageSource());
		//portrait 竖屏；landscape 横屏
		System.out.println(driver.getOrientation().value());
		driver.resetApp();
		driver.openNotifications();
		AndroidElement element=driver.findElement(By.id("xx"));
		driver.tap(1, element, 50);
		driver.tap(1, 300, 500, 100);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//锁屏
		driver.lockDevice();
		//解锁屏幕
		driver.unlockDevice();
		//判断屏幕是否锁定
		driver.isLocked();
		//判断是否锁屏，如果是则解锁屏幕，你的测试机通常我们设置为不锁屏不休眠或者说最大锁屏时间
		if(driver.isLocked()){
			driver.unlockDevice();
		}
		
		//长按设备物理键
		driver.longPressKeyCode(AndroidKeyCode.HOME);
		File file=driver.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(file, new File("images/file.png"));
//		driver.installApp("C:/Users/LXG/Desktop/maimai.apk");
//		if(driver.isAppInstalled("com.taou.maimai")){
//			System.out.println("安装成功");
//		}else{
//			System.out.println("安装失败");
//		}
//		driver.removeApp("com.taou.maimai");
//		if(driver.isAppInstalled("com.taou.maimai")){
//			System.out.println("卸载失败");
//		}else{
//			System.out.println("卸载成功");
//		}
		//driver.rotate(ScreenOrientation.LANDSCAPE);

//		TouchAction ta=new TouchAction(driver);
//		AndroidElement jinri=driver.findElement(By.name("今日头条"));
//		jinri.tap(1, 3000);
//		AndroidElement zhihuTu=driver.findElement(By.name("知乎"));
////		ta.press(input).release().perform();
////		ta.tap(input).release().perform();
//		ta.longPress(jinri).moveTo(zhihuTu).release().perform();
		//ta.press(300, 500).moveTo(30, 50).moveTo(100, 80).moveTo(-30, -60).moveTo(20, 30).release().perform();
		
		//zhihu.search("test");
		//zhihu.settings();
		//zhihu.attetionWithXpath(); 
		//zhihu.logout();
		Thread.sleep(3000);
		driver.quit();
	}*/

}
