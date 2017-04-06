package cn.crazy.appium.base;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
/**
 * 设备参数接口
 * @author qinghua
 *
 */
public interface AndroidCapabilityType extends MobileCapabilityType {
	String NO_SIGN="noSign";//不重签名app
	String UNICODE_KEY_BOARD="unicodeKeyboard";//使用隐藏式键盘
	String RESET_KEY_BOARD="resetKeyboard";//重置系统输入法
	String AUTO_LAUNCH="autoLaunch";//是否设置为自动启动
}
