package com.hack.naver.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;
import com.hack.naver.service.PagingService;

@Controller
public class PagingController {
	@Resource(name = "PagingService")
	private PagingService pagingDao ;
	
	
	@RequestMapping(value = "/paging", method = RequestMethod.GET)
	public String paging(HttpServletRequest request) {
		int unit=10;
		int pagingNo=1;
		try{
			unit=Integer.parseInt(request.getParameter("unit"));
			pagingNo=Integer.parseInt(request.getParameter("no"));
		}catch (Exception e) { 			
		}
		
		Map<String, Object> map=pagingDao.select(pagingNo,unit);
		request.setAttribute("map", map);
		return "paging";
	}
	
	@RequestMapping(value = "/insert-notice")
	public String insertNotice(HttpServletRequest request) {
		return "insert_notice";
	}
}
