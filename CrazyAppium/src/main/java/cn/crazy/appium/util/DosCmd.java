package cn.crazy.appium.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.crazy.appium.server.Port;

/**
 * @author 狂沙 qq289303905
 *
 */
/**
 * 此类完成dos或shell命令的执行封装
 *
 */
public class DosCmd {
	private Log logger=Log.getLogger(DosCmd.class);
	String osName=System.getProperty("os.name");//获取当前的环境
	
	/**
	 * execute dos command
	 * @param dos command,String
	 * @return boolean.succeed is true,Failure is false
	 * 
	 */
	public boolean execCmd(String cmdString){
		Runtime p = Runtime.getRuntime();//获取当前执行环境是windows还是mac
		
		try {
			if(osName.toLowerCase().contains("mac")){
				String[] command={"/bin/sh","-c",cmdString};
				Process process=p.exec(command);
			}else if(osName.toLowerCase().contains("win")){
				Process process=p.exec("cmd /c "+cmdString);
			}
			//Thread.sleep(10000);
			//appium -p 4490 -bp 2553 -U 127.0.0.62001
			//当执行启动appium命令的时候，判断端口是否启动成功，直到appium服务启动成功
			int portNum=RandomUtil.getInt(cmdString, 0);//获取到appium -p 4490 -bp 2553 -U 127.0.0.62001 命令的第一组数字，
			System.out.println(portNum);
			if(portNum>0){//接上，获取到后，判断端口号是否被占用
				Port port=new Port(new DosCmd());
				while(!port.isPortUsed(portNum)){//如果没有被占用，那么就等待，知道端口被占用，服务起起来了
					Thread.sleep(1000);
				}
			}
			System.out.println("dos命令执行完成");
			logger.debug("execute  command "+cmdString+" Succeed");
			return true;//最好执行完后返回一个true
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			logger.error("execute  command "+cmdString+" Failure", e);
			return false;
		}
	}
	/**
	 * get result of command, after execute dos command 
	 * @param dos command,String
	 * @return List<String>
	 */
	public List<String> execCmdConsole(String cmdString) throws InterruptedException {
		List<String> dosRes = new ArrayList<String>();
		try {
			Process process=null;
			System.out.println(cmdString);
			if(osName.toLowerCase().contains("mac")){
				String[] command={"/bin/sh","-c",cmdString};
				process = Runtime.getRuntime().exec(command);
			}else if(osName.toLowerCase().contains("win")){
				process = Runtime.getRuntime().exec("cmd /c "+cmdString);
			}
			//31:37
			InputStream in = process.getInputStream();
			BufferedReader inr = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = inr.readLine()) != null) {
				dosRes.add(line);
			}
			process.waitFor();
			process.destroy();
			logger.debug("get result of command after execute dos command "+cmdString+" Succeed ");
		} catch (IOException e) {
			logger.error("get result of command after execute dos command "+cmdString+" Failure", e);
		}
		return dosRes;
	}
	/**
	 * kill server with appium servers
	 * 干掉Appium服务
	 * @return boolean
	 */
	public boolean killServer(){
		String command="taskkill -F -PID node.exe";
		if(osName.toLowerCase().contains("mac")){
			command="killall node";
		}else if(osName.toLowerCase().contains("win")){
			command="taskkill -F -PID node.exe";
		}else{
			command="taskkill -F -PID node.exe";
		}
		if(execCmd(command)){
			logger.debug("kill server node  Succeed");
			return true;
		}else{
			logger.error("kill server node Failure");
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(System.getProperty("os.name"));
		DosCmd dc=new DosCmd();
		//dc.execCmd("appium -p 4490 -bp 2551 -U 760ABM95N8S3");
		//dc.execCmd("taskkill -f - pid node.exe");//杀掉进程
					/*		Windows 10
							4490
							netstat -ano|findstr 4490
							0
							netstat -ano|findstr 4490
							0
							netstat -ano|findstr 4490
							0
							netstat -ano|findstr 4490
							0
							netstat -ano|findstr 4490
							1
							dos命令执行完成
		
		          */
		
       System.out.println(dc.osName);
		List<String> devicesList=dc.execCmdConsole("adb devices");
		System.out.println(devicesList.size());
		for(String s:devicesList){
			System.out.println(s);
		}
		
	}
}
