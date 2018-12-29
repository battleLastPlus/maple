package com.maple.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yrf
 * @date 2018年12月29日
 */

@Controller
@RequestMapping("/")
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	
	@RequestMapping("/toLogin")
	public String toLogin() {
		logger.debug("===111-------------login------------");
		return "login";
	}
}
