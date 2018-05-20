package com.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Controller {

	@Autowired
	private UserService UserService;

	@RequestMapping(method = RequestMethod.GET)
	public Object userinfo(Authentication authentication) {
        return authentication;
    }

	@RequestMapping(path = "hello", method = RequestMethod.GET)
	public String example() {
        return UserService.example();
    }


}
