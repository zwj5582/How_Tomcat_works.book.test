/*************************************************************************
	> File Name: SimpleContextLifecycleListener.java
	> Author: zhongwenjie
	> Mail: zwj5582@gmail.com 
	> Created Time: Thu 17 Nov 2016 01:53:46 PM CST
 ************************************************************************/
package org.zhongwenjie.servlet.core;
import org.catalina.*;
public class SimpleContextLifecycleListener implements LifecycleListener{
	public void lifecycleEvent(LifecyclEvent event){
		Lifecycle lifecycle=event.getLifecycle();
		String type=event.getType();
		System.out.println("Event: "+type);
		if(type.equals(Lifecycle.START_EVENT))
			System.out.println("start...");
		else if(type.equals(Lifecycle.STOP_EVENT))
			System.out.println("stop...");
	}
}

