/*************************************************************************
	> File Name: ClientLoggValve.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Wed 16 Nov 2016 11:13:31 AM CST
 ************************************************************************/
package org.zwj.servlet.core;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.catalina.*;
public class ClientLoggValve implements Valve,Contained{
	protected Container Container=null;
	public String getInfo(){
		return null;
	}
	public Container getContainer(){
		return container;
	}
	public void setContainer(Container container){
		this.container=container;
	}
	public void invoke(Request request,Response,response,ValveContext context)throws IOException,ServletException{
		context.invokeNext(request,response,context);
		System.out.println("--------------");
		System.out.println("head:");
		ServletRequest sq=request.getRequest();
		if(sq instanceof HttpServletRequest){
			HttpServletRequest hsq=(HttpServletRequest)sq;
			Enumation num=hsq.getHeaderNames();
			while(num.hasMoreElements()){
				String key=num.nextElement().toString();
				String value=num.getHeader(key);
				System.out.println(key+":"value);
			}
		}
		System.out.println("---------------");
	}
}

