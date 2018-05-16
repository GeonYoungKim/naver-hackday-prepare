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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hack.naver.service.LoginService;
import com.hack.naver.service.NoticeService;

@Controller
public class NoticeController {
	@Resource(name = "NoticeService")
	private NoticeService noticeService ;
	@Resource(name = "LoginService")
	private LoginService loginService ;
	
	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public String paging(HttpServletRequest request,HttpSession sesstion) {
		System.out.println("notice - GET");
		
		int unit=10;
		int pagingNo=1;
		try{
			unit=Integer.parseInt(request.getParameter("unit"));
			pagingNo=Integer.parseInt(request.getParameter("no"));
			
		}catch (Exception e) {
		}
		
		List<Map<String,Object>> userElement=loginService.getUserElement(((String)sesstion.getAttribute("userId")));
		
		Map<String, Object> map=noticeService.select(pagingNo,unit,userElement);
		request.setAttribute("map", map);
		request.setAttribute("unit", unit);
		return "notice";
	}
	
	@RequestMapping(value = "/insert-notice-form")
	public String insertNoticeForm(HttpServletRequest request) {
		System.out.println("noticeInsertForm - GET");
		return "insert_notice_form";
	}
	@RequestMapping(value = "/insert-notice", method = RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public String insertNotice(HttpServletRequest request) throws UnsupportedEncodingException {
		System.out.println("noticeInsert - POST");
		request.setCharacterEncoding("UTF-8");
		List<String> elementList=new ArrayList<String>();
		String id=request.getParameter("userId");
		String content=request.getParameter("content");
		String A=(request.getParameter("A")!=null)?"A":"NO";
		String B=(request.getParameter("B")!=null)?"B":"NO";
		String C=(request.getParameter("C")!=null)?"C":"NO";	
		
		if(A.equals("NO")&&B.equals("NO")&&C.equals("NO")) {
			elementList.add("NO");
		}else {
			elementList.add(A);
			elementList.add(B);
			elementList.add(C);	
			elementList.add("NO");
		}
		
		noticeService.insertNotice(id,elementList,content);
		return "redirect:/notice";
	}
	
	@RequestMapping(value = "/delete-notice", method = RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public void deleteNotice(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		System.out.println("noticeDelete - POST");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		int num=Integer.parseInt(request.getParameter("noticeNum"));
		noticeService.deleteNotice(num);
	}
	@RequestMapping(value = "/update-notice", method = RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public String updateNotice(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		System.out.println("noticeUpdate - POST");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		int num=Integer.parseInt(request.getParameter("num"));
		String content=request.getParameter("content");
		
		noticeService.updateNotice(num,content);
		
		return "redirect:/notice";
	}
	
	@RequestMapping(value = "/update-notice-form")
	public String updateNoticeForm(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		System.out.println("noticeUpdateForm - GET");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		int num=Integer.parseInt(request.getParameter("num"));
		List<Map<String,Object>> notice=noticeService.selectNotice(num);
		request.setAttribute("notice", notice);
		return "update_notice_form";
		
	}
}
