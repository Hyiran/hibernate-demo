package cn.crazy.appium.testng;

import org.testng.Assert;
import org.testng.Reporter;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;


public class Assertion  {
	  private  AndroidDriverBase driver;//定义一个driver，因为我们的截图是要用到driver的
	  public Assertion(AndroidDriverBase driver){//利用构造方法初始化driver
		  this.driver=driver;
	  }
	  /**
	   * 
	   * @param actual  实际值
	   * @param expected 期望值
	   * @param fileName 截图后命名的文件名
	   */
	  public  void assertEquals(Object actual, Object expected,String fileName){
	        try{
	            Assert.assertEquals(actual, expected);//调用testng的断言
	        }catch(AssertionError e){//注意断言的异常不是一个exception，AssertionError 代表断言的错误
	        	fail(fileName);
	        }
	  }
	  /**
	   * 
	   * @param actual
	   * @param expected
	   * @param fileName
	   * @param message
	   */
	  public  void assertEquals(Object actual, Object expected, String fileName,String message){
	        try{
	            Assert.assertEquals(actual, expected, message);
	        }catch(AssertionError e){
	        	fail(fileName,message);
	        }
	  }
	  /**
	   * 
	   * @param actual 实际值
	   * @param expected 期望值
	   * @param fileName 文件名
	   */
	  public  void verifyEquals(Object actual, Object expected,String fileName){
	        try{
	            Assert.assertEquals(actual, expected);
	        }catch(AssertionError e){
	        	try {
	        		driver.takeScreen(CrazyPath.path, "\\images\\"+Thread.currentThread().getId()+fileName);//只做了截图的操作，没有调用fail方法
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        }
	  }
	  public  void verifyEquals(Object actual, Object expected,String fileName,String message){
	        try{
	            Assert.assertEquals(actual, expected, message);
	        }catch(AssertionError e){
	           	try {
	        		driver.takeScreen(CrazyPath.path, "\\images\\"+Thread.currentThread().getId()+fileName);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	        }
	  }
	  public void fail(String fileName){
		  try {
      			//System.out.println(CrazyPath.path+"\\images\\"+Thread.currentThread().getId()+fileName);
      			//Reporter.log("<a href=http://localhost:8080/jenkins/job/test/crazyappium/images/" + Thread.currentThread().getId()+fileName + " target=_blank>Failed Screen Shot</a>", true);  
      			//Reporter.log("<img src=http://localhost:8080/jenkins/job/test/crazyappium/images/"+Thread.currentThread().getId()+fileName +" style=width:30px;height:30px img/>", true);
				driver.takeScreen(CrazyPath.path+"\\test-output\\html\\images\\",Thread.currentThread().getId()+fileName);//调用截图方法，将图片存在这个路径下
		  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
		  Assert.fail();//截完图之后，再将当前用例至为失败
	  }
	  public void fail(String fileName,String message){//截图并加上消息
		  try {
      			//System.out.println(CrazyPath.path+"\\images\\"+Thread.currentThread().getId()+fileName);
			  //存储图片的地址改成你自己的
      			//Reporter.log("<a href=http://localhost:8080/jenkins/job/test/crazyappium/images/" + Thread.currentThread().getId()+fileName + " target=_blank>Failed Screen Shot</a>", true);  
    			//Reporter.log("<img src=http://localhost:8080/jenkins/job/test/crazyappium/images/"+Thread.currentThread().getId()+fileName +" style=width:30px;height:30px img/>", true);
			
			  driver.takeScreen(CrazyPath.path+"\\test-output\\html\\images\\",Thread.currentThread().getId()+fileName);
		  } catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		  }
		  Assert.fail(message);
	  }
}
