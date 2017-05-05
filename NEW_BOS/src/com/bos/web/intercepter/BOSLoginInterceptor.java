package com.bos.web.intercepter;

import org.apache.struts2.ServletActionContext;

import com.bos.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 设置用户登录的页面
 * @author HGD
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor{

	//拦截方法
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		
		if(user == null) {
			return "login";
		}
		return invocation.invoke();
	}

}
