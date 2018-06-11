package com.hack.naver.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hack.naver.service.LoginService;


@Controller
public class LoginController{
	@Resource(name = "LoginService")
	private LoginService loginService;
	
	@GetMapping("/")
	public ModelAndView main(ModelAndView modelAndView,HttpSession session) {
		System.out.println("login - GET");
		
		if(session.getAttribute("userId")==null) {
			modelAndView.setViewName("login");
			return modelAndView;	
		}else {
			modelAndView.setViewName("redirect:/notice?id="+session.getAttribute("userId"));
			return modelAndView;
		}
	}

	@PostMapping("/login")
	public @ResponseBody String login(@RequestBody Map<String,Object> data,HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
		System.out.println("login - POST");
		request.setCharacterEncoding("UTF-8");
		List<String> elementList=new ArrayList<String>();
		
		String id=data.get("userId").toString();
		
		String A=data.get("A").toString();
		String B=data.get("B").toString();
		String C=data.get("C").toString();
		
		elementList.add(A);
		elementList.add(B);
		elementList.add(C);
		elementList.add("NO");

		loginService.login(id,elementList);
		session.setAttribute("userId",id);
		
		return id;
	}

}
