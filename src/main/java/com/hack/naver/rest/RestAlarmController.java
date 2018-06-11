package com.hack.naver.rest;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.hack.naver.model.Response;
import com.hack.naver.service.LoginService;
import com.hack.naver.service.NoticeService;

@RestController
public class RestAlarmController {
	private Gson gson=new Gson();
	
	@Resource(name = "LoginService")
	private LoginService loginService ;
	
	@PostMapping("/alarm/judge")
	public @ResponseBody String alarmJudge(@RequestBody Map<String,Object> data,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws UnsupportedEncodingException {
		
		System.out.println("alarmJudge - POST");	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		Map<String, Object> dataMap=gson.fromJson(data.get("data").toString(), Map.class);
		String noticeId=dataMap.get("id").toString();
		String myId=data.get("userId").toString();
		dataMap.remove("id");
		
		String result=loginService.selectOneUserGroup(myId,noticeId,dataMap);
		return result;
	}
}
