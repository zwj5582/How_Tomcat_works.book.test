/*************************************************************************
	> File Name: StaticProcessor.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Fri 11 Nov 2016 02:35:15 PM CST
 ************************************************************************/
package org.zhong.net.servlet;
public class StaticProcessor{
	private Request request;
	private Response response;
	public StaticProcessor(Request request,Response response){
		this.request=request;
		this.response=response;
	}
	public void process()throws java.io.IOException{
		response.sendtoStaticFile();
	}
}

