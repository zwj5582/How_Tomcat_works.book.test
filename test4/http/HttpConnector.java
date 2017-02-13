/*************************************************************************
	> File Name: HttpConnector.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Tue 15 Nov 2016 11:54:15 AM CST
 ************************************************************************/
package org.wenjie.zhong.servlet.http;
import java.io.*;
import java.net.*;
public class HttpConnector{
	protected int minProcessor=5;
	protected int maxProcessor=20;
	protected int curProcessor=0;
	private Stack<HttpProcessor> processors=new Stack<>();
	private Vector<HttpProcessor> vector=new Vector<>();
	private int port=8080;
	private String scheme="http";
	private ServerSocket serversocket=null;
	private Thread thread=null;
	private String threadName=null;
	private boolean stoped=false;
	private boolean inited=false;
	private boolean started=false;
	public void init(){
		if(!inited){
			try{
				InetAddress addr=new InetAddress("127.0.0.1");
				serverSocket=new ServerSocket(port,1,addr);
			}catch(Exception e){}
			if(serverSocket==null)
				System.exit(0);
			inited=true;
		}
	}
	public void run(){
		while(!stoped){
			Socket socket=null;
			try{
				socket=serverSocket.accept();
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
			HttpProcessor processor=createProcessor();
			if(processor==null){
				continue;
			}
			processor.assign(socket);
		}
	}
	public HttpProcessor createProcessor(){
		synchronized(processors){
			if(processors.size()>0){
				return processors.pop();
			}
			if(maxProcessor>0&&curProcessor<maxProcessor){
				return newPropcessor();
			}else{
				if(maxProcessor<0)
					return newProcessor();
				else
					return null;
			}
		}
	}
	public HttpProcessor newProcessor(){
		HttpProcessor processor=new HttpProcessor(this,threadName);
		processor.start();
		vector.addElement(processor);
		return processor;
	}
	public void Threadstart(){
		thread=new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}
	public void start(){
		if(!started){
			threadName="HttpConnector["+port+"]";
			started=true;
			threadStart();
			while(curProcessor<minProcessor){
				if(maxProcessor>0&&curProcessor>maxProcess)
					break;
				HttpProcessor processor=newProcessor();
				recycle(processor);
			}
		}
	}
	public void recycle(HttpProcessor processor){
		processors.push(processor);
	}
}

