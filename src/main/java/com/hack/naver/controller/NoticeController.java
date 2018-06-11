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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hack.naver.service.LoginService;
import com.hack.naver.service.NoticeService;

@Controller
public class NoticeController {
	
	@Resource(name = "NoticeService")
	private NoticeService noticeService;
	
	@Resource(name = "LoginService")
	private LoginService loginService;
	
	@GetMapping("/insert-notice-form")
	public ModelAndView insertNoticeForm(ModelAndView modelAndView) {
		modelAndView.setViewName("insert_notice_form");
		return modelAndView;
	}

	@GetMapping("/update-notice-form")
	public ModelAndView updateNoticeForm(@RequestParam("num") int numParam,ModelAndView modelAndView,HttpSession session) throws UnsupportedEncodingException {
		System.out.println("noticeUpdateForm - GET");
		modelAndView.setViewName("update_notice_form");
		
		modelAndView.addObject("id",session.getAttribute("userId"));
		modelAndView.addObject("num", numParam);
		
		return modelAndView;
	}
	@GetMapping("/notice-select")
	public ModelAndView noticeSelect(@RequestParam("num") String num,ModelAndView modelAndView) throws UnsupportedEncodingException {
		modelAndView.setViewName("select_one_notice");
		
		Map<String,Object> map=noticeService.selectOneNotice(num);
		modelAndView.addObject("selectOneNotice",map);
		
		return modelAndView;
		
	}
	@PostMapping("/notice")
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
