/*************************************************************************
	> File Name: BootStrap.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Tue 15 Nov 2016 03:40:42 PM CST
 ************************************************************************/
package org.wenjie.zhong.servlet;
import org.wenjie.zhong.servlet.http.*;
public class BootStrap{
	public static void main(String[] args){
		SimpleContainer container=new container();
		HttpConnector connector=new HttpConnector();
		connector.setContainer(container);
		connector.init();
		connector.start();
	}
}

