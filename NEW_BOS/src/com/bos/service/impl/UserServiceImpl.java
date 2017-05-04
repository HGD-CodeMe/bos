package com.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bos.dao.IUserDao;
import com.bos.domain.User;
import com.bos.service.IUserService;
import com.bos.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements IUserService{

	@Autowired
	private IUserDao userDao;
	
	
	
	@Override
	public User login(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		
		password = MD5Utils.md5(password);//MD5º”√‹
		
		
		
		return userDao.findByUsernameAndPassword(username,password);
	}



	@Override
	public void editPassword(String password, String id) {
		userDao.executeUpdate("editPassword", password, id);
		
	}
	
}
