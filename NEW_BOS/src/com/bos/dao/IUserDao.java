package com.bos.dao;

import com.bos.dao.base.IBaseDao;
import com.bos.domain.User;

public interface IUserDao extends IBaseDao<User>{

	public User findByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);
	
	

}
