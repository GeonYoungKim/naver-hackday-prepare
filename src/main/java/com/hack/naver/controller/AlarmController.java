package com.hack.naver.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.hack.naver.model.Response;
import com.hack.naver.service.LoginService;

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
}
