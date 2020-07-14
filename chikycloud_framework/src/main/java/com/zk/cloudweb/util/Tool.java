package com.zk.cloudweb.util;

import java.text.SimpleDateFormat;
import java.util.*;

public class Tool {

	/*创建唯一机内码*/
	public static String CreateID()
	{
		String id= UUID.randomUUID().toString().replaceAll("-", "");
		return id.length()>40?id.substring(0,39):id;
	}
    /*获取时间的唯一字符串编码*/
	public static String CreateTimeID()
	{
		Random random = new Random();
		SimpleDateFormat sd = new SimpleDateFormat("hhmmss");
		return sd.format(new Date())+random.nextInt(100);

	}
	/*字符串数组转list*/
	public static List<String> StringArrayToList(String[] source)
	{
		if (source==null || source.length==0) return null;
		List<String> lTemp=new ArrayList<String>();
		for (String s:source) {
			lTemp.add(s);
		}
		return lTemp;
	}
}