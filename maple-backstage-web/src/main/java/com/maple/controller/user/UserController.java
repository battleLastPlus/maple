package com.maple.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maple.pojo.user.User;
import com.maple.service.user.UserService;



@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/getUser")
	public String getUser() {
		
		User user = userService.queryById(1);
		System.out.println(user.getUsername());
		return "/test";
	}
	
	@RequestMapping("/insertUser")
	public String insertUser() {
		
		User user = new User();
		user.setEmail("1212");
		int index = userService.insertUser(user);
		System.out.println(index);
		return "/test";
	}
}
