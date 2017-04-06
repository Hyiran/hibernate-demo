package cn.crazy.appium.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.crazy.appium.server.Port;
import cn.crazy.appium.server.Servers;


public class XmlUtil {
	/**
	 * 读取device.xml配置文件
	 * @param filePath   
	 * @return
	 * @throws DocumentException
	 */
	public static List<String> readXML(String filePath) throws DocumentException {
		//读device.xml文件
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(new File(filePath));
		Element root = document.getRootElement();//先 获取根元素
		//System.out.println(root.getName());

		// 获取特定名称的子元素
		@SuppressWarnings("unchecked")
		List<Element> deviceList = root.elements("deviceId");//获取deviceId 底下的内容，底下的内容都是我们要的
		// 利用迭代输出
		List<String> deviceData = new ArrayList<String>();
		for (Element e : deviceList) {
			for (Iterator iter = e.elementIterator(); iter.hasNext();) {//获取deviceId下的所有的子标签内容，把它们放在迭代器里面，看是不是还有下一个
				Element e1 = (Element) iter.next();//然后在循环里面每次都拿一个，把它转换为Element对象
				deviceData.add(e1.getText());//然后获取它们的每一个值，将它们的值存在deviceData这个集合里
			}
		}
		return deviceData;//最终将这个集合返回出去
		/**
		 * 读到的格式是什么？
		 * 760ABM95N8S3，4490，4f71329f，4491 有四个元素 规律每一个奇数项的索引就代表设备的udid，每一个偶数项代表对应的端口
		 */
	}
	/**
	 * 创建device.xml文件
	 * @param deviceList
	 * @param appiumPortList
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void createDeviceXml(List<String> deviceList,List<Integer> appiumPortList) throws IOException,//根据当前连接的端口，自动生成一个对应的device xml文件，
																																																	//这个文件在configs\device.xml
	InterruptedException {
		Document document = DocumentHelper.createDocument();//先创建一个Document对象用来创建xml文件
		Element root = DocumentHelper.createElement("Device");//先创建一个标签为Device
		document.setRootElement(root);//然后将它设置成根标签
		root.addAttribute("name", "appiumstartlist");//给根标签添加属性name 值为appiumstartlist
		if (deviceList.size() > 0) {//将传进来的集合，判断一下是否大于0，如果大于0，说明当前有设备连接
			for (int j = 0; j < deviceList.size(); j++) {//然后遍历设备的集合
				Element deviceId = root.addElement("deviceId");//在根标签之下生成deviceId标签
				deviceId.addAttribute("id", String.valueOf(j));//deviceId有个属性id，把它设置进来
				Element deviceName = deviceId.addElement("deviceName");//在deviceId 底下有两个子标签deviceName(设备的udid)、appiumPort (设备的端口号)，将它们分别设置进去
				Element appiumPort = deviceId.addElement("appiumPort");
				deviceName.setText(deviceList.get(j));
				appiumPort.setText(String.valueOf(appiumPortList.get(j)));
			}
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2 = new XMLWriter(new FileOutputStream("configs/device.xml"),
				format);
		xmlWrite2.write(document);
	}
	/**
	 * 创建testng.xml配置文件
	 * 
	 * @param classname
	 * @throws Exception
	 */
	public static void createTestngXml(String classname) throws Exception {
		/*
		 * 创建一个testng的xml文件，它的参数是一个类名，类名其实就是你要执行那个类的测试用例的类
		 * (但这样要注意，这样生成的是都是执行相同的测试用例)
		 */
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());//创建一个servers对
		List<String> deviceList=servers.getDevices();//获取当前所有的连接设备，放在一个deviceList集合里面
		System.out.println("设备数量："+deviceList.size());
		Document document = DocumentHelper.createDocument();//创建一个 Document对象（xml）
		Element root = DocumentHelper.createElement("suite");//创建一个标签为suit
		document.setRootElement(root);//将suit标签设置为根标签
		root.addAttribute("name", "Suite");//将根标签添加一个属性名为name ，值为Suit
		root.addAttribute("parallel", "tests");//在根标签里添加一个属性名为parallel，值为tests
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));//在根标签里添加一个属性名为thread-count，值为设备的数量
		Element listeners=root.addElement("listeners"); //创建listeners标签
		Element listener1=listeners.addElement("listener");//创建listener1标签
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");//给listener1标签设置名class-name，值为org.uncommons.reportng.HTMLReporter
		
		Element listener2=listeners.addElement("listener");//然后将
		listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");
		List<String> s=readXML("configs/device.xml");
		//{192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for(int j=0;j<deviceList.size();j++){//遍历设备集合，有多少设备就有多少test
			Element test = root.addElement("test");//将test标签加到test标签下面
			test.addAttribute("name", deviceList.get(j));//test标签 加一个属性名为name 值为设备的udid值
			Element paramUuid=test.addElement("parameter"); //test标签下增加一个标签名为parameter
			paramUuid.addAttribute("name","udid");//parameter标签下增加一个属性名为name 值为udid
			paramUuid.addAttribute("value",s.get(2*j));//parameter标签下增加一个属性名为value 值为设备编号
			Element paramPort=test.addElement("parameter");//test标签下增加一个子标签为parameter
			paramPort.addAttribute("name", "port");//parameter标签，增加属性名为name，值为port
			paramPort.addAttribute("value",s.get(2*j+1));//parameter标签，增加属性名为value，值为设备编号
			Element classes = test.addElement("classes");//在test标签下增加子标签classes标签
			Element classNode=classes.addElement("class");//在classes下增加class标签
			classNode.addAttribute("name", classname);//classname为传进来的测试类
		}
		//下面就是写testng的xml文件 
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"),format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//封装多个相同的测试类的方法
	public static void createTestngXml(List<String> classesList) throws Exception {
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList=servers.getDevices();
		System.out.println("设备数量"+deviceList.size());
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners=root.addElement("listeners");
		Element listener1=listeners.addElement("listener");
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
		Element listener2=listeners.addElement("listener");
		listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");
		List<String> s=readXML("configs/device.xml");
		//{192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for(int j=0;j<deviceList.size();j++){
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			/*
			 *   <parameter name="udid" value="4f71329f"/>
			 */
			Element paramUuid=test.addElement("parameter");//第一个parameter标签装udid
			paramUuid.addAttribute("name","udid");
			paramUuid.addAttribute("value",s.get(2*j));
			/*
			 *  <parameter name="port" value="4490"/>
			 */
			Element paramPort=test.addElement("parameter");//第二个标签parameter装port
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value",s.get(2*j+1));
			/*
			 * 追加用户名
			 */
//			Element paramuser=test.addElement("parameter");
//			paramuser.addAttribute("name", "user");
//			paramuser.addAttribute("value", "tzqh");
//			/*
//			 * 追加密码
//			 */
//			Element parampwd=test.addElement("parameter");
//			parampwd.addAttribute("name", "pwd");
//			parampwd.addAttribute("value", "123456");
//			
			/*
			 *    <classes>
			 */
			Element classes = test.addElement("classes");
			for(String className:classesList){
				Element classNode=classes.addElement("class");
				classNode.addAttribute("name", className);
			}
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"),format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//封装不同的测试类给每个一个设备
	public static void createTestngSingleXml(List<String> classesList) throws Exception {
		Servers servers=new Servers(new Port(new DosCmd()), new DosCmd());
		List<String> deviceList=servers.getDevices();
		System.out.println("设备数量"+deviceList.size());
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("suite");
		document.setRootElement(root);
		root.addAttribute("name", "Suite");
		root.addAttribute("parallel", "tests");
		root.addAttribute("thread-count", String.valueOf(deviceList.size()));
		Element listeners=root.addElement("listeners");
		Element listener1=listeners.addElement("listener");
		listener1.addAttribute("class-name", "org.uncommons.reportng.HTMLReporter");
		Element listener2=listeners.addElement("listener");
		listener2.addAttribute("class-name", "org.uncommons.reportng.JUnitXMLReporter");
		List<String> s=readXML("configs/device.xml");
		//{192.168.56.101:5555,4490,192.168.56.102:5555,4491,xxx,4492,yyy,4493}
		for(int j=0;j<deviceList.size();j++){
			Element test = root.addElement("test");
			test.addAttribute("name", deviceList.get(j));
			Element paramUuid=test.addElement("parameter");
			paramUuid.addAttribute("name","udid");
			paramUuid.addAttribute("value",s.get(2*j));
			Element paramPort=test.addElement("parameter");
			paramPort.addAttribute("name", "port");
			paramPort.addAttribute("value",s.get(2*j+1));
			Element classes = test.addElement("classes");
			Element classNode=classes.addElement("class");
			classNode.addAttribute("name", classesList.get(j));//但是这个方法有个缺点就是 有多个设备就要传多个测试类进来 如 有5个设备就需要传5个测试类进来
		}
		OutputFormat format = new OutputFormat("    ", true);
		XMLWriter xmlWrite2;
		try {
			xmlWrite2 = new XMLWriter(new FileOutputStream("testng.xml"),format);
			xmlWrite2.write(document);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception { 
//		List<String>  deviceData=readXML("configs/device.xml");
//		for(String s:deviceData){
//			System.out.println(s);
//		}
//		List<String> testng = readXML("configs/device.xml");
//		for(String s:testng){
//			System.out.println(s);
//		}
// 		
		
		
		
		//读xml文件
//		SAXReader reader=new SAXReader();//读文件的时候，先创建一个SAXReader对象
//		Document doc=reader.read(new File("configs/student1.xml"));//然后创建一个Document对象，通过读我们的这个文件，将读到的内容存到Document对象里doc
//		//Document doc=reader.read(new File("testng.xml"));//然后创建一个Document对象，通过读我们的这个文件，将读到的内容存到Document对象里doc
//
//		Element root=doc.getRootElement();//先获取到根标签
//		List<Element> s=root.elements("student");//然后获取根标签下所有的student标签，获取到之后，存在一个集合里
//		for(Element e:s){//拿到以后我们遍历这个集合
//			System.out.println(e.getText());//打印这个标签下的每一个值
//		}
//		createTestngXml(classesList);
//createTestngXml("xx.x.xx.xx.xx.appium2");
		
		
		
		//createTestngXml("cn.crazy.appium.network.study.Study");
//	
//		createDeviceXml(new Servers(new Port(new DosCmd()),new DosCmd()).getDevices() ,new Port(new DosCmd()).GeneratPortList(4490, 2));
		//添加多个测试类到testng classes 标签里
		List<String> classesList=new ArrayList<String>();
		classesList.add("cn.crazy.appium.network.study.Study");
		classesList.add("cn.crazy.appium.network.study.Study1");
		createTestngXml(classesList);
		
		
//		//createTestngSingleXml(classesList); //不同的测试类
//		
		
		
		
		
//		//写一个xml文件
//		Document d=DocumentHelper.createDocument();//先创建一个Document对象d
//		Element root=DocumentHelper.createElement("teacher");//创建一个element对象标签，标签的名字叫teacher
//		d.setRootElement(root);//把我们生成的根标签设置成teacher 标签
//		root.addAttribute("name", "沙陌");//给标签加上一个属性叫name，值是沙陌
//		String[] studentArr={"艳","tracy-yan","你给我滚出去","流星","下个路口","神庙","心雪"};//定义一个String 数组将一组文字加进去
//		for(int i=0;i<studentArr.length;i++){//利用循环
//			Element student=root.addElement("student");//把子标签都命名为student
//			student.addAttribute("id", String.valueOf(i));//给student的标签加上一个属性id，值为循环赋值0123456
//			student.setText(studentArr[i]);//设置标签的值
//		}
//		//创建一个输出流的格式
//		OutputFormat format=new OutputFormat("    ", true);
//		//创建一个xmlWriter对象，将文件写在哪一个文件里头，用format格式
//		XMLWriter w=new XMLWriter(new FileOutputStream("configs/student1.xml"), format);
//		w.write(d);//最后说写
		
	}
}
