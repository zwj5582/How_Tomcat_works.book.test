/*************************************************************************
	> File Name: StaticProcessor.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Sun 13 Nov 2016 12:12:27 PM CST
 ************************************************************************/
package org.zhong.wenjie.servlet;
package org.zhong.wenjie.servlet.http.*;
public class StaticProcessor{
	public void process(HttpRequest request,HttpResponse response){
		try{
			request.sendStaticFile();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

