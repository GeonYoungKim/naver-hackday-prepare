package com.hack.naver.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.hack.naver.model.Response;
import com.hack.naver.service.LoginService;
import com.hack.naver.service.NoticeService;

@Controller
public class AlarmController {
	private Gson gson=new Gson();
	
	@Resource(name = "LoginService")
	private LoginService loginService ;
	
	@MessageMapping(value="/hello")
	@SendTo("/topic/greetings")
	public Response response(Map<String, Object> params){
		
		return new Response(gson.toJson(params));
	}
	
	@RequestMapping(value = "/alarm-judge", method = RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public @ResponseBody String alarmJudge(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws UnsupportedEncodingException {
		
		System.out.println("alarmJudge - POST");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		Map<String, Object> data=gson.fromJson(request.getParameter("data"), Map.class);
		String noticeId=data.get("id").toString();
		String myId=session.getAttribute("userId").toString();
		data.remove("id");
		
		String result=loginService.selectOneUserGroup(myId,noticeId,data);
		return result;
	}
}
