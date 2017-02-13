/*************************************************************************
	> File Name: RequestUtil.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Sun 13 Nov 2016 09:12:36 PM CST
 ************************************************************************/
package org.zhong.wenjie.servlet;
public class RequestUtil{
	public static Cookie[] parseRequestUtil(String str){
		if(str==null||str.length<0){
			return null;
		}
		List<Cookie> list=new ArrayList<>();
		while(str.length()>0){
			int index=str.indexOf(";");
			if(index<0){
				index=str.length();
			}else if(index==0){
				break;
			}
			String KeyAndValue=str.substring(0,index);
			if(index==str.length())
				str=str.substring(index+1);
			else
				str="";
			try{
				int pos=KeyAndValue.indexOf("=");
				if(pos>=0){
					String name=KeyAndValue.substring(0,pos).trim();
					String value=KeyAndValue.substring(pos+1).trim();
					list.add(new Cookie(name,value));
				}
			}catch(Throwable e){}
		}
		return list.toArray(new Cookie[list.size()]);
	}
}

