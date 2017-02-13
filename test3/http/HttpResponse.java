/*************************************************************************
	> File Name: HttpResponse.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Mon 14 Nov 2016 03:27:05 PM CST
 ************************************************************************/
package org.zhong.wenjie.servlet.http;
import java.io.*;
import java.next.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import org.apache.catalina.util.*;
public class HttpResponse implements HttpServletResponse{
	private OutputStream output;
	private HttpRequest request;
	PrintWriter writer;
	protected String encoding;
	protected ArrayList<Cookie> cookies=new ArrayList<>();
	protected HashMap<String,ArrayList<String>> headers=new HashMap<>();
	private static final int BUFFER_SIZE=1024;
	protected byte[] buff=new byte[BUFFER_SIZE];
	protected int bufferCount=0;
	protected int status=HttpServletResponse.SC_OK;
	protected String message=getStatusMessage(HttpServletResponse.SC_OK);
	protected boolean committed=false;
	private String protocal;
	private String ContentType;
	private String encoding;
	private int ContentLength;
	public HttpResponse(OutputStream output){
		this.output=output;
	}
  protected final SimpleDateFormat format =
    new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.US);
	public PrintWriter getWriter()throws IOException{
		ResponseStream newStream=new ResponseStream(this);
		newStream.setCommit(false);
		writer=new PrintWriter(new OutputStreamWriter(newStream,getCharacterEncding()));
		return writer;
	}
	public void write(byte[] b,int off,int len)throws IOException{
		if(len==0)
			return;
		if(len<=buff.length-bufferCount){
			System.arraycopy(b,off,buff,bufferCount,len);
			bufferCount+=len;
			contentCount+=len;
			return;
		}
		flushBuffer();
		int num=len/buff.length;
		int rest=len-num*buff.length;
		for(int i=0;i<num;i++){
			write(b,off+i*buff.length,buff.length);
		}
		if(rest>0){
			write(b,off+num*buff.length,rest);
		}
	}
	public void write(byte[] b)throws IOException{
		write(b,0,b.length);
	}
	public void write(int b)throws IOException{
		if(bufferCount>=buff.length)
			flushBuffer();
		buff[bufferCount++]=(byte)b;
		contentCount++;
		return;
	}
	public void flushBuffer()throws IOException{
		if(bufferCount>0){
			try{
				output.write(buff,0,bufferCount);
			}finally{
				bufferCount=0;
			}
		}
	}
	protected void sendHeaders()throws IOException{
		if(isCommitted)
			return;
		OutputStreamWriter o=null;
		try{
			o=new OutputStreamWriter(getStream(),getCharacterEncoding());
		}catch(UnsupportedEncodingException e){
			ooo=new OutputStreamnWriter(getStream);
		}
		final PrintWiter out=new PrintWriter(o);
		out.print(getProtocal());
		out.print(" ");
		out.print(status);
		if(message!=null){
			out.print(" ");
			out.print(message);
		}
		out.print("\r\n");
		if(getContentType()!=null){
			out.print("Content-Type: "+getContentType()+"\r\n");
		}
		if(getContentLength()!=null){
			out.print("Content-Length: "+getContentLength()+"\r\n");
		}
		synchronized(headers){
			for(String name : headers.KeySet()){
				out.print(name+": ");
				ArrayList<String> list=headers.get(name);
				for(String value : list){
					out.print(value+";");
				}
				out.print("\r\n");
			}
		}
		synchronized(cookies){
			for(Cookie cookie : cookies){
				out.print(CookieTools.getCookieHeaderName(cookie));
				ou.print(":");
				out.print(CookieTools.getCookieHeaderValue(cookie));
				out.print("\r\n");
			}
		}
		out.println("\r\n");
		out.flush();
		committed=true;
	}
	public void setRequest(HttpRequest request){
		this.request=request;
	}
	public String getProtocal(){
		request.getProtocal();
	}
	public void finishResponse(){
		if(writer!=null){
			writer.flush();
			writer.close();
		}
	}
	public int getContentLength() {
		return contentLength;
	}

	public String getContentType() {
		return contentType;
	}
	public OutputStream getStream(){
		return this.output;
	}
	protected String getStatusMessage(int status) {
    switch (status) {
      case SC_OK:
        return ("OK");
      case SC_ACCEPTED:
        return ("Accepted");
      case SC_BAD_GATEWAY:
        return ("Bad Gateway");
      case SC_BAD_REQUEST:
        return ("Bad Request");
      case SC_CONFLICT:
        return ("Conflict");
      case SC_CONTINUE:
        return ("Continue");
      case SC_CREATED:
        return ("Created");
      case SC_EXPECTATION_FAILED:
        return ("Expectation Failed");
      case SC_FORBIDDEN:
        return ("Forbidden");
      case SC_GATEWAY_TIMEOUT:
        return ("Gateway Timeout");
      case SC_GONE:
        return ("Gone");
      case SC_HTTP_VERSION_NOT_SUPPORTED:
        return ("HTTP Version Not Supported");
      case SC_INTERNAL_SERVER_ERROR:
        return ("Internal Server Error");
      case SC_LENGTH_REQUIRED:
        return ("Length Required");
      case SC_METHOD_NOT_ALLOWED:
        return ("Method Not Allowed");
      case SC_MOVED_PERMANENTLY:
        return ("Moved Permanently");
      case SC_MOVED_TEMPORARILY:
        return ("Moved Temporarily");
      case SC_MULTIPLE_CHOICES:
        return ("Multiple Choices");
      case SC_NO_CONTENT:
        return ("No Content");
      case SC_NON_AUTHORITATIVE_INFORMATION:
        return ("Non-Authoritative Information");
      case SC_NOT_ACCEPTABLE:
        return ("Not Acceptable");
      case SC_NOT_FOUND:
        return ("Not Found");
      case SC_NOT_IMPLEMENTED:
        return ("Not Implemented");
      case SC_NOT_MODIFIED:
        return ("Not Modified");
      case SC_PARTIAL_CONTENT:
        return ("Partial Content");
      case SC_PAYMENT_REQUIRED:
        return ("Payment Required");
      case SC_PRECONDITION_FAILED:
        return ("Precondition Failed");
      case SC_PROXY_AUTHENTICATION_REQUIRED:
        return ("Proxy Authentication Required");
      case SC_REQUEST_ENTITY_TOO_LARGE:
        return ("Request Entity Too Large");
      case SC_REQUEST_TIMEOUT:
        return ("Request Timeout");
      case SC_REQUEST_URI_TOO_LONG:
        return ("Request URI Too Long");
      case SC_REQUESTED_RANGE_NOT_SATISFIABLE:
        return ("Requested Range Not Satisfiable");
      case SC_RESET_CONTENT:
        return ("Reset Content");
      case SC_SEE_OTHER:
        return ("See Other");
      case SC_SERVICE_UNAVAILABLE:
        return ("Service Unavailable");
      case SC_SWITCHING_PROTOCOLS:
        return ("Switching Protocols");
      case SC_UNAUTHORIZED:
        return ("Unauthorized");
      case SC_UNSUPPORTED_MEDIA_TYPE:
        return ("Unsupported Media Type");
      case SC_USE_PROXY:
        return ("Use Proxy");
      case 207:       // WebDAV
        return ("Multi-Status");
      case 422:       // WebDAV
        return ("Unprocessable Entity");
      case 423:       // WebDAV
        return ("Locked");
      case 507:       // WebDAV
        return ("Insufficient Storage");
      default:
        return ("HTTP Response Status " + status);
    }
  }
	public void sendStaticFile()throws IOException{
		byte[] buff=new byte[BUFFER_SIZE];
		FileInputStream fis=null;
		try{
			File file=new File(Canstants.WEB_ROOT,request.getRequestURI());
			fis=new FileInputStream(file);
			int len=fis.read(buff,0,BUFFER_SIZE);
			while(len>0){
				output.write(buff,0,len);
				len=fis.read(buff,0,BUFFER_SIZE);
			}
		}catch(FileNotFoundException e){
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
				"Content-Type: text/html\r\n" +
				"Content-Length: 23\r\n" +
				"\r\n" +
				"<h1>File Not Found</h1>";
			output.write(errorMessage.getBytes());
		}finally{
			if(fis!=null){
				fis.close();
			}
		}
	}
	public void addCookie(Cookie cookie){
		if(isCoomitted())
			return;
		synchronized(cookies){
			cookies.add(cookie);
		}
	}
	public void addDateHeader(String name,long value){
		if(isCommitted())
			return;
		addHeader(name,format.format(new Date(value)));
	}
	public void addHeader(String name,String value){
		if(isCommitted())
			return;
		synchronized(headers){
		ArrayList<String> values=headers.get(name);
			if(values==null)
				values=new ArrayList<String>();
			values.add(value);
		}
	}
	public void addIntHeader(String name,int value){
		if(isCommitted())
			return;
		addheader(name,""+value);
	}
	public boolean containsHeader(String name){
		synchronized(headers){
			return (headers.get(name)!=null);
		}
	}
	public String encodeRedirectURL(String url) {
		return null;
	}

	public String encodeRedirectUrl(String url) {
		return encodeRedirectURL(url);
	}

	public String encodeUrl(String url) {
		return encodeURL(url);
	}

	public String encodeURL(String url) {
		return null;
	}
	public String getCharacterEncoding(){
		if(encoding==null)
			return "ISO-8859-1";
		else
			return encoding;
	}
	public boolean isCommitted() {
		return (committed);
	}

	public void reset() {
	}

	public void resetBuffer() {
	}

	public void sendError(int sc) throws IOException {
	}

	public void sendError(int sc, String message) throws IOException {
	}

	public void sendRedirect(String location) throws IOException {
	}

	public void setBufferSize(int size) {
	}
	public void setContentLength(int len){
		if(isCommitted())
			return;
		this.contentLength=len;
	}
	public void setContentType(String type) {
		if(isCommitted())
			return;
		this.contentType=type;
	}
	public void setHeader(String name,String value){
		if(isCommitted())
			return;
		ArrayList<String> values=new ArrayList<>();
		values.add(value);
		synchronized(headers){
			headers.put(name,values);
		}
		String match=name.toLowerCase();
		if(match.equals("content-length")){
			int len=-1;
			try{
				len=Integer.parseInt(value);
			}catch(NumberFormatException e){}
			if(len>=0)
				setContentLength(len);
		}else if(match.equals("content-type")){
			setContentType(value);
		}
	}
	public void setIntHeader(String name,int value){
		if(isCommitted())
			return;
		setHeader(name,""+value);
	}
	public void setLocale(Locale locale) {
    if (isCommitted())
      return;
    //if (included)
      //return;     // Ignore any call from an included servlet

   // super.setLocale(locale);
    String language = locale.getLanguage();
    if ((language != null) && (language.length() > 0)) {
      String country = locale.getCountry();
      StringBuffer value = new StringBuffer(language);
      if ((country != null) && (country.length() > 0)) {
        value.append('-');
        value.append(country);
      }
      setHeader("Content-Language", value.toString());
    }
  }
	public void setStatus(int sc) {
  }

  public void setStatus(int sc, String message) {
  }
}


