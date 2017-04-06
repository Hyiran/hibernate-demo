package cn.crazy.appium.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.util.ProUtil;

public class BasePage {
	//下面的三个信息每个类都具备的信息共有的
	public String curActivity;
	public String pageSource;
	public AndroidDriverBase driver;
	public BasePage(AndroidDriverBase driver){//初始化一个构造方法
		this.driver=driver;//注意初始化的顺序driver必须排在第一个，下面的两个可以自行定
		this.curActivity=getCurActivity();
		this.pageSource=getPageSource();
	}
	//获取当前activity
	public String getCurActivity(){
		return driver.currentActivity();
	}
	public String getPageSource(){
		return driver.getPageSouce();
	}
	//输入
	public void sendkeys(AndroidElement element,String value){
		if(element!=null){//判断一下如果不是null 我才做输入
			element.sendKeys(value);
		}else{
			System.out.println("元素没有定位到，是null");
		}
	}
	//直接定位并输入
	public void sendkeys(By by,String value){
		AndroidElement element=driver.findElement(by);
		sendkeys(element,value);
	}
	//点击
	public void click(AndroidElement element){
		if(element!=null){
			element.click();
		}else{
			System.out.println("元素没有定位到，是null");
		}
	}
	//定位并点击
	public void click(By by){
		AndroidElement element=driver.findElement(by);
		click(element);
	}
	//清除
	public void clear(AndroidElement element){
		if(element!=null){
			element.clear();
		}else{
			System.out.println("元素没有定位到，是null");
		}
	}
	//逐个清除，对于密码输入框是无效的
	public void clearOneByOne(AndroidElement element){
		if(element!=null){
			element.click();
			driver.pressKeyCode(AndroidKeyCode.KEYCODE_MOVE_END);
			String text=element.getText();
			driver.pressBackspace(text.length());
		}else{
			System.out.println("元素没有定位到，是null");
		}
	}
	//输入内容直到正确，格式要正确类似手机号格式一样
	public void sendkeysUntilCorrect(AndroidElement element,String str){
		if(element!=null){
			boolean flag=true;
			element.sendKeys(str);//先输入一次
			while(flag){//然后做while循环
				if(str.equals(element.getText())){//用我传进来的值跟我输入的的文本进行比对
					flag=false;//如果一样那么循环结束
				}else{
					element.sendKeys(str);//如果不一样继续输入
				}
			}
		}else{
			System.out.println("元素为null");
		}
	}
	//逐个输入数字，模拟的是键盘输入，15610112934
	public void sendKeysOneByOne(String text){
		char[] chr=text.toCharArray();//{1,5,6,1,x,x,x,x}   //把我们传进来的值拆分一下拆成一个char的数组转换一下 
		for(int i=0;i<chr.length;i++){//然后我遍历它
			int c=Integer.valueOf(String.valueOf(chr[i]));//用i做索引拿到每一项，把它转换成int变量
			int number=0;
			switch (c) {//然后判断这个值是几  如果是0，那么就点击0
			case 0:
				//driver.pressKeyCode(AndroidKeyCode.KEYCODE_0);
				number=AndroidKeyCode.KEYCODE_0;
				break;
			case 1:
				number=AndroidKeyCode.KEYCODE_1;
				break;
			case 2:
				number=AndroidKeyCode.KEYCODE_2;
				break;
			case 3:
				number=AndroidKeyCode.KEYCODE_3;
				break;
			case 4:
				number=AndroidKeyCode.KEYCODE_4;
				break;
			case 5:
				number=AndroidKeyCode.KEYCODE_5;
				break;
			case 6:
				number=AndroidKeyCode.KEYCODE_6;
				break;
			case 7:
				number=AndroidKeyCode.KEYCODE_7;
				break;
			case 8:
				number=AndroidKeyCode.KEYCODE_8;
				break;
			case 9:
				number=AndroidKeyCode.KEYCODE_9;
				break;
			default:
				System.out.println("数值不对");
				break;
			}
			driver.pressKeyCode(number);//每一次循环我都去点击一下
		}
	}
	//坐标元素点击，针对一些能定位到整体元素但具体元素无法定位并且具有规律性的元素,获取每一个子元素的中心点坐标
	//思路：获取整体元素，将整体元素分为多行多列元素，取每一个子元素的中心坐标进行点击
	public List<Point> getElementByCoordinates(AndroidElement element,int rows,int columns){
		//int[] coordinate=new int[2];//定义一个存放坐标的数组
		List<Point> elementResolve=new ArrayList<Point>();//定义一个存放所有点的Point对象是org.openqa.selenium.Point 的
		if(element!=null){
			int startx=element.getLocation().getX();//起始点坐标x
			int starty=element.getLocation().getY();//起始点坐标y
			int offsetx=element.getSize().getWidth();//该元素的宽
			int offsety=element.getSize().getHeight();//该元素的高
			for(int i=0;i<rows;i++){
				for(int j=0;j<columns;j++){
//					coordinate[0]=startx+(offsetx/2*columns)*(2*j+1);
//					coordinate[1]=starty+(offsety/(2*rows)*(2*i+1));
					int x =startx+(offsetx/2*columns)*(2*j+1);
					int y=starty+(offsety/(2*rows)*(2*i+1));
					Point p=new Point(x, y);
					elementResolve.add(p);//将生成的点加到这个集合里面去
				}
			}
		}
		return elementResolve;
	}
	//根据整体元素拆分后的规律子元素索引点击元素
	public void clickElementByCoordinate(AndroidElement element,int rows,int columns,int index){
		if(element!=null){
			List<Point> elementResolve=getElementByCoordinates(element,rows,columns);
			if(!elementResolve.isEmpty()&&elementResolve!=null){
				driver.clickByCoordinate(elementResolve.get(index).x,elementResolve.get(index).y);
			}else{
				System.out.println("坐标集合为空");
			}
		}else{
			System.out.println("元素为null");
		}
	}
	//输入安全键盘的密码, 128606
	public void sendkeysPwd(List<Point> pwd,int[] password){
		if(password.length>0){
			for(int i=0;i<password.length;i++){
				if(password[i]==0){
					driver.clickByCoordinate(pwd.get(10).x, pwd.get(10).y);
				}else{
					driver.clickByCoordinate(pwd.get(password[i]-1).x, pwd.get(password[i]-1).y);
				}
			}
		}
	}
	//输入安全键盘的密码, 128606
	public void sendkeysPwd(AndroidElement element,int rows,int columns,int[] password){
		if(element!=null){
			List<Point> pwd=getElementByCoordinates(element,rows,columns);
			if(password.length>0){
				for(int i=0;i<password.length;i++){
					if(password[i]==0){
						driver.clickByCoordinate(pwd.get(10).x, pwd.get(10).y);
					}else{
						driver.clickByCoordinate(pwd.get(password[i]-1).x, pwd.get(password[i]-1).y);
					}
				}
			}
		}

	}
	//九宫格手势解锁,参数indexs是密码数字组成的数组，参数indexs={1,2,5,6,8,9}
	public void wakeByGestures(AndroidElement element,int[] indexs){
		if(element!=null){
			List<Point> elementResolve=getElementByCoordinates(element,3,3);
			TouchAction ta=null;
			if(indexs.length>0){
				ta=new TouchAction(driver).press(elementResolve.get(indexs[0]-1).x, elementResolve.get(indexs[0]-1).y).waitAction(500);
				for(int i=1;i<indexs.length;i++){
					ta.moveTo(elementResolve.get(indexs[i]-1).x-elementResolve.get(indexs[i-1]-1).x, elementResolve.get(indexs[i]-1).y-elementResolve.get(indexs[i-1]-1).y).waitAction(500);
				}
				ta.release().perform();
			}else{
				System.out.println("密码参数有误");
			}
		
		}
	}
}
