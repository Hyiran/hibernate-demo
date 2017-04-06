package cn.crazy.appium.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import cn.crazy.appium.testng.TestngRetry;

public class RetryTest {
	@Test(retryAnalyzer = TestngRetry.class)//失败重试
	public void test(){
		System.out.println("xxx");
		Assert.fail();
	}
}
