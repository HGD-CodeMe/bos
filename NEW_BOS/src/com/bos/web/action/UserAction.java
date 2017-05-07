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
@Scope("prototype")//创建多例对象
public class UserAction extends BaseAction<User> {
	
	
	
	
	private String checkcode;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	/*public String login() {

		//生成的验证码
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {
			User user = userService.login(model);
			if(user != null) {
				//登录成功，将user放入session域中，撞到 系统首页
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return "home";
			}else {
				//登录 失败，设置错误登录信息，重新跳回页面
				this.addActionError("用户名或密码错误");
				return "login";
				
			}
		}else {
			this.addActionError("验证码错误");
			return "login";
		}
		
	}*/
	
	//使用shiro进行验证
	public String login() {

		//生成的验证码
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {
			//获得用户对象
			Subject subject = SecurityUtils.getSubject();
			String password = model.getPassword();
			password = MD5Utils.md5(password);
			//构造一个用户名密码令牌
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),password);
			
			try {
				subject.login(token);
			} catch (UnknownAccountException e) {
				e.printStackTrace();
				this.addActionError("用户名不存在！");
				return "login";
			}catch (Exception e) {
				e.printStackTrace();
				this.addActionError("用户名或密码不正确！");
				return "login";
			}
			//获取从认证对象中存储的user对象
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return "home"; 
		
		}else {
			this.addActionError("验证码错误！");
			return "login";
		}
		
	}
	
	
	

	/**
	 * 用户已退出
	 */
	public String logout() {
		//销毁session
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}
	
	/**
	 * 修改密码
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
