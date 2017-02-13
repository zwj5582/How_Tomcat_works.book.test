/*************************************************************************
	> File Name: SimpleContext.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Thu 17 Nov 2016 12:35:08 PM CST
 ************************************************************************/
package org.zhongwenjie.servlet.core;
import javax.servlet.*;
import javax.servlet.http.*;
import org.catalina.*;
import org.catalina.util.*;
import java.io.*;
import java.net.*;
import java.util.*;
public class SimpleContext implements Lifecycle,Pipeline,Context{
	protected boolean started=false;
	protected Logger logger=null;
	public void setLogger(Logger logger){
		this.logger=logger;
	}
	public Logger getLogger(){
		return logger;
	}
	public void log(String message){
		Logger logger=getLogger();
		if(logger!=null)
			logger.log(message);
	}
	protected LifecycleSupport lifecycle=new LifecycleSupport(this);
	public void addLifecycleListener(LifecycleListener listener){
		lifecycle.addLifecycleListener(listener);
	}
	public LifecycleListener[] findLifecycleListeners(){
		lifecycle.findLifecycleListeners();
	}
	public void removeLifecycleListener(LifecycleListener listener){
		lisecycle.removeLifecycleListener(listener);
	}
	public synchronized void start()throws LifecycleException{
		log("start...");
		if(started)
			throw new LifecycleException();
		lifecycle.fireLifecycleEvent(BEFORE_START_EVENT,null);
		started=true;
		try{
			if(loader!=null&&loader instanceof Lifecycle)
				((Lifecycle)loader).start();
			if(children!=null&&){
				for(Container child : children){
					if(child instanceof Lifecycle)
						((Lifecycle)child).start();
				}
			}
			if(pipeline!=null&&pipeline instanceof Lifecycle)
				((Lifecycle)pipeline).start();
			lifecycle.fireLifecycleEvent(START_EVENT,null);
		}catch(Exception e){
			e.printStackTrace();
		}
		lifecycle.fireLifecycleEvent(AFTER_START_EVENT);
		log("stop...")
	}

}

