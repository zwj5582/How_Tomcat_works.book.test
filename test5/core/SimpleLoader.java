/*************************************************************************
	> File Name: SimpleLoader.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Tue 15 Nov 2016 06:13:26 PM CST
 ************************************************************************/
package org.zwj.servlet.core;
import org.apache.catalina.*;
import org.apache.catalina.core.*;
import java.io.*;
import java.net.*;
import java.beans.*;
public class SimpleLoader implements Loader{
	public final static String WEB_ROOT=System.getProperty("user.dir")+File.separator+"web_root";
	private ClassLoader classLoader=null;
	private Container parent=null;
	public SimpleLoader(){
		try{
			File file=new File(WEB_ROOT);
			URL[] urls=new URL[1];
			URLStreamHandler streamHandler=null;
			String name=(new URL("file",null,file.getCanonicalPath()+File.separator)).toString();
			urls[0]=new URL(null,name,streamHandler);
			classLoader=new URLClassLoader(urls);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public ClassLoader getClassLoader() {
    return classLoader;
  }

  public Container getContainer() {
    return parent;
  }
	public void setContext(Context c){}
	public Context getContext(){
		return null;
	}
	public void backgroundProcess(){}
  public void setContainer(Container container) {
    this.parent = container;
  }

/*  public DefaultContext getDefaultContext() {
    return null;
  }*/

/*  public void setDefaultContext(DefaultContext defaultContext) {
  }*/

  public boolean getDelegate() {
    return false;
  }

  public void setDelegate(boolean delegate) {
  }

  public String getInfo() {
    return "A simple loader";
  }

  public boolean getReloadable() {
    return false;
  }

  public void setReloadable(boolean reloadable) {
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
  }

  public void addRepository(String repository) {
  }

  public String[] findRepositories() {
    return null;
  }

  public boolean modified() {
    return false;
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
  }

}

