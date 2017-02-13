/*************************************************************************
	> File Name: HttpProcessor.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Tue 15 Nov 2016 12:58:56 PM CST
 ************************************************************************/
package org.wenjie.zhong.servlet.http;
import java.io.*;
import java.net.*;
public class HttpProcessor implements Runnable{
	private Thread thread=null;
	private String threadName=null;
	private boolean available=false;
	private HttpConnector connector;
	private Socket socket=null;
	private boolean stopped=false;
	public HttpProcessor(HttpConnector connector,String threadName){
		this.threadName=threadName;
		this.connector=connector;
	}
	public void run(){
		while(!stopped){
			try{
				socket=await();
			}catch(Exception e){}
			available=true;
			process(socket);
			connector.recycle(this);
		}
	}
	private synchronized Socket await(){
		while(!available){
			try{
				wait();
			}catch(Exception);
		}
		Socket socket=this.socket;
		available=true;
		return socket;
	}
	synchronized void assign(Socket socket){
		while(available){
			try{
				wait();
			}catch(InterruptedException e){}
		}
		this.socket=socket;
		available=true;
		notifyAll();
	}
}

