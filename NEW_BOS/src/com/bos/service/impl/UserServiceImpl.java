package com.bos.service.impl;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bos.dao.IRoleDao;
import com.bos.dao.IUserDao;
import com.bos.domain.Role;
import com.bos.domain.User;
import com.bos.service.IUserService;
import com.bos.utils.MD5Utils;
import com.bos.utils.PageBean;

@Service
@Transactional
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IdentityService identityService;
	
	
	@Override
	public User login(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		
		password = MD5Utils.md5(password);//MD5加密
		return userDao.findByUsernameAndPassword(username,password);
	}



	@Override
	public void editPassword(String password, String id) {
		userDao.executeUpdate("editPassword", password, id);
		
	}



	@Override
	public void pageQuery(PageBean pageBean) {
		
		
		userDao.pageQuery(pageBean);
		
	}



	@Override
	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);
		
		org.activiti.engine.identity.User actUser = 
				new UserEntity(user.getId());
		
		identityService.saveUser(actUser);
		//用户关联角色
		for(String roleId : roleIds) {
			Role role = roleDao.findById(roleId);
			//用户关联角色
			user.getRoles().add(role);
			
			identityService.createMembership(actUser.getId(), role.getName());
		}
		
		
	}
	
}
