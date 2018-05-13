package com.hack.naver.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;
import com.hack.naver.service.NoticeService;

@Controller
public class NoticeController {
	@Resource(name = "NoticeService")
	private NoticeService noticeService ;
	
	
	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public String paging(HttpServletRequest request) {
		int unit=10;
		
		int pagingNo=1;
		try{
			unit=Integer.parseInt(request.getParameter("unit"));
			pagingNo=Integer.parseInt(request.getParameter("no"));
			
		}catch (Exception e) {
		}
		
		Map<String, Object> map=noticeService.select(pagingNo,unit);
		request.setAttribute("map", map);
		request.setAttribute("unit", unit);
		return "notice";
	}
	
	@RequestMapping(value = "/insert-notice-form")
	public String insertNoticeForm(HttpServletRequest request) {
		return "insert_notice_form";
	}
	@RequestMapping(value = "/insert-notice", method = RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public void insertNotice(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		List<String> elementList=new ArrayList<String>();
		String id=request.getParameter("userId");
		String content=request.getParameter("content");
		String A=(request.getParameter("A")!=null)?"A":"NO";
		String B=(request.getParameter("B")!=null)?"B":"NO";
		String C=(request.getParameter("C")!=null)?"C":"NO";	
		
		elementList.add(A);
		elementList.add(B);
		elementList.add(C);
		noticeService.insertNotice(id,elementList,content);
		
	}
}
