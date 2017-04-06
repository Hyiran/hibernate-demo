package cn.crazy.appium.base;

import java.util.List;

import cn.crazy.appium.util.DosCmd;

public class AndroidSpecific {
	//public static String udid;
	/**
	 * 获取设备默认输入法
	 * @return
	 */
	public static  String getDefaultInput(String udid){
		DosCmd dc=new DosCmd();
		String input="";
		try {
			//List<String> res=dc.execCmdConsole("adb -s 192.168.56.101:5555 shell settings get secure default_input_method");
			List<String> res=dc.execCmdConsole("adb -s "+udid+" shell settings get secure default_input_method");
			//System.out.println("adb -s "+udid+" shell settings get secure default_input_method");
			System.out.println(res.size());
			input=res.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}
	/**
	 * 唤醒屏幕
	 * @throws Exception
	 */
	public static void wakeUp(String udid) throws Exception{
		DosCmd dc=new DosCmd();
		try {
			dc.execCmdConsole("adb -s "+udid+" shell am start -n io.appium.unlock/io.appium.unlock.Unlock");
			System.out.println("adb -s "+udid+" shell am start -n io.appium.unlock/io.appium.unlock.Unlock");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception {
		AndroidSpecific as=new AndroidSpecific();
		//System.out.println(as.getDefaultInput("127.0.0.1:62001"));
		System.out.println(as.getDefaultInput("760ABM95N8S3"));

		//wakeUp("192.168.56.101:5555");
	}
}
