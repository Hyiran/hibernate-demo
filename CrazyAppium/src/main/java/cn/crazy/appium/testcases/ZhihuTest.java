package cn.crazy.appium.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import cn.crazy.appium.base.AndroidDriverBase;
import cn.crazy.appium.datadriver.ExcelUtil;
import cn.crazy.appium.page.HomePage;
import cn.crazy.appium.page.LoginPage1;
import cn.crazy.appium.testng.Assertion;

public class ZhihuTest extends CaseBaseTest {
	public AndroidDriverBase driver;
	public LoginPage1 loginTest;
	public Assertion as;
	@BeforeClass
	@Parameters({ "udid", "port" })
	public void beforeClass(String udid, String port) {
		try {
			System.out.println("读到的udid是："+udid+"读到的port是："+port);
			driver=driverInit(udid, port);
			driver.implicitlyWait(10);
			loginTest=new LoginPage1(driver);
			as=new Assertion(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("执行测试testtest");
	}
	@DataProvider
	public Object[][] loginData(){
		//Object[][] data={{"crazysand_001@163.com","123456","登录"},{"crazysand_001@163.com","12345678","首页"}};
		Object[][] data=null;
		try {
			data = ExcelUtil.getTestData("configs/test.xlsx", "Sheet1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	//{{"2","登录用户名错误","crazysand_009@163.com","123456","登录","usererror.png","y"}，{}}
	@Test(dataProvider="loginData")
	public void login(String caseNumber,String caseName,String username,String password,String assertStr,String erroFile) throws Exception{
		//LoginPage1 loginTest=new LoginPage1(driver);
		HomePage hp=loginTest.login(username,password);
		//hp.clickIngroe();
		//Assertion as=new Assertion(driver);
		ExcelUtil excel=new ExcelUtil("configs/test.xlsx", "Sheet1");
		try {
			as.assertEquals(hp.pageSource.contains(assertStr), true,erroFile);
			excel.setCellData(Integer.valueOf(caseNumber),excel.getLastColumnNum(), "测试用例执行成功");
		} catch (AssertionError e) {
			// TODO: handle exception
			excel.setCellData(Integer.valueOf(caseNumber),excel.getLastColumnNum(), "测试用例执行失败");
			Assert.fail("login failure");
		}
		
	}
	@Test
	public void reg(){
		//LoginPage1 loginTest=new LoginPage1(driver);
		HomePage hp=loginTest.reg("crazysand_234@163.com", "12345678", "xxxxxx");
		hp.clickIngroe();
		//Assertion as=new Assertion(driver);
		as.assertEquals(hp.pageSource.contains("首页"), true, "regerror.png");
	}
	@AfterMethod
	public void afterMethod(){
		driver.resetApp();
		//driver.startActivity(appPackage, appActivity);
	}
	@AfterClass
	public void quit(){
		driver.quit();
	}
}
