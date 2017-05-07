package com.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bos.dao.IUserDao;
import com.bos.dao.base.impl.BaseDaoImpl;
import com.bos.domain.User;


@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		String hql =  "from User u where u.username = ? and u.password = ?";
		List<User> list = this.getHibernateTemplate().find(hql,username,password);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public User findUserByUsername(String username) {
		
		String hql =  "from User u where u.username = ? ";
		List<User> list = this.getHibernateTemplate().find(hql,username);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	
}
