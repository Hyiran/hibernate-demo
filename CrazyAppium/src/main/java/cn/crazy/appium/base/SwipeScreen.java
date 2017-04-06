package cn.crazy.appium.base;

import org.openqa.selenium.By;

public class SwipeScreen {
	private AndroidDriverBase driver;
	private int appWidth;
	private int appHeight;
	/**
	 * 构造方法，对三个变量进行初始化
	 * @param driver
	 */
	public SwipeScreen(AndroidDriverBase driver){
		this.driver=driver;
		this.appWidth=driver.appScreenWidth();
		this.appHeight=driver.appScreenHeight();
	}
	/**
	 * 向左滑动
	 * @param duration 滑动时间，单位是毫秒
	 */
	private void swipeToLeft(int duration){
		int startx=appWidth*9/10;
		int endx=appWidth*1/10;
		int y=appHeight*1/2;
		try {
			driver.swipe(startx,y,endx,y,duration);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 向右滑动
	 * @param duration 滑动时间，单位毫秒
	 */
	private void swipeToRight(int duration){
		int startx=appWidth*1/10;
		int endx=appWidth*9/10;
		int y=appHeight*1/2;
		try {
			driver.swipe(startx,y,endx,y,duration);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 向上滑动
	 * @param duration 持续时间，单位毫秒
	 */
	private void swipeToUp(int duration){
		int starty=appHeight*9/10;
		int endy=appHeight*1/10;
		int x=appWidth*1/2;
		try {
			driver.swipe(x,starty,x,endy,duration);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 向下滑动
	 * @param duration 持续时间，单位毫秒
	 */
	private void swipeToDown(int duration){
		int starty=appHeight*1/10;
		int endy=appHeight*9/10;
		int x=appWidth*1/2;
		try {
			driver.swipe(x,starty,x,endy,duration);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 滑动方法，通过参数实现各方向滑动
	 * @param direction 方向参数，值为"up"、"down"、"left"、"right"
	 * @param duration 滑动时间，单位毫秒
	 */
	public void swipe(String direction,int duration){
		String direc=direction.toLowerCase();
		switch(direc){
		case "up":
			this.swipeToUp(duration);
			break;
		case "down":
		    this.swipeToDown(duration);
		    break;
		case "left":
			this.swipeToLeft(duration);
			break;
		case "right":
			this.swipeToRight(duration);
			break;
		default:
			System.out.println("方向参数错误");
			break;
		}
	}

	
}
