/*************************************************************************
	> File Name: Response.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Thu 10 Nov 2016 09:21:44 PM CST
 ************************************************************************/
package org.zhong.net.servlet;
import java.io.*;
import java.net.*;
import java.util.*;
public class Response implements ServletResponse{
	private OutputStream output;
	private Request request;
	public Response(OutputStream output){
		this.output=output;
	}
	public void setRequest(Request request){
		this.request=request;
	}
	public void sendtoStaticFile()throws IOException{
		byte[] buff=new byte[4096];
		FileInputStream fis=null;
		try{
			File file=new File(HttpServer1.WEB_ROOT,request.getURL());
			if(file.exists()){
				fis=new FileInputStream(file);
				int len=fis.read(buff,0,4096);
				while(len!=-1){
					output.write(buff,0,len);
					len=fis.read(buff,0,4096);
				}
			}else{
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
					"Content-Type: text/html\r\n" +
					"Content-Length: 23\r\n" +
					"\r\n" +
					"<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		}catch(IOException ee){
			ee.printStackTrace();
		}finally{
			if(fis!=null){
				fis.close();
			}
		}
	}
	public String getCharacterEncoding(){
		return null;
	}
	public String getContentType(){
		return null;
	}
	public ServletOutputStream getOutputStream()throws IOException{
		return null;
	}
	public PrintWriter getWriter()throws IOException{
		return new PrintWriter(output,true);
	}
	public void setCharacterEncoding(String charset){}
	public void setContentLength(int len){}
	public void setContentType(String type){}
	public void setBufferSize(int size){}
	public int getBufferSize(){
		return 0;
	}
	public void flushBuffer()throws IOException{}
	public void resetBuffer(){}
	public boolean isCommitted(){
		return false;
	}
	public void reset(){}
	public void setLocale(Locale loc){}
	public Locale getLocale(){
		return null;
	}
}

