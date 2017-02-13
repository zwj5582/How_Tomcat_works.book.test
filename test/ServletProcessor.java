/*************************************************************************
	> File Name: ServletProcessor.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Fri 11 Nov 2016 02:40:22 PM CST
 ************************************************************************/
package org.zhong.net.servlet;
import java.net.*;
import java.io.*;
public class ServletProcessor{
	private Request request;
	private Response response;
	public ServletProcessor(Request request,Response response){
		this.request=request;
		this.response=response;
	}
	public void process(){
		URLClassLoader loader=null;
		String url=request.getURL();
		String filename=url.substring(url.lastIndexOf("/")+1);
		try{
			URL[] urls=new URL[1];
			URLStreamHandler streamHandler = null;
			File classpath=new File(HttpServer1.WEB_ROOT);
			String repository =(new URL("file", null, classpath.getCanonicalPath() +File.separator)).toString();
			System.out.println(repository);
			urls[0]=new URL(null,repository,streamHandler);
			loader=new URLClassLoader(urls);
		}catch(IOException ee){
			ee.printStackTrace();
		}
		Class clazz=null;
		System.out.println(url);
		try{
			clazz=loader.loadClass(filename);
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}
		Servlet servlet=null;
		try{
			servlet=(Servlet)clazz.newInstance();
			servlet.service((ServletRequest)request,(ServletResponse)response);
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}

