/*************************************************************************
	> File Name: SimpleContext.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Wed 16 Nov 2016 03:54:45 PM CST
 ************************************************************************/
package org.zwj.servlet.core;
import org.apache.catalina.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import java.io.*;
public class SimpleContext implements Context,Pipeline{
	protected Pipeline pipeline=new SimplePipeline(this);
	protected HashMap<String,Mapper> mappers=new HashMap<>();
	protected HashMap<String,String> servletMappings=new HashMap<>();
	protected Mapper mapper;
	private Container parent=null;
	protected HashMap<String,Container> children=new HashMap<>();
	protected Loader loader=null;
	public SimpleContext(){
		setBasic(new SimpleContextValve());
	}
	public Valve getBasic(){
		return pipeline.getBasic();
	}
	public Valve setBasic(Valve valve){
		pipeline.setBasic(valve);
	}
	public synchronized void addMappers(Mapper mapper){
		if(mappers.get(mapper.getProtocol())!=null)
			throw new ServletException();
		mapper.setContainer(this);
		mappers.put(mapper.getProtocol(),mapper);
		if(mappers.size()==1)
			this.mapper=mapper;
		else
			this.mapper=null
	}
	public void addChild(Container child){
		child.setParent(this);
		synchronized(chridren){
			children.put(child.getName(),child);
		}
	}
	public void addValve(Valve valve){
		pipeline.addValve(valve);
	}
	public synchronized void addServletMappings(String pattern,String servletname){
		servletMappings.add(pattern,servletname);
	}
	public synchronized String findServletMapping(String name){
		return servletMappings.get(name);
	}
	public synchronized String findChild(String name){
		return servletMappings.get(name);
	}
	public Mapper findMapper(String protocol){
		if(this.mapper!=null)
			return mapper;
		synchronized(mappers){
			return mappers.get(protocol);
		}
	}
	public Container map(Request request,Boolean flag){
		Mappper mapper=findMapper(request.getRequest().getProtocol());
		if(mapper==null)
			return null;
		return mapper.map(request,flag);
	}
	public void setLoader(Loader loader){
		this.loader=loader;
	}
	public void getLoader(){
		if(loader!=null)
			return loader;
		if(parent!=null)
			return parent.getLoader();
		return null;
	}
	public void invoke(Request rquest,Response)throws IOException,ServletException{
		pipeline.invoke(request,response);
	}
}

