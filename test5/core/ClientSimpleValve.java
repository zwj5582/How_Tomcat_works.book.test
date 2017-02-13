/*************************************************************************
	> File Name: ClientSimpleValve.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Wed 16 Nov 2016 11:00:02 AM CST
 ************************************************************************/
package org.zwj.servlet.core;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.catalina.*;
public class ClientSimpleValve implements Valve,Contained{
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
		System.out.println("-----------------");
		System.out.println("ip address:");
		ServletRequest sq=request.getRequest();
		System.out.println(sq.getRemoteAddr());
		System.out.println("-----------------");
	}
}

