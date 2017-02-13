/*************************************************************************
	> File Name: HttpRequest.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Mon 14 Nov 2016 10:54:24 AM CST
 ************************************************************************/
package org.zhong.wenjie.servlet.http;
import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.catalina.util.*;
import java.security.Principal;
import javax.servlet.http.*;
import javax.servlet.*;
import java.text.*;
public class HttpRequest implements HttpServletRequest{
	private Socket socket;
	private InputStream input;
	private ServletInputStream stream;
	protected ArrayList<Cookie> cookies=new ArrayList<>();
	protected HashMap<String,ArrayList<String>> headers=new HashMap<>();
	protected ParameterMap parameters=null;
	protected boolean parsed=false;
	private int contentLength;
	private String contentType;
	private String protocol;
	private String method;
	private String queryString;
	private String requestURI;
	private String encoding;
	BufferedReader reader=null;
	protected SimpleDateFormat formats[] = {
		new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
		new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
		new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)
	};
	public HttpRequest(InputStream input){
		this.input=input;
	}
	public void addHeader(String name,String value){
		name=name.toLowerCase();
		synchronized(headers){
			ArrayList<String> list=headers.get(name);
			if(list==null){
				list=new ArrayList<>();
				headers.put(name,list);
			}
			list.add(value);
		}
	}
	protected void parseParameters(){
		if(parsed)
			return;
		ParameterMap result=parameters;
		result.setLocked(false);
		if(result==null)
			result=new ParameterMap();
		String encoding=getCharacterEncoding();
		if(encoding==null)
			encoding="ISO-8859-1";
		String queryString=getQueryString();
		try{
			RequestUtil.parseParameters(result,queryString,encoding);
		}catch(UnsupportedEncodingException e){}
		String contentType=getContentType();
		if(contentType==null)
			contentType="";
		int pos=contentType.indexOf(";");
		if(pos<0){
			contentType=contentType.trim();
		}else{
			contentType=contentType.substring(0,pos).trim();
		}
		if("POST".equals(getMethod())&&getContentLength()>0&&"application/x-www-form-urlencoded".equals(contentType)){
			try{
				int max=getContentLength();
				int len=0;
				byte[] buf=new byte[max];
				ServletInputStream is=getInputStream();
				while(true){
					int next=is.read(buf,len,max-len);
					if(len<0)
						break;
					len+=next;
				}
				is.close();
				if(len<max)
					throw new RuntimeException("Content length mismatch");
				RequestUtil.parseParameters(result,buf,encoding);
			}catch(UnsupportedEncodingException ue){}
			catch(IOException e){
				throw new RuntimeException("Content read fail");
			}
		}
		result.setLocked(true);
		parsed=true;
		parameters=result;
	}
	public void addCookie(Cookie cookie){
		synchronized(cookies){
			cookies.add(cookie);
		}
	}
	public ServletInputStream createInputStream()throws IOException{
		return new RequestStream(this);
	}
	public ServletInputStream getInputStream()throws IOException{
		if(reader!=null)
	throw new IllegalStateException("getReader has been called");
		if(stream==null)
			stream=createInputStream();
		return stream;
	}
	public String getCharacterEncoding(){
		return encoding;
	}
	public BufferedReader getReader()throws IOException{
		if(stream!=null)
			throw new IllegalStateException("getInputStream has been called");
		if(reader==null){
			String encoding=getCharacterEncoding();
			if(encoding==null)
				encoding="ISO-8859-1";
			reader=new BufferedReader(new InputStreamReader(createInputStream(),encoding));
		}
		return reader;
	}
	public InputStream getStream(){
		return input;
	}
	public void setContentLength(int len){
		this.contentLength=len;
	}
	public void setContentType(String type){
		this.contentType=type;
	}
	public void setQueryString(String queryString){
		this.queryString=queryString;
	}
	public void setMethod(String method){
		this.method=method;
	}
	public void setProtocol(String protocol){
		this.protocol=protocol;
	}
	public void setRequestURI(String requestURI){
		this.requestURI=requestURI;
	}
	public void setRequestedSessionCookie(boolean flag){
		this.requestedSessionCookie=flag;
	}
	private boolean requestedSessionCookie;
	private String requestedSessionId;
	private boolean requestedSessionURL;

	public void setRequestedSessionId(String id){
		this.requestedSessionId=id;
	}

	public void setRequestedSessionURL(boolean flag){
		this.requestedSessionURL=flag;
	}
	public Cookie[] getCookies(){
		synchronized(cookies){
			if(cookies.size()<1)
				return null;
			Cookie[] cook=new Cookie[cookies.size()];
			return cookies.toArray(cook);
		}
	}
	public int getContentLength(){
		return contentLength;
	}
	public String getContentType(){
		return contentType;
	}
	public String getQueryString(){
		return queryString;
	}
	public String getMethod(){
		return method;
	}
	public String getProtocal(){
		return protocol;
	}
	public Enumeration getHeaderNames(){
		synchronized(headers){
			return new Enumerator(headers.keySet());
		}
	}
	public int getIntHeader(String name){
		synchronized(headers){
			ArrayList<String> values=headers.get(name);
			if(values==null){
				return -1;
			}else{
				return Integer.parseInt(values.get(0));
			}
		}
	}
	public String getParameter(String name){
		parseParameters();
		String[] values=(String[])parameters.get(name);
		if(values==null)
			return null;
		else
			return values[0];
	}
	public Map getParameterMap(){
		parseParameters();
		return parameters;
	}
	public Enumeration getParameterNames(){
		parseParameters();
		return new Enumerator(parameters.keySet());
	}
	public String[] getParameterValues(String name){
		parseParameters();
		String[] values=(String[])parameters.get(name);
		if(values==null)
			return null;
		else
			return values;
	}
	public long getDateHeader(String name){
		synchronized(headers){
			String[] values=(String[])headers.get(name).toArray();
			if(values==null)
				return(-1L);
			else{
				for(int i=0;i<formats.length;i++){
					try{
						Date date=formats[i].parse(values[0]);
						return date.getTime();
					}catch(ParseException e){}
				}
			}
		}
		throw new IllegalArgumentException(name);
	}
	public String getRquestedSessionId(){
		return null;
	}
	public String getRequestURI(){
		return requestURI;
	}
	public StringBuffer getRequestURL() {
		return null;
	}

  public HttpSession getSession() {
    return null;
  }

  public HttpSession getSession(boolean create) {
    return null;
  }

  public String getServletPath() {
    return null;
  }

  public Principal getUserPrincipal() {
    return null;
  }

  public boolean isRequestedSessionIdFromCookie() {
    return false;
  }

  public boolean isRequestedSessionIdFromUrl() {
    return isRequestedSessionIdFromURL();
  }

  public boolean isRequestedSessionIdFromURL() {
    return false;
  }

  public boolean isRequestedSessionIdValid() {
    return false;
  }

  public boolean isSecure() {
    return false;
  }

  public boolean isUserInRole(String role) {
    return false;
  }

  public void removeAttribute(String attribute) {
  }

  public void setAttribute(String key, Object value) {
  }

  /**
   * Set the authorization credentials sent with this request.
   *
   * @param authorization The new authorization credentials
   */
  public void setAuthorization(String authorization) {
    //this.authorization = authorization;
  }

  public void setCharacterEncoding(String encoding) throws UnsupportedEncodingException {
  }
	public String getRealPath(String path) {
    return null;
  }
	public String getPathInfo() {
    return null;
  }
	public Locale getLocale() {
    return null;
  }

  public Enumeration getLocales() {
    return null;
  }
  public String getRemoteAddr() {
    return null;
  }

  public String getRemoteHost() {
    return null;
  }

  public String getRemoteUser() {
    return null;
  }

  public RequestDispatcher getRequestDispatcher(String path) {
    return null;
  }

  public String getScheme() {
   return null;
  }

  public String getServerName() {
    return null;
  }

  public int getServerPort() {
    return 0;
  }

}

