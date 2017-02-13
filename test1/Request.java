/*************************************************************************
	> File Name: Request.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Fri 11 Nov 2016 12:20:29 PM CST
 ************************************************************************/
package org.zhong.net.servlet1;
import java.io.*;
import java.net.*;
import java.util.*;
public class Request implements ServletRequest{
	private InputStream input;
	private String url;
	public Request(InputStream input){
		this.input=input;
	}
	public String getURL(){
		return url;
	}
	public void parse(){
		StringBuffer string=new StringBuffer(2048);
		byte[] buff=new byte[2048];
		int i=0;
		try{
			i=input.read(buff);
		}catch(IOException e){
			e.printStackTrace();
			i=-1;
		}
		for(int j=0;j<i;j++){
			string.append((char)buff[j]);
		}
		url=getParseUrl(string.toString());
	}
	private String getParseUrl(String string){
		String[] strarr=string.split(" ");
		int i=0;
		for(String str : strarr){
			if(str!=null&&!str.equals("")&&!str.equals(" "))
				i++;
			if(i==2)
				return str;
		}
		return null;
	}
	@Override
    public Object getAttribute(String name){
		return null;
	}
    public Enumeration getAttributeNames(){
		return null;
	}
    public String getCharacterEncoding(){
		return null;
	}
    public void setCharacterEncoding(String env) throws java.io.UnsupportedEncodingException{
	}
    public int getContentLength(){
		return 0;
	}
	public String getContentType(){
		return null;
	}
	public ServletInputStream getInputStream()throws IOException{
		return null;
	}
	public String getParameter(String name){
		return null;
	}
	public Enumeration getParameterNames(){
		return null;
	}
	public String[] getParameterValues(String name){
		return null;
	}
	public Map getParameterMap(){
		return null;
	}
	public String getProtocol(){
		return null;
	}
	public String getScheme(){
		return null;
	}
	public String getServerName(){
		return null;
	}
	public int getServerPort(){
		return 0;
	}
	public BufferedReader getReader()throws IOException{
		return null;
	}
	public String getRemoteAddr(){
		return null;
	}
	public String getRemoteHost(){
		return null;
	}
	public void setAttribute(String name,Object o){}
	public void removeAttribute(String name){}
	public Locale getLocale(){
		return null;
	}
	public Enumeration getLocales(){
		return null;
	}
	public boolean isSecure(){
		return false;
	}
	public RequestDispatcher getRequestDispatcher(String name){
		return null;
	}
	public String getRealPath(String path){
		return null;
	}
	public int getRemotePort(){
		return 0;
	}
	public String getLocalName(){
		return null;
	}
	public String getLocalAddr(){
		return null;
	}
	public int getLocalPort(){
		return 0;
	}
}

