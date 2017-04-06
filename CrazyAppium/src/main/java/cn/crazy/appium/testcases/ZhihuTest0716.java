package cn.crazy.appium.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.base.CrazyPath;
import cn.crazy.appium.datadriver.ExcelUtil;
import cn.crazy.appium.page.Drawals;
import cn.crazy.appium.page.HomePage;
import cn.crazy.appium.page.Login0716;
import cn.crazy.appium.testng.Assertion;
import cn.crazy.appium.testng.TestngListener;
import cn.crazy.appium.testng.TestngRetry;
import cn.crazy.appium.util.GetByLocator;
import cn.crazy.appium.util.ProUtil;
import cn.crazy.appium.util.SendMail;

public class ZhihuTest0716 extends CaseBaseTest{
		AndroidDriverBase driver;
		Login0716 loginPage;
		HomePage homePage;
		Drawals drawals;
		Assertion as ;
		
			@Parameters({"udid","port"})
			@BeforeClass
			public void beforeClass (String udid,String port) throws Exception{
				try {
					driver = driverInit(udid, port);
					driver.implicitlyWait(10);
					loginPage=new Login0716(driver);
					homePage = new HomePage(driver);
		            as = new Assertion(driver);
		            drawals = new Drawals(driver);
		            
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					SendMail sm = new SendMail();
					ProUtil p = new ProUtil(CrazyPath.globalPath);
					String [] to = p.getPro("tomail").split(",");
					sm.send("driver初始化失败", udid+"   driver初始化失败", to);
				}
				System.out.println("driver is： "+driver);
		}
	
		//登录测试，读取登录数据
			@DataProvider
			public Object[][] loginData() throws IOException{
				Object [][] data =ExcelUtil.getTestData("configs/test.xlsx", "sheet1");
				System.out.println("从Excel中读取内容的长度是："+data.length+" 个数据长度");//0数据长度,读到没有数据，login不执行
				return data;
			}
			//登录测试，读取登录数据
			@DataProvider
			public Object[][] regData() throws IOException{
				Object [][] data =ExcelUtil.getTestData("configs/test.xlsx", "sheet2");
				System.out.println("从Excel中读取内容的长度是："+data.length+" 个数据长度");//0数据长度,读到没有数据，login不执行
				return data;
			}
			
			
			
			
			
	
	/**
	 * @parem line  行号
	 * @param   caseName 用例名称
	 * @param username 用户名
	 * @param password  密码
	 * @param assertValue  断言的值
	 * @param fileNme  失败后的截图
	 */
		//	0数据长度,读到没有数据，login不执行
	    //@Test(dataProvider="loginData")
		public void login( String line,String caseName,String username,String password,String assertValue,String fileName) throws Exception{
			loginPage.login(username, password);
			ExcelUtil eu = new ExcelUtil("config/test.xlsx", "sheet1");
			try {
				Assert.assertEquals(driver.getPageSouce().contains(assertValue), true);
				eu.setCellData(Integer.valueOf(line), eu.getLastColumnNum(), "测试通过");
			}catch(AssertionError e){
				as.fail(fileName, caseName+"执行失败");
				eu.setCellData(Integer.valueOf(line), eu.getLastColumnNum(), "测试失败");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		@Test
//		public void reg(){
//			//Login0716 l = new Login0716(driver);
//			loginPage.reg("18817450650", "12345678", "清华");
//			//Assert.assertEquals(driver.getPageSouce().contains("搜索问题"), true);
//			as.assertEquals(driver.getPageSouce().contains("搜索问题"), true, "regerror.png");
//		}
		
	    //@Test(dataProvider="loginData")
	  		public void reg( String line,String caseName,String username,String password,String assertValue,String fileName) throws Exception{
	  			loginPage.login(username, password);
	  			ExcelUtil eu = new ExcelUtil("config/test.xlsx", "sheet2");
	  			try {
	  				Assert.assertEquals(driver.getPageSouce().contains(assertValue), true);
	  				eu.setCellData(Integer.valueOf(line), eu.getLastColumnNum(), "测试通过");
	  			}catch(AssertionError e){
	  				as.fail(fileName, caseName+"执行失败");
	  				eu.setCellData(Integer.valueOf(line), eu.getLastColumnNum(), "测试失败");
	  			} catch (Exception e) {
	  				e.printStackTrace();
	  			}
	  		}
		
	    @Test
	    public void drawals() throws Exception {
	    	drawals.withDrawals();
           
	    	//Assert.assertEquals(driver.getPageSouce().contains("出现"), true);
	    	try {
		    	System.out.println("来到成功断言前");
	    	  // 	as.assertEquals(driver.getPageSouce().contains("12223333"),true , "drawalsuccess.png");
	    	   	//Assert.assertEquals(driver.isElementExist(By.name("充值"), 10), true);//断言是否登录成功
	    	   	//as.assertEquals(driver.isElementExist(GetByLocator.getLocator("recharge")), true, "drawalsuccess.png");
	    	   	Assert.assertEquals(driver.isElementExist(GetByLocator.getLocator("recharge")), true, "测试通过");
	    	   	
	    	   	//System.out.println("来到成功断言后");
			} catch ( AssertionError e) {
			 	Assert.assertEquals(!driver.isElementExist(GetByLocator.getLocator("recharge")), false, "测试失败");
	    	   //	as.assertEquals(!driver.getPageSouce().contains("申请"),false , "drawalerror.png");
	    		System.out.println("来到失败断言");
			}
	 


	    }
		
		
		
		
		
		
		
		
		
		
		//遍历文章
		//@Test(dependsOnMethods="login" )
		public void article(){
			homePage.article();
			//需要补充断言
		}
		
		//搜索问题或人
		//@Test(dependsOnMethods="login" )
		public void addAnswer(){
			homePage.addAnswer();
			//需要补充断言
		}
		
		
	//	@Test//(dependsOnMethods="login" )//依赖于登录方法
		public void logout(){
			//退出方法
			//driver.findElement(By.name("允许")).click();
		}
		//@AfterMethod
		public void afterMethod(){
			//driver.startActivity("", "");//登录后的操作用startActivity
		
		}
		
		//注意不建议登录和注册都放在一个测试类里面来做
		
		
		
	@AfterClass
	//@AfterClass(alwaysRun = true)
	public void afterClass(){
		//driver.resetApp();//先登录后执行，就用这个方法把应用重置，把应用重置到一个未登录的状态
		driver.quit();
	}
	
	
	
}
