package com.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.crm.domain.Customer;

import com.bos.domain.User;
import com.bos.service.IUserService;
import com.bos.utils.MD5Utils;
import com.bos.web.action.base.BaseAction;
import com.crm.CustomerService;

@Controller()
@Scope("prototype")//������������
public class UserAction extends BaseAction<User> {
	
	
	
	
	private String checkcode;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	/*public String login() {

		//���ɵ���֤��
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {
			User user = userService.login(model);
			if(user != null) {
				//��¼�ɹ�����user����session���У�ײ�� ϵͳ��ҳ
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return "home";
			}else {
				//��¼ ʧ�ܣ����ô����¼��Ϣ����������ҳ��
				this.addActionError("�û������������");
				return "login";
				
			}
		}else {
			this.addActionError("��֤�����");
			return "login";
		}
		
	}*/
	
	//ʹ��shiro������֤
	public String login() {

		//���ɵ���֤��
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {
			//����û�����
			Subject subject = SecurityUtils.getSubject();
			String password = model.getPassword();
			password = MD5Utils.md5(password);
			//����һ���û�����������
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),password);
			
			try {
				subject.login(token);
			} catch (UnknownAccountException e) {
				e.printStackTrace();
				this.addActionError("�û��������ڣ�");
				return "login";
			}catch (Exception e) {
				e.printStackTrace();
				this.addActionError("�û��������벻��ȷ��");
				return "login";
			}
			//��ȡ����֤�����д洢��user����
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return "home"; 
		
		}else {
			this.addActionError("��֤�����");
			return "login";
		}
		
	}
	
	
	

	/**
	 * �û����˳�
	 */
	public String logout() {
		//����session
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	
	/**
	 * �޸�����
	 * @throws IOException 
	 */
	public String editPassword() throws IOException {
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		
		String password = model.getPassword();
		password = MD5Utils.md5(password);
		String flag = "1";
		try {
			userService.editPassword(password,user.getId());
		} catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
}
