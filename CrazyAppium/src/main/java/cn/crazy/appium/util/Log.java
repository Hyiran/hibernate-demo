package cn.crazy.appium.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author 狂沙 qq289303905
 *
 */
public class Log {
	private static Logger log = Logger.getLogger(Log.class);
	private static Log logger;
	private static Thread thread;
	public static Log getLogger(Class<?> T) {
		init();
		log = Logger.getLogger(T);
		logger = new Log();
		thread=Thread.currentThread();//获取当前线程
		return logger;//返回当前类对象
	}
	public static void init() {
		PropertyConfigurator.configure("configs/log4j.properties");//加载log4j配置文件
	}
	//日志的几个级别debug、info、warn、error

	public void debug(Object message) {//错误的信息的log
		log.debug(thread.getName()+message);
	}

	public void debug(Object message, Throwable t) {//错误消息和错误站的记录
		log.debug(thread.getName()+message, t);
	}

	public void info(Object message) {
		log.info(thread.getName()+message);
	}

	public void info(Object message, Throwable t) {
		log.info(thread.getName()+message, t);
	}

	public void warn(Object message) {
		log.warn(thread.getName()+message);
	}

	public void warn(Object message, Throwable t) {
		log.warn(thread.getName()+message, t);
	}

	public void error(Object message) {
		log.error(thread.getName()+message);
	}

	public void error(Object message, Throwable t) {
		log.error(thread.getName()+message, t);
	}
}