package com.bos.web.intercepter;

import org.apache.struts2.ServletActionContext;

import com.bos.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * �����û���¼��ҳ��
 * @author HGD
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor{

	//���ط���
	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		
		if(user == null) {
			return "login";
		}
		return invocation.invoke();
	}

}
