package cn.crazy.appium.base;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cn.crazy.appium.util.DosCmd;
import cn.crazy.appium.util.Log;
import cn.crazy.appium.util.ProUtil;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
/**
 * 封装自己的driver类，包含重写及自主封装的方法
 * @author 沙陌&&狂沙
 *
 */
public class AndroidDriverBase extends AndroidDriver{//AndroidDriverBase 继承AndroidDriver
	
	private Log logger=Log.getLogger(AndroidDriverBase.class);//使用日志类
	public  String input;//原有输入法
	public  String udid;//设备的udid
	/**
	 * 构造方法
	 * @param remoteAddress
	 * @param desiredCapabilities
	 * @throws Exception 
	 */
	/**
	 * 
	 * @param server 服务appium
	 * @param port     appium与设备通讯的端口
	 * @param capsPath			如果要安装这个路径要有参数
	 * @param udid				设备的udid，可通过adb devices 获取该值
	 * @param input				设备的输入法
	 * @param installed			是否安装
	 * @throws Exception
	 */
	public AndroidDriverBase(String server,String port,String capsPath,String udid,String input,boolean installed) throws Exception{
		super(new URL(server+":"+port+"/wd/hub"),new CrazyCapabilities().initCaps(capsPath, udid,installed));
		this.input=input;
		this.udid=udid;
	}
	public AndroidDriverBase(String server,String port,String capsPath,String udid,String input,int waitFlag) throws Exception{
		super(new URL(server+":"+port+"/wd/hub"),new CrazyCapabilities().initCapsWait(capsPath, udid));
		this.input=input;
		this.udid=udid;
	}
	/**
	 * 获取app应用占用的屏幕宽度
	 * @return 返回宽度
	 */
	public int appScreenWidth(){
		int width=super.manage().window().getSize().getWidth();
		//System.out.println("获取app应用占用的屏幕宽度为->"+width);
		logger.info("获取app应用占用的屏幕宽度为->"+width);
		return width;
	}
	/**
	 * 获取app应用占用的屏幕高度
	 * @return 返回高度
	 */
	public int appScreenHeight(){
		int height=super.manage().window().getSize().getHeight();
		//System.out.println("获取app应用占用的屏幕宽度为->"+height);
		logger.info("获取app应用占用的屏幕宽度为->"+height);
		return height;
	}
	/**
	 * 判断元素是否存在
	 * @param by By.id("xxxx") By.xpath("xxx")  NoSuchElementException
	 * @return 存在则返回true，不存在则返回false
	 */
	public boolean isElementExist(By by){
		try {
			super.findElement(by).isDisplayed();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			logger.info("你要找的元素不存在");
			return false;
		}
	}
	/**
	 * 在指定超时时间内元素是否存在，如存在则立即返回结果，如不存在则在超时后返回结果
	 * @param by 定位对象
	 * @param timeout 超时时间
	 * @return 指定时间内任意时间该元素出现则停止等待返回true，指定时间内没出现则返回false
	 */
	public boolean isElementExist(By by,int timeout){
		try {
			this.implicitlyWaitZero();
			new WebDriverWait(this,timeout).until(ExpectedConditions.presenceOfElementLocated(by));
			this.implicitlyWaitDefault();
			logger.info(" finds the element："+by);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			//e.printStackTrace();
			logger.debug("element not found"+by);
			this.implicitlyWaitDefault();
			return false;
		}
		
	}
	/**
	 * 查找元素存在则返回该元素对象，不存在则返回null
	 * @param by
	 * @return
	 */
	@Override
	public AndroidElement findElement(By by){
		try {
			AndroidElement element=(AndroidElement)super.findElement(by);
			logger.info("找到该元素："+by);
			return element;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("没有找到该元素："+by);
			return null;
		}
	}
	/**
	 * 在指定超时时间内查找元素是否存在，如存在则立即返回AndroidElement对象，如不存在则在超时后返回null
	 * @param by
	 * @param timeout 单位是秒
	 * @return
	 */
	public AndroidElement findElement(final By by,int timeout){
		try {
			AndroidElement element=new WebDriverWait(this, timeout).until(new ExpectedCondition<AndroidElement>() {
				@Override
				public AndroidElement apply(WebDriver driver) {
					// TODO Auto-generated method stub
					return (AndroidElement) driver.findElement(by);
				}
			});
			logger.info("找到该AndroidElement对象："+by);
			return element;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("没有找到该AndroidElement对象："+by);
			return null;
		}
	}
//	@Override
//	public List findElements(By by){
//		List<AndroidElement> elementList=super.findElements(by);
//		return elementList;
//	}
	/**
	 * 滑动屏幕方法，通过参数实现各方向滑动
	 * @param direction 方向参数，值为"up"、"down"、"right"、"left"
	 * @param duration 滑动时间，单位毫秒
	 */
	public void swipe(String direction,int duration){
		try {
			SwipeScreen swipeSreen=new SwipeScreen(this);
			swipeSreen.swipe(direction, duration);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("不明参数请重新审核");
			//System.out.println(e);
		}
	}
	/**
	 * 在元素上滑动
	 * @param element 元素对象
	 * @param derction 方向参数，值为"up"、"down"、"right"、"left"
	 * @param duration
	 */
	
	public void swipeOnElement(AndroidElement element,String derction,int duration){
		String derc=derction.toLowerCase();
		switch (derc) {
		case "up":
			element.swipe(SwipeElementDirection.UP, 10, 10, duration);//根据元素偏移量来滑动
			break;
		case "down":
			element.swipe(SwipeElementDirection.DOWN, 10, 10, duration);
			break;
		case "left":
			element.swipe(SwipeElementDirection.LEFT, 10, 10, duration);
			break;
		case "right":
			element.swipe(SwipeElementDirection.RIGHT, 10, 10, duration);
			break;
		default:
			System.out.println("方向参数错误");
			break;
		}
	}
	/**
	 * 查找到该元素后进行滑动
	 * @param by
	 * @param derction
	 * @param duration
	 */
	public void swipeOnElement(By by,String derction,int duration){
		AndroidElement	element=this.findElement(by);
		this.swipeOnElement(element, derction, duration);
	}
	/**
	 * 向某方向滑动直到某元素出现
	 * @param by 查找对象
	 * @param direction 方向
	 * @param duration 每次滑动时间，单位毫秒
	 * @param findCount 查找次数
	 * 应用场景:滑动到顶部底部左到底右到底
	 */
	public boolean swipeUntilElementAppear(By by,String direction,int duration,int findCount){
		//this.implicitlyWait(3);
		boolean flag=false;
		while(!flag&&findCount>0){
			try {
				super.findElement(by);
				flag=true;
			} catch (Exception e) {
				// TODO: handle exception
				this.swipe(direction,duration);
				findCount--;
			}
		}
		return flag;
	}
	/**
	 * 滑动到这个元素，并且定位到这个元素，
	 * 我再把这个元素返回出来
	 * @param by
	 * @param direction
	 * @param duration
	 * @param findCount
	 * @return
	 */
	
	public AndroidElement swipeUntilElement(By by,String direction,int duration,int findCount){
		//this.implicitlyWait(3);
		AndroidElement element=null;
		boolean flag=false;
		while(!flag&&findCount>0){
			try {
				element=(AndroidElement) super.findElement(by);
				flag=true;
			} catch (Exception e) {
				// TODO: handle exception
				this.swipe(direction,duration);
				findCount--;
			}
		}
		return element;
	}
	/**
	 * 与服务端断开连接,断开前重置输入法
	 */
	public void quit(){
		this.resetInput();
		super.quit();
	}
	/**
	 * 单击某元素，使用tap方式
	 * @param element
	 * @param duration
	 */
	public void tapSingle(AndroidElement element){
		super.tap(1, element,100);
	}
	/**
	 * touchaction的方式点击元素
	 * @param element
	 */
	public void tap(AndroidElement element){
		try {
			TouchAction ta=new TouchAction(this);
			ta.tap(element).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 点击某个坐标点
	 * @param x
	 * @param y
	 */
	public void clickByCoordinate(int x,int y){
		try {
			TouchAction ta=new TouchAction(this);
			ta.tap(x,y).release().perform();
			//System.out.println("点击坐标"+x+"  "+y+"成功");
			logger.info("点击坐标"+x+"  "+y+"成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("点击坐标"+x+"  "+y+"报错");
		}
	}
	/**
	 * 找到该元素进行长按
	 * @param by
	 */
	public void longPress(By by){
		try {
			AndroidElement element=(AndroidElement) super.findElement(by);
//			TouchAction ta=new TouchAction(this);
//			ta.longPress(element).release().perform();
			this.longPress(element);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 元素长按
	 * @param by
	 */
	public void longPress(AndroidElement element){
		try {
			//AndroidElement element=(AndroidElement) super.findElement(by);
			TouchAction ta=new TouchAction(this);
			logger.info("长按该元素："+element);
			ta.longPress(element).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("长按该元素失败："+element);
			e.printStackTrace();
		}
	}
	/**
	 * 在某一个坐标点上长按
	 * @param x
	 * @param y
	 */
	public void longPress(int x,int y){
		try {
			TouchAction ta=new TouchAction(this);
			logger.info("长按("+x+","+y+") +成功");
			ta.longPress(x,y).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("长按("+x+","+y+") +失败");
			e.printStackTrace();
		}
	}
	/**
	 * 获取整个页面的资源
	 * @return
	 */
	public String getPageSouce(){
		return super.getPageSource();
	}
	/**
	 * 隐式等待
	 * @param timeout
	 */
	public void implicitlyWait(int timeout){
		super.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}
	/**
	 * 通过读取配置文件的方式来设置等待时间
	 */
	public void implicitlyWaitDefault(){
		ProUtil p=new ProUtil(CrazyPath.globalPath);
		super.manage().timeouts().implicitlyWait(Integer.valueOf(p.getPro("implicitlyWait")), TimeUnit.SECONDS);
	}
	/**
	 * 等待0秒
	 */
	public void implicitlyWaitZero(){
		super.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}
	
	/**
	 * 获取手机分辨率
	 * @return
	 */
	public Point getMobileSize(){
		//adb shell wm size
		DosCmd dos=new DosCmd();
		Point p=null;
		try {
			/**
			 * 创建一个String 类型list集合来存放执行 adb -s [udid] shell wm size 后的内容
			 */
			List<String> resList=dos.execCmdConsole("adb -s "+udid+" shell wm size");
			/*
			 * Physical size: 480x800
			 */
			String[] size=new String[2];
			if(resList.size()>0){
					size=resList.get(0).split(": ")[1].split("x");
					//获取到的数据Physical size: 480x800  用(: )冒号加空格对它进行分割拿索引的第二部分 480x800，再用split (x) 再拆分一下
			}
			//                                          分辨率x                              分辨率y
			//将分辨率x 和 y转换为一个point 去存储
			logger.info("取到分辨率宽："+Integer.valueOf(size[0])+"取到分辨率宽："+Integer.valueOf(size[1]));
			p=new Point(Integer.valueOf(size[0]),Integer.valueOf(size[1]));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return p;
	}
	/**
	 * 重置输入法
	 * @param input
	 *  adb 命令 http://note.youdao.com/noteshare?id=ee0be8e49e3964896183e1e20d23c8b5
	 */
	public void resetInput(){
		DosCmd dc=new DosCmd();
		try {
			dc.execCmdConsole("adb -s "+udid+"shell \"ime set "+input+"\"");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 切换输入法
	 * @param input
	 */
	public void resetInput(String input){
		DosCmd dc=new DosCmd();
		try {
			dc.execCmdConsole("adb shell \"ime set "+input+"\"");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 判断两个字符串集合list是否元素对应相等
	 * @param strSrc
	 * @param strDes
	 * @return
	 *应用场景：用在我们滑动屏幕的前后的内容是否一致
	 *在没有底部标识的时候，我们要滑动到底部 
	 * 
	 */
	public Boolean listStrEquals(List<String> strSrc,List<String> strDes){
		Boolean flag=false;
		if((!strSrc.isEmpty()&&strSrc!=null)&&(!strDes.isEmpty()&&strDes!=null)){
			if(strSrc.size()==strDes.size()){
				for(int i=0;i<strDes.size();i++){
					if(strSrc.get(i).equals(strDes.get(i))){
						flag=true;
						continue;
					}else{
						flag=false;
						//System.out.println(strSrc.get(i)+" "+strDes.get(i));
						break;
					}
				}
			}else{
				System.out.println("两个list大小不相等");
			}
		}else{
			System.out.println("list为空或者为null");
		}
		return flag;
	}
	/**
	 * 从指定androidelement集合中根据索引选取某一个
	 * @param srcList
	 * @param index
	 * @return
	 */
	public AndroidElement selectElementFromList(List<AndroidElement> srcList,int index){
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			if(index>=0&&index<srcList.size()){
				element=srcList.get(index);
			}else{
				System.out.println("集合大小为"+srcList.size()+"但是索引参数传值为"+index);
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	/**
	 * 从指定androidelement集合中根据索引选取某一个
	 * @param srcList
	 * @param index
	 * @return
	 */
	public AndroidElement selectElementFromList(By by,int index){
		List<AndroidElement> srcList=super.findElements(by);
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			if(index>=0&&index<srcList.size()){
				element=srcList.get(index);
			}else{
				System.out.println("集合大小为"+srcList.size()+"但是索引参数传值为"+index);
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	/**
	 * 从AndroidElement集合中选出text值与预期相符的第一个元素
	 * @param srcList
	 * @param strFind
	 * @return
	 */
	public AndroidElement selectElementFromList(List<AndroidElement> srcList,String strFind){
		AndroidElement element=null;
		if(!srcList.isEmpty()&&srcList!=null){
			for(AndroidElement ae:srcList){
				if(ae.getAttribute("text").equals(strFind)){
					element=ae;
					break;
				}
			}
		}else{
			System.out.println("集合为空或者为null");
		}
		return element;
	}
	/**
	 * 某方向滑动直到边界，如底部，顶部(在没有边界标识的时候使用)
	 * @param direction
	 * @param strSrc
	 * @param strDes
	 * @return
	 * 实现滑动前后的字符串集合list的对比
	 */
	public void swipeUntilBoundary(String direction,By by){
		boolean flag=false;
		while(!flag){
			List<String> strSrc=new ArrayList<String>();
			List<String> strDes=new ArrayList<String>();
			//滑动前定位元素并将元素的text添加到集合strSrc里
			List<AndroidElement> elementOld=super.findElements(by);
			for(AndroidElement ae:elementOld){
				strSrc.add(ae.getText());
				
			}
			//这里可以增加对每个元素的操作
			this.swipe(direction, 500);
			this.wait(1000);
			//滑动后定位元素并将元素的text添加到集合strSrc里
			List<AndroidElement> elementNew=super.findElements(by);
			for(AndroidElement ae:elementNew){
				strDes.add(ae.getText());
			}
			flag=this.listStrEquals(strSrc,strDes);
		}
	}
	/**
	 * 设备返回键操作
	 */
	public void pressBack(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.BACK);
	}
	/**
	 * 多次返回
	 * @param number
	 */
	public void pressBack(int number){
		if(number>0){
			for(int i=0;i<number;i++){
				this.pressBack();
				System.out.println("执行第"+String.valueOf(i+1)+"次返回");
			}
		}else{
			System.out.println("参数有误");
		}
	}
	/**
	 * 设备home键操作
	 */
	public void pressHome(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.HOME);
	}
	/**
	 * 设备回车键操作
	 */
	public void pressEnter(){
		this.wait(500);
		super.pressKeyCode(AndroidKeyCode.ENTER);
	}
	/**
	 * 手机键盘删除操作
	 */
	public void pressBackspace(){
		this.wait(200);
		super.pressKeyCode(AndroidKeyCode.BACKSPACE);
	}
	/**
	 * 多次手机键盘删除操作
	 */
	public void pressBackspace(int numbers){
		if(numbers>0){
			for(int i=0;i<numbers;i++){
				this.pressBackspace();
				System.out.println("执行第"+String.valueOf(i+1)+"次删除");
			}
		}else{
			System.out.println("参数有误");
		}
	}
	/**
	 * 唤醒屏幕//版本变化可以直接调用
	 */
	public void wakeUp() {
		try {
			if(super.isLocked()){
				super.unlockDevice();
				//AndroidSpecific.wakeUp(udid);
			}else{
				System.out.println("未锁屏不用唤醒");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 等待，死等
	 * @param milliSecond
	 */
	public void wait(int milliSecond){
		try {
			Thread.sleep(milliSecond);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//截图全屏
	public void takeScreen(String path,String fileName) throws Exception{
		File srcFile=super.getScreenshotAs(OutputType.FILE);
		System.out.println(path+fileName);
		FileUtils.copyFile(srcFile,new File(path+"/"+fileName));
	}
	//针对元素进行截图
	public void takeScreenForElement(AndroidElement element,String path,String fileName) throws Exception{
		 // 获得element的位置和大小
		 Point location = element.getLocation();//获取元素的起始点
		 Dimension size = element.getSize();//获取元素的大小
		 byte[] imageByte=super.getScreenshotAs(OutputType.BYTES);//获取整张图的截屏的大小字节
		 // 创建全屏截图
		 BufferedImage originalImage =ImageIO.read(new ByteArrayInputStream(imageByte));
		 // 截取element所在位置的子图。
		 BufferedImage croppedImage = originalImage.getSubimage(
		  location.getX(),
		  location.getY(),
		  size.getWidth(),
		  size.getHeight());
		 try {
			  ImageIO.write(croppedImage, "png", new File(path+fileName+".png"));
				//ImageIO.write(im, formatName, output)
		 }catch (IOException e) {
				// TODO Auto-generated catch block
			  e.printStackTrace();
		 }
	}
	//针对元素进行截图
	public void takeScreenForElement(By by,String path,String fileName) throws Exception{
		 // 获得element的位置和大小
		 AndroidElement element=(AndroidElement) super.findElement(by);
		 this.takeScreenForElement(element, path, fileName);
	}
	
	
}
