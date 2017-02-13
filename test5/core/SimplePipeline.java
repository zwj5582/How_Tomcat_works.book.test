/*************************************************************************
	> File Name: SimplePipeline.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Tue 15 Nov 2016 06:58:04 PM CST
 ************************************************************************/
package org.zwj.servlet.core;
import java.io.*;
import org.apache.catalina.*;
import java.util.*;
public class SimplePipeline implements Pipeline{
	public SimplePipeline(Container container){
		setContainer(container);
	}
	private Container container=null;
	private Arraylist<Valve> valves=new Arraylist<>();
	private Valve basic=new SimpleWrappedValve(); 
	public Valve[] getValves(){
		return (Valve[])valves.toArray();
	}
	public void setBasic(Valve valve){
		this.basic=valve;
		((Contained)valve).setContainer(container);
	}
	public Valve getBasic(){
		return basic;
	}
	public synchronized void addValve(Valve valve){
		valves.add(valve);
		if(valve instanceof Contained)
			((Contained)valve).setContainer(container);
	}
	public void setContainer(Container container){
		this.container=container;
	}
	public void invoke(Request request,Response response)throws IOException,ServletException{
		(new SimplePipeValveContext()).invokeoNext(request,response);
	}
	private class SimplePipeValveContext implements ValveContext{
		protected int stage=0;
		public String getInfo(){
			return null;
		}
		public void invokeNext(Request request,Response response)throws IOException,ServletException{
			int substage=stage;
			stage++;
			if(substage<valves.size()){
				values.get(i).invoke(request,response,this);
			}else if(substage==valves.size()&&basic!=null)
				basic.invoke(request,response);
			else{
				throw new ServletException("no valve");
			}
		}
	}
}

