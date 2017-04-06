package cn.crazy.appium.server;

import java.util.ArrayList;
import java.util.List;

import cn.crazy.appium.util.DosCmd;
import cn.crazy.appium.util.XmlUtil;

public class Servers {
	
	
	public static void main(String[] args) throws Exception {
		DosCmd dc=new DosCmd();
//		List<String> deviceList=dc.execCmdConsole("adb devices");
//		for(int i=1;i<deviceList.size()-1;i++){
//			String[] deviceInfo=deviceList.get(i).split("\t");
//			//System.out.println(deviceList.get(i));
//			if(deviceInfo[1].equals("device")){
//				System.out.println(deviceInfo[0]);
//			}
//		}
		Servers server=new Servers(new Port(new DosCmd()), new DosCmd());
		//测试获取udid
//		List<String> devices = server.getDevices();
//		for(String s:devices){
//			System.out.println(s);
//		}
		
		
		//server.startServers();
//		List<String> serverCommands=server.GeneratServerCommand();//测试命令集合
//		for(String s:serverCommands){//遍历组合的命令
//			System.out.println(s);
//		}
		if(dc.killServer()){
			server.startServers();
		}
		//server.startServers();
		
	}
	private List<Integer> appiumPortList;
	private List<Integer> bootstrapPortList;
	private List<String> deviceList;
	private Port port;
	private DosCmd dos;
	private String path=System.getProperty("user.dir");
	public Servers(Port port,DosCmd dos){
		this.port=port;
		this.dos=dos;
	}
	/**
	 * 根据设备数量生成可用端口列表
	 * @param start 端口起始号
	 * @return 返回值是一个List<Integer>
	 * @throws Exception
	 */
	//
	private List<Integer> getPortList(int start) throws Exception{
		List<String> deviceList=getDevices();
		List<Integer> portList=port.GeneratPortList(start, deviceList.size());
		return portList;	
	}
	/**
	 * 获取当前可用设备{"xxx1","xx2","xx3"}
	 * @return
	 * @throws Exception
	 */
	public  List<String> getDevices() throws Exception {
		List<String> devList = dos.execCmdConsole("adb devices");
		List<String> deviceRes = new ArrayList<String>();
		if (devList.size() > 2) {//大于2表示表示有设备
			for(int i = 1; i < devList.size() - 1; i++) {
				String deviceInfo[] = devList.get(i).split("\t");
				if (deviceInfo[1].trim().equals("device")) {
					deviceRes.add(deviceInfo[0].trim());
				}
			}
		} else {
			System.out.println("当前没有设备或设备连接状态不正确");
		}
		return deviceRes;
	}
	/**
	 * 生成服务端启动命令字符串存入List
	 * @return
	 * @throws Exception
	 */
	public List<String> GeneratServerCommand() throws Exception{//把我要启动的命令存在一个String 集合里面
		appiumPortList=getPortList(4490);//指的是appium -p [端口号]
		bootstrapPortList=getPortList(2233);//指的是appium -p [] -bp [设备与服务端通讯的端口号]
		deviceList=getDevices();//指的是appium -p [] -bp [设备与服务端通讯的端口号] -U[设备udid]
		List<String> commandList=new ArrayList<String>();//定义当前命令生成的list
		for(int i=0;i<deviceList.size();i++){//以deviceList作为主要循环的终止数
			//下面按照字符串的形式把它拼接起来 appium -p 4490 -bp 2553 -U 760ABM95N8S3
			String command="appium -p "+appiumPortList.get(i)+" -bp "+bootstrapPortList.get(i)
					+" -U "+deviceList.get(i)+">"+path+"/logs/"+deviceList.get(i).split(":")[0]+i+".log";
																                                     //127.0.0.1:62001
			//System.out.println(command);//打印生成的命令
			commandList.add(command);//命令加到这个集合里面来
		}
		XmlUtil.createDeviceXml(deviceList,appiumPortList);//生成deviceList 和 appiumPortList xml文件 问 生成的device.xml文件有什么用，这个有需要的时候可以用上，获取设备与端口的对应关系
		return commandList;//将这个集合返回出去
	}
	/**
	 * 启动所有appium服务端
	 * @return
	 * @throws Exception
	 */
	public boolean startServers() throws Exception{
		List<String> startCommand=GeneratServerCommand();//调用上面生成的命令
		boolean flag=false;//将falg 置为flase相当于一个开关
		
		if(startCommand.size()>0){//先遍历一下命令的个数是否是有，有 我遍历它
			for(String s:startCommand){
				dos.execCmd(s);//然后按个执行它
			}
			flag=true;
		}else{
			flag=false;	
		}
		return flag;
	}

	
}
