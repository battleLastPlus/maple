package com.maple.serviceImpl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maple.dao.user.UserMapper;
import com.maple.pojo.user.User;
import com.maple.service.user.UserService;

/**
 * @author yrf
 * @date 2018年12月19日
 */

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * @author yrf
	 * @date 2018年12月19日
	 */
	public User queryById(Integer id) {
		// TODO Auto-generated method stub
		System.out.println(333);
		return userMapper.queryById(id);
	}
	
	@Transactional
	public int insertUser(User user) {
		int index = userMapper.insert(user);
		System.out.println(index);
		
		StringBuffer a= null;
		a.toString();
		return index;
	}

}
