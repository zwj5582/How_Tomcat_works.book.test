/*************************************************************************
	> File Name: SimpleContainer.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Tue 15 Nov 2016 03:16:46 PM CST
 ************************************************************************/
package org.wenjie.zhong.servlet;
import java.net.*;
import java.text.*;
import java.io.*;
import javax.servlet.http.*;
public class SimpleContainer implements Container{
	private final static WEB_ROOT=System.getProperty("user.dir")+File.separartor+"web_root";
	public void invoke(HttpRequest request,HttpResponse response){
		String uri=request.getRequestURI();
		String filename=uri.substring(uri.lastIndexOf("/")+1);
		URLClassLoader loader=null;
		try{
			URL[] urls=new URL[1];
			File classPath=new File(WEB_ROOT);
			URLStreamHandler streamHandler=null;
			String name=new URL("file",null,classPath.getCanonicalPath()+File.separator).toString();
			urls[0]=new URL(null,name,streamHandler);
			loader=new URLClassLoader(urls);
		}catch(IOException e){
			e.printStackTrace();
		}
		Class clazz=null;
		try{
			clazz=loader.getLoaderClass();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		try{
			Servlet servlet(Servlet)clazz.newInstance();
			servlet.service(request,response);
		}catch(Throwable t){}
	}
	public String getInfo(){
		return null;
	}
	public Loader getLoader(){
		
	}
}

