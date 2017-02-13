/*************************************************************************
	> File Name: SimpleWrapperValve.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Wed 16 Nov 2016 10:33:37 AM CST
 ************************************************************************/
package org.zwj.servlet.core;
import org.apache.catalina.*;
import org.apache.catalina.Request;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import java.io.*;
public class SimpleWrapperValve implements Contained,Valve{
	protected Container container=null;
	public void setContainer(Container container){
		this.container=container;
	}
	public Container getContainer(){
		return this.container;
	}
	public String getInfo(){
		return null;
	}
	public void invoke(Request request,Response response,ValveContext context)throws IOException,ServletException{
		Wrapper wrapper=getContainer();
		ServletRequest sq=request.getRequest();
		ServletResponse ss=response.getResponse();
		HttpServletRequest hsq=null;
		if(sq instanceof HttpServletRequest)
			hsq=(HttpServletRequest)sq;
		HttpServletResponse hss=null;
		if(ss instanceof HttpServletResponse)
			hss=(HttpServletResponse)ss;
		try{
			Servlet servlet=wrapper.allocate();
			if(hsq!=null&&hss!=null){
				servlet.service(hsq,hss);
			}else
				servlet.service(sq,ss);
		}catch(ServletException e){}
	}
}

