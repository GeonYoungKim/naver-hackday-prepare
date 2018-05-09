package com.hack.naver.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hack.naver.service.LoginService;


/**
 * Handles requests for the application home page.
 */

@Controller
public class LoginController{
	@Resource(name = "LoginService")
	private LoginService loginService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(HttpServletRequest request) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public void login(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		
		String id=request.getParameter("id");
		
		String A=request.getParameter("A");
		String B=request.getParameter("B");
		String C=request.getParameter("C");
		
		loginService.insertUser(id,A,B,C);
		
	}

}
