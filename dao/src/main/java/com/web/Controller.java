package com.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.Users;
import com.domain.UsersService;

@RestController
@RequestMapping("/user")
public class Controller {

	@Autowired
	UsersService service;

	// 一覧表示
	@RequestMapping(method=RequestMethod.GET)
	public List<Users> counterSelectAll() {
		return service.selectAll();
	}
}
