package com.bos.service;



import com.bos.domain.User;


public interface IUserService {
	//ע��
	public User login(User user);

	public void editPassword(String password, String id);
	
	
	
	

}
