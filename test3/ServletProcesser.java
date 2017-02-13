/*************************************************************************
	> File Name: ServletProcesser.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Sun 13 Nov 2016 11:48:18 AM CST
 ************************************************************************/
package org.zhong.wenjie.servlet;
import org.zhong.wenjie.servlet.http.*;
import java.net.*;
import java.io.*;
public class ServletProcesser{
	public void process(HttpRequest request,HttpResponse response){

		String url=request.getParseRequest();
		String filename=url.substring(url.lastIndexOF("/")+1);
		URLClassloader loader=null;
		try{
			URL[] urls=new URL[0];
			URLStreamHandler streamHanadler=null;
			File classPath=new File(Cantants.WEB_ROOT);
			String context=(new URL("file",null,classPath.getCannonicalPath()+File.separator)).toString();
			urls[0]=new URL(null,context,streamHandler);
			loader=new URLClassLoader(urls);
		}catch(IOException ex){
			ex.printStackTrace();
		}
		Class clazz=null;
		try{
			clazz=loader.loadClass(filename);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		Servlet servlet=null;
		try{
			servlet=(Servlet)clazz.newInstance();
			HttpRequestFacade requestF=new HttpRequestFacade(request);
			HttpResponseFacade responseF=new HttpResponseFacade(response);
			servlet.service(requestF,responseF);
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}

