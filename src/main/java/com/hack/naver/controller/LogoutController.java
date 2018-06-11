package com.hack.naver.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hack.naver.service.LogoutService;

@Controller
public class LogoutController {
	
	@Resource(name = "LogoutService")
	private LogoutService logoutService ;
	
	@GetMapping("/logout")
	public ModelAndView logout(ModelAndView modelAndView,HttpSession session) {
		String id=session.getAttribute("userId").toString();
		
		//유저가 공지사항 볼 수 있었던 갯수 저장
		logoutService.insertUserCount(id);
		
		session.removeAttribute("userId");
		modelAndView.setViewName("redirect:/");
		return modelAndView;
	}
}
