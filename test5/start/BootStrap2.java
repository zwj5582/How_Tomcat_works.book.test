/*************************************************************************
	> File Name: BootStrap2.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Thu 17 Nov 2016 08:53:10 AM CST
 ************************************************************************/
package org.zwj.servlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.catalina.*;
import org.zwj.servlet.core;
public class BootStrap2{
	public static void main(String[] args){
		HttpConnector connector=new HttpConnector();
		SimpleContext simpleContext=new SimpleContext();
		Wrapper wrapper1=new SimpleWrapper();
		wrapper1.setName("Modrem");
		wrapper.setServletClass("ModremServlet");
		Wrapper wrapper2=new SimpleWrapper();
		wrapper2.setName("Prition");
		wrapper2.setServletClass("PritionServlet");
		simpleContext.addChild(wrapper1);
		simpleContext.addChild(wrapper2);
		Valve valve1=new ClientSimpleValve();
		Valve valve2=new ClientLoggValve();
		simpleContext.addValve(valve1);
		simpleContext.addValve(valve2);
		Mapper mapper=new SimpleContextMapper();
		mapper.setProtocol("http");
		simpleContext.addMapper(mapper);
		Loader loader=new SimpleLoader();
		simpleContext.setLoader(loader);
		simpleContext.addServletMapping("/Modrem","Modrem");
		simpleContext.addServletMapping("/Prition","Prition");
		connector.setContainer(simpleContext);
		try{
			connector.init();
			connector.start();
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
}

