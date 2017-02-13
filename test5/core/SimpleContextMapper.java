/*************************************************************************
	> File Name: SimpleContextMapper.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Wed 16 Nov 2016 08:18:35 PM CST
 ************************************************************************/
package org.zwj.servlet.core;
import javax.servlet.*;
import javax.servlet.http.*;
import org.catalina.*;
import java.io.*;
import java.net.*;
public class SimpleContextMapper implements Mapper{
	private SimpleContext container;
	public Container getContainer(){
		return container;
	}
	public void setContainer(Conatainer container){
		if(!(container instanceof SimpleContext))
			throw new IllegalArgumentException();
		this.container=(SimpleContext)container;
	}
	public String getProtocol(){
		return null;
	}
	public void setProtocol(String protocol){
	}
	public Container map(Request request,boolean update){
		String contextPath=((HttpServletRequest)request).getContextPath();
		String requestURI=((HttpServletRequest)request).getDecodeURI();
		String url=requestURI.substring(context.length());
		SimpleContext conatainer=null;
		String name=this.container.findServletMapping(url);
		if(name=!null){
			container=this.container.findChild(name);
		}
		return container;
	}
}

