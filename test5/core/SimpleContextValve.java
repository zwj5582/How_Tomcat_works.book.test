/*************************************************************************
	> File Name: SimpleContextValve.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Wed 16 Nov 2016 09:40:35 PM CST
 ************************************************************************/
package org.zwj.servlet.core;
import javax.servlet.http.*;
import javax.servlet.*;
import org.catalina.*;
import java.io.*;
import java.net.*;
public class SimpleContextValve implements Contained,Valve{
	private Container container;
	public void invoke(Request request,Response response)throws IOException,ServletException{
		if(!(request instanceof HttpServletRequest)||!(response instanceof HttpServletResponse))
			return;
		HttpServletRequest hsr=(HttpServletRequest)request.getRequest();
		String contextPath=hsr.getContextPath();
		String requestURI=((HttpServletRequest)request).getDecodeURI();
		String rURI=requestURI.substring(context.length()).toUpperCase();
		Container container=getContainer();
	}
}

