/*************************************************************************
	> File Name: Response.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Thu 10 Nov 2016 09:21:44 PM CST
 ************************************************************************/
package org.zhong.net.servlet1;
import java.io.*;
import java.net.*;
import java.util.*;
public class ResponseFacade implements ServletResponse{
	private ServletResponse response;
	public ResponseFacade(ServletResponse response){
		this.response=response;
	}
	public String getCharacterEncoding(){
		return response.getCharacterEncoding();
	}
	public String getContentType(){
		return response.getContentType();
	}
	public ServletOutputStream getOutputStream()throws IOException{
		return response.getOutputStream();
	}
	public PrintWriter getWriter()throws IOException{
		return response.getWriter();
	}
	public void setCharacterEncoding(String charset){
		response.setCharacterEncoding(charset);
	}
	public void setContentLength(int len){
		response.setContentLength(len);
	}
	public void setContentType(String type){
		response.setContentType(type);
	}
	public void setBufferSize(int size){
		response.setBufferSize(size);
	}
	public int getBufferSize(){
		return response.getBufferSize();
	}
	public void flushBuffer()throws IOException{
		response.flushBuffer();
	}
	public void resetBuffer(){
		response.resetBuffer();
	}
	public boolean isCommitted(){
		return response.isCommitted();
	}
	public void reset(){
		response.reset();
	}
	public void setLocale(Locale loc){
		response.setLocale(loc);
	}
	public Locale getLocale(){
		return response.getLocale();
	}
}

