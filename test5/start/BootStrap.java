/*************************************************************************
	> File Name: BootStrap.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Wed 16 Nov 2016 11:29:10 AM CST
 ************************************************************************/
package org.zwj.servlet;
import javax.servlet.*;
import org.catalina.*;
import org.zwj.servlet.core.*;
public class BootStrap{
	public static void main(String[] args){
		HttpConnector connector=new HttpConnector();
		SimpleWrapper wrapper=new SimpleWrapper();
		SimpleLoader loader=new SimpleLoader();
		wrapper.setLoader(loader);
		wrapper.setServletClass("ModernServlet");
		connector.setContainer(wrapper);
		Valve valve1=new ClientSimpleValve();
		Valve valve2=new ClinetloggValve();
		wrapper.addValve(valve1);
		wrapper.addValve(valve2);
		try{
			connector.init();
			connector.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

