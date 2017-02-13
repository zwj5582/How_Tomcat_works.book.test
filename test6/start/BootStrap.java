/*************************************************************************
	> File Name: BootStrap.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Thu 17 Nov 2016 02:07:26 PM CST
 ************************************************************************/
package org.zhongwenjie.servlet;
import org.catalina.*;
import javax.servlet.*;
public class BootStrap{
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
		simpleContext.addListener(new SimpleContextLifecycleListener());
		simpleContext.addServletMapping("/Modrem","Modrem");
		simpleContext.addServletMapping("/Prition","Prition");
		Logger logger=new FileLooger();
		System.setProperty("catatlina.base",System.getProperty("user.dir"));
		logger.setPrefix("FileLogger_");
		logger.setSuffix(".TXT");
		logger.setTimestmap(true);
		logger.setDirectory("web_root");
		simpleContext.setLogger(logger);
		connector.setContainer(simpleContext);
		try{
			connector.init();
			connector.start();
			simpleContext.start();
		}catch(Throwable e){
			e.printStackTrace();
		}
	}
}

