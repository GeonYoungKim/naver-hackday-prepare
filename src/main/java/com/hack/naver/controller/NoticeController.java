package com.hack.naver.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hack.naver.service.LoginService;
import com.hack.naver.service.NoticeService;

@Controller
public class NoticeController {
	
	@Resource(name = "NoticeService")
	private NoticeService noticeService;
	
	@Resource(name = "LoginService")
	private LoginService loginService;
	
	@RequestMapping(value = "/insert-notice-form")
	public String insertNoticeForm(HttpServletRequest request) {
		return "insert_notice_form";
	}

	@RequestMapping(value = "/update-notice-form")
	public String updateNoticeForm(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws UnsupportedEncodingException {
		System.out.println("noticeUpdateForm - GET");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		int num = Integer.parseInt(request.getParameter("num"));
		request.setAttribute("id", session.getAttribute("userId"));
		request.setAttribute("num", num);
		return "update_notice_form";
	}
	@RequestMapping(value = "/notice-select")
	public String noticeSelect(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String num=request.getParameter("num");
		
		Map<String,Object> map=noticeService.selectOneNotice(num);
		request.setAttribute("selectOneNotice", map);
		return "select_one_notice";
		
	}
	@RequestMapping(value = "/notice", method = RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public String paging(HttpServletRequest request) {
		String id=request.getParameter("id");
		System.out.println("notice - POST");		
		int unit = 10;
		int pagingNo = 1;
		try {
			unit = Integer.parseInt(request.getParameter("unit"));
			pagingNo = Integer.parseInt(request.getParameter("no"));
		} catch (Exception e) {}

		List<Map<String, Object>> userElement = loginService.getUserElement(id);

		Map<String, Object> map = noticeService.select(pagingNo, unit, userElement, id);
		request.setAttribute("map", map);
		request.setAttribute("unit", unit);
		request.setAttribute("id", id);
		return "notice";
	}
	
}
