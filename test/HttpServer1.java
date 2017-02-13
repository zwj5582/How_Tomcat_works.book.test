/*************************************************************************
	> File Name: HttpServlet1.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Fri 11 Nov 2016 12:13:18 PM CST
 ************************************************************************/
package org.zhong.net.servlet;
import java.io.*;
import java.net.*;
public class HttpServer1{
	public static final String WEB_ROOT=System.getProperty("user.dir")+File.separator+"web_root";
	private static final String SHUTDOWN="/showdown";
	private boolean showdown=false;
	public static void main(String[] args){
		HttpServer1 http=new HttpServer1();
		http.await();
	}
	public void await(){
		ServerSocket socket=null;
		try{
			socket=new ServerSocket(8080,1,InetAddress.getByName("127.0.0.1"));
		}catch(IOException ex){
			ex.printStackTrace();
			System.exit(0);
		}
		while(!showdown){
			Socket sock=null;
			InputStream input=null;
			OutputStream output=null;
			try{
				sock=socket.accept();
				input=sock.getInputStream();
				output=sock.getOutputStream();
				Request request=new Request(input);
				request.parse();
				Response response=new Response(output);
				response.setRequest(request);
				if(request.getURL().startsWith("/servlet/")){
					ServletProcessor processor=new ServletProcessor(request,response);
					processor.process();
				}else{
					StaticProcessor processor=new StaticProcessor(request,response);
					processor.process();
				}
				sock.close();
				showdown=request.getURL().equals(SHUTDOWN);
			}catch(IOException ee){
				ee.printStackTrace();
				continue;
			}
		}
	}
}

