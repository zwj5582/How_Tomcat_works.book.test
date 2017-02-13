/*************************************************************************
	> File Name: HttpConnector.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Sun 13 Nov 2016 12:21:09 PM CST
 ************************************************************************/
package orh.zhong.wenjie.servlet.http;
import java.io.*;
import java.net.*;
public class HttpConnector implements Runnable{
	boolean stop=false;
	private String scheme="http";
	public String getScheme(){
		return scheme;
	}
	public void run(){
		ServerSocket socket=null;
		try{
			socket=new ServerSocket(8080,1,new InetAddress.getByName("127.0.0.1"));
		}catch(IOException ex){
			ex.printStackTrace();
			System.exit(1);
		}
		while(!stop){
			Socket s=null;
			try{
				s=socket.accept();
			}catch(IOException e){
				e.printStackTrace();
			}
			HttpProcessor processor=new HttpProcessor(this);
			processor.process(s);
		}
	}
	public void start(){
		new Thread(this).start();
	}
}

