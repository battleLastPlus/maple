package com.maple.service.user;

import com.maple.pojo.user.User;

/**
 * @author yrf
 * @date 2018��12��19��
 */
public interface UserService {

	
	public User queryById(Integer id);
	
	public int insertUser(User user);
}
