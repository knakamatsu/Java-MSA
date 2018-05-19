package com.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping(path = "/userinfo", method = RequestMethod.GET)
    Object userinfo(Authentication authentication) {
        return authentication;
    }

}
