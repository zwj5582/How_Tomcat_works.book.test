/*************************************************************************
	> File Name: SimpleWrapper.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Tue 15 Nov 2016 09:47:11 PM CST
 ************************************************************************/
package org.zwj.servlet.core;
import org.apache.catalina.*;
import java.net.*;
import java.io.*;
import java.bean.*;
import javax.servlet.*;
public class SimpleWrapper implements Wrapper,Pipeline{
	private Pipeline pipeline=new Pipeline(this);
	private Servlet instance=null;
	private String ServletClass=null;
	private Loader loader=null;
	private Container parent=null;
	public SimpleWrapper(){
		pipeline.setBasic(new SimpleWrapperValve());
	}
	public synchronized void addValve(Valve valve){
		pipeline.addValve(valve);
	}
	public void load()throws ServletException{
		instance=loadServlet();
	}
	public ClassLoader getLoader(){
		if(this.loader!=null)
			return loader;
		else if(parent!=null)
			return parent.getLoader();
		else
			return null;
	}
	public void setBasic(Valve valve){
		pipeline.setBasic(valve);
	}
	public Valve getBasic(){
		return pipeline.getBasic();
	}
	public Valve[] getValves(){
		return pipeline.getValves();
	}
	public void setLoader(ClassLoader loader){
		this.loader=loader;
	}
	public void invoke(Request request,Response response)throws IOException,ServletException{
		pipeline.invokeNext(request,response);
	}
	public void setParent(Container c){
		this.parent=c;
	}
	public Container getParent(){
		return parent;
	}
	public Servlet allocate()throws ServletException{
		if(instance==null){
			try{
				instance=loadServlet();
			}catch(ServletException e){
				throw e;
			}catch(Throwable ee){
				throw new ServletException("Cannot allocate a servlet",ee);
			}
		}
		return instance;
	}
	private Servlet loadServlet()throws ServletException{
		if(instance!=null)
			return instance;
		String name=ServletClass;
		if(name==null)
			throw new ServletException("servlet class not been specified");
		Loader loader=getloader();
		if(loader==null)
			throw new ServletException("not found loader");
		Class clazz=null;
		try{
			Class clazz=loader.getClassLoader().loadClass(name);
		}catch(ClassNotFoundException e){
			throw new ServletException("servlet class not found");
		}
		Servlet servlet=null;
		try{
			servlet=(Servlet)clazz.newInstance();
		}catch(Throwable e){
			throw new ServletException("not instance");
		}
		try{
			servlet.init(null);
		}catch(Throwable e){
			throw new ServletException("failed init");
		}
		return servlet;
	}
	public void setServletClass(String servletClass){
		this.servletClass=servletClass;
	}
	public String getServletClass(){
		return servletClass;
	}
	public String getInfo() {
    return null;
  }
	public Logger getLogger() {
    return null;
  }

  public void setLogger(Logger logger) {
  }

  public Manager getManager() {
    return null;
  }

  public void setManager(Manager manager) {
  }

  public Cluster getCluster() {
    return null;
  }

  public void setCluster(Cluster cluster) {
  }

  public String getName() {
    return null;
  }

  public void setName(String name) {
  }
	public ClassLoader getParentClassLoader() {
    return null;
  }

  public void setParentClassLoader(ClassLoader parent) {
  }

  public Realm getRealm() {
    return null;
  }

  public void setRealm(Realm realm) {
  }

  public DirContext getResources() {
    return null;
  }

  public void setResources(DirContext resources) {
  }

  public long getAvailable() {
    return 0;
  }

  public void setAvailable(long available) {
  }

  public String getJspFile() {
    return null;
  }

  public void setJspFile(String jspFile) {
  }

  public int getLoadOnStartup() {
   return 0;
  }

  public void setLoadOnStartup(int value) {
  }

  public String getRunAs() {
    return null;
  }

  public void setRunAs(String runAs) {
  }
	public void addChild(Container child) {
  }

  public void addContainerListener(ContainerListener listener) {
  }

  public void addMapper(Mapper mapper) {
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
  }

  public Container findChild(String name) {
    return null;
  }

  public Container[] findChildren() {
    return null;
  }

  public ContainerListener[] findContainerListeners() {
    return null;
  }

  public void addInitParameter(String name, String value) {
  }

  public void addInstanceListener(InstanceListener listener) {
  }

  public void addSecurityReference(String name, String link) {
  }

  public void deallocate(Servlet servlet) throws ServletException {
  }

  public String findInitParameter(String name) {
    return null;
  }

  public String[] findInitParameters() {
    return null;
  }

  public String findSecurityReference(String name) {
    return null;
  }

  public String[] findSecurityReferences() {
    return null;
  }

  public Mapper findMapper(String protocol) {
    return null;
  }

  public Mapper[] findMappers() {
    return null;
  }
	public boolean isUnavailable() {
    return false;
  }
	public Container map(Request request, boolean update) {
    return null;
  }

  public void removeChild(Container child) {
  }

  public void removeContainerListener(ContainerListener listener) {
  }

  public void removeMapper(Mapper mapper) {
  }

  public void removeInitParameter(String name) {
  }

  public void removeInstanceListener(InstanceListener listener) {
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
  }

  public void removeSecurityReference(String name) {
  }

  public void unavailable(UnavailableException unavailable) {
  }

  public void unload() throws ServletException {
  }
	public void removeValve(Valve valve) {
    pipeline.removeValve(valve);
  }

}

