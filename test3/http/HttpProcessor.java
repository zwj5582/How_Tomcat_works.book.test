/*************************************************************************
	> File Name: HttpProcessor.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Sun 13 Nov 2016 12:44:05 PM CST
 ************************************************************************/
package org.zhong.wenjie.servlet.http;
import java.io.*;
import java.net.*;
import org.zhong.wenjie.servlet.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.catalina.util.*;
public class HttpProcessor{
	private HttpConnector connector=null;
	private HttpRequest request;
	private HttpResponse response=null;
	private HttpRequestLine requestLine=new HttpRequestLine();
	protected StringManager sm=StringManager.getManager("org.zhong.wenjie.servlet.http");
	public HttpConnect(HttpConnector connector){
		this.connector=connector;
	}
	public void process(Socket socket){
		SocketInputStream input=null;
		OutputStream output=null;
		try{
			input=new SocketInputStream(socket.getInputStream(),2048);
			out=socket.getOutputStream();
			request=new HttpRequest(input);
			response=new HttpResponse(output);
			response.setRequest(request);
			response.setHeader("Server", "Pyrmont Servlet Container");
			parseRequest(input, output);
			parseHeaders(input);
			if(request.getRequestUrl().startsWith("/servlet")){
				ServletProcessor p=new ServletProcessor();
				p.process(request,response);
			}else{
				StaticProcessor p=new StaticProcessor();
				p.processor(request,response);
			}
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	private void parseHeaders(SocketInputStream input)throws IOException,ServletException{
		while(true){
			HttpHeader header=new HttpHeader();
			input.readHeader(header);
			if(header.nameEnd==0){
				if(header.valueEnd==0)
					return;
				else
					throw new ServletException("header error");
			}
			String name=new String(header.name,0,header.nameEnd);
			String value=new String(header.value,0,header.valueEnd);
			request.addHeader(name,value);
			if(name.equals("cookie")){
				Cookie[] cookies=RequestUtil.parseCookieHeader(value);
				for(int i=0;i<cookies.length;i++){
					if(cookies[i].getName().equals("jsessionid")){
						if(!request.isRequestSessionIdFromCookie()){
							request.setRequestedSessionId(cookies[i].getValue());
							request.setRequestedSessionCookie(true);
							request.setRequestedSessionURL(false);
						}
					}
					request.addCookie(cookies[i]);
				}
			}else if(name.equals("content-length")){
				int n=-1;
				try{
					n=Integer.parseInt(value);
				}catch(Exception e){
					throw new ServletException(sm.getString("httpProcessor.parseHeaders.contentLength"));
				}
				request.setContentLength(n);
			}else if(name.equals("content-type")){
				request.setContentType(value);
			}
		}
	}
	private void parseRequest(SocketInputStream input,OutputStream output)throws IOException,ServletException{
		input.readRequestLine(requestLine);
		String uri;
		String method=new String(requestLine.method,0,requestLine.methodEnd);
		String protocol=new String(requestLine.protocol,0,requestLine.protocolEnd);
		if(method.length()<0){
			throw new ServletException("HTTP method");
		}
		if(requestLine.uriEnd<0){
			throw new ServletException("HTTP url");
		}
		int question=requestLine.indexOf("?");
		if(question<0){
			request.setQueryString(null);
			uri=new String(requestLine.uri,0,requestLine.uriEnd);
		}else{
			request.setQueryString(requestLine.uri,question+1,requestLine.uriEnd-question-1);
			uri=new String(requestLine.uri,0,question);
		}
		if(!uri.startsWith("/")){
			int pos=uri.indexOf("://");
			if(pos<0){
				uri="";
			}else{
				uri=uri.substring(pos+3);
			}
		}
		String match=";jsessionId=";
		int sess1=uri.indexOf(match);
		if(sess1>=0){
			String rest=uri.substring(sess1+match.length());
			int sess2=rest.indexOf(";");
			if(sess2>=0){
				request.setRequestedSessionId(rest.substring(0,sess2));
				rest=rest.substring(sess2);
			}else{
				request.setRequestedSessionId(rest);
				rest="";
			}
			request.setRequestedSessionURL(true);
			uri=uri.substring(0,sess1)+rest;
		}else{
			request.setRequestedSessionId(null);
			request.setRequestedSessionURL(false);
		}
		String normalized=normalize(uri);
		request.setMethod(method);
		request.setProtocal(protocal);
		if(normalized!=null){
			request.setRequestURI(normalized);
		}else{
			request.setRequestURL(uri);
		}
		if(normalized==null){
			throw new ServletException("HTTP normalized");
		}
	}
  protected String normalize(String path) {
    if (path == null)
      return null;
    // Create a place for the normalized path
    String normalized = path;

    // Normalize "/%7E" and "/%7e" at the beginning to "/~"
    if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
      normalized = "/~" + normalized.substring(4);

    // Prevent encoding '%', '/', '.' and '\', which are special reserved
    // characters
    if ((normalized.indexOf("%25") >= 0)
      || (normalized.indexOf("%2F") >= 0)
      || (normalized.indexOf("%2E") >= 0)
      || (normalized.indexOf("%5C") >= 0)
      || (normalized.indexOf("%2f") >= 0)
      || (normalized.indexOf("%2e") >= 0)
      || (normalized.indexOf("%5c") >= 0)) {
      return null;
    }

    if (normalized.equals("/."))
      return "/";

    // Normalize the slashes and add leading slash if necessary
    if (normalized.indexOf('\\') >= 0)
      normalized = normalized.replace('\\', '/');
    if (!normalized.startsWith("/"))
      normalized = "/" + normalized;

    // Resolve occurrences of "//" in the normalized path
    while (true) {
      int index = normalized.indexOf("//");
      if (index < 0)
        break;
      normalized = normalized.substring(0, index) +
        normalized.substring(index + 1);
    }

    // Resolve occurrences of "/./" in the normalized path
    while (true) {
      int index = normalized.indexOf("/./");
      if (index < 0)
        break;
      normalized = normalized.substring(0, index) +
        normalized.substring(index + 2);
    }
    // Resolve occurrences of "/../" in the normalized path
    while (true) {
      int index = normalized.indexOf("/../");
      if (index < 0)
        break;
      if (index == 0)
        return (null);  // Trying to go outside our context
      int index2 = normalized.lastIndexOf('/', index - 1);
      normalized = normalized.substring(0, index2) +
        normalized.substring(index + 3);
    }

    // Declare occurrences of "/..." (three or more dots) to be invalid
    // (on some Windows platforms this walks the directory tree!!!)
    if (normalized.indexOf("/...") >= 0)
      return (null);

    // Return the normalized path that we have completed
    return (normalized);

  }

}

