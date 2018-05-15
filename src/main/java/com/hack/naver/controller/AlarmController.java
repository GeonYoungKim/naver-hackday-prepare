package com.hack.naver.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hack.naver.model.Response;
import com.hack.naver.model.Test;

@Controller
public class AlarmController {

	
	@RequestMapping(value="/alarm")
	public String alarm(){
		return "alarm";
	}
	
	
	@MessageMapping(value="/hello")
	@SendTo("/topic/greetings")
	public Response response(Test test) throws InterruptedException {
		
		return new Response("Hello " +test.getName());
	}
}
