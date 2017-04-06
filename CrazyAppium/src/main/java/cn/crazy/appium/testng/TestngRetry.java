package cn.crazy.appium.testng;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;
/**
 * 重试监听
 * @author qinghua
 *
 */
public class TestngRetry implements IRetryAnalyzer {
	//private static Logger logger = Logger.getLogger(TestngRetry.class);
	private int retryCount = 1;
	private  int maxRetryCount=3;//表示重试监听的最大次数为3次
	@Override
	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryCount) {
			String message = "running retry for  '" + result.getName() + "' on class " + this.getClass().getName() + " Retrying "
					+ retryCount + " times";
			//logger.info(message);
			Reporter.setCurrentTestResult(result);
			Reporter.log("RunCount=" + (retryCount + 1));
			retryCount++;
			return true;
		}
		return false;
	}

}