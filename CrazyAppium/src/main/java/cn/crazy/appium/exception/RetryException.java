package cn.crazy.appium.exception;
/**
 * 重试异常
 * 
 */
public class RetryException extends Exception{
	
	public RetryException(String string){
		super(string);
	}
	public RetryException(String message, Throwable cause) {
        super(message, cause);
    }
    public RetryException(Throwable cause) {
        super(cause);
    }
}