package cn.crazy.appium.network.study;

import java.util.ArrayList;
import java.util.List;

public class ListEquas {

	public static void main(String[] args) {
		/**
		 * 对比文本的方法
		 */
		// TODO Auto-generated method stub
		List<String> aList=new ArrayList<String>();
		aList.add("aaa");
		aList.add("bbb");
		aList.add("ccc");
		aList.add("ddd");
		List<String> bList=new ArrayList<String>();
		bList.add("aaa");
		bList.add("bbb");
		bList.add("ccc");
		bList.add("ddd");
		boolean flag=false;
		if(aList.size()==bList.size()){
			for(int i=0;i<aList.size();i++){
				if(aList.get(i).equals(bList.get(i))){
					flag=true;
				}else{
					flag=false;
					break;
				}
			}
		}
		System.out.println(flag);


	}

}
