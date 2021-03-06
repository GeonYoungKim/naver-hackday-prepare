package com.hack.naver.rest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.hack.naver.service.LoginService;
import com.hack.naver.service.NoticeService;

@RestController
public class RestNoticeController {

	private Gson gson=new Gson();
	
	@Resource(name = "NoticeService")
	private NoticeService noticeService;
	
	@Resource(name = "LoginService")
	private LoginService loginService;

	@PostMapping("/notice/insert")
	public @ResponseBody String insertNotice(@RequestBody Map<String,Object> data,HttpServletRequest request) throws UnsupportedEncodingException {
		System.out.println("noticeInsert - POST");
		request.setCharacterEncoding("UTF-8");
		
		List<String> elementList = new ArrayList<String>();
		
		String id = data.get("userId").toString();
		String content = data.get("content").toString();
		String A = data.get("A").toString();
		String B = data.get("B").toString();
		String C = data.get("C").toString();

		elementList.add(A);
		elementList.add(B);
		elementList.add(C);
		elementList.add("NO");

		String noticeNum=noticeService.insertNotice(id, elementList, content);
		return noticeNum;
	}
	
	@PostMapping("/notice/delete/{noticeNum}")
	public void deleteNotice(HttpServletRequest request, HttpServletResponse response,@PathVariable("noticeNum") int noticeNum) throws UnsupportedEncodingException {
		System.out.println("noticeDelete - POST");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		noticeService.deleteNotice(noticeNum);
	}
	
	@PostMapping("/notice/update/{num}")
	public void updateNotice(@RequestBody Map<String,Object> data,HttpServletRequest request, HttpServletResponse response,@PathVariable("num") int num) throws UnsupportedEncodingException {
		System.out.println("noticeUpdate - POST");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String content = data.get("content").toString();
		String userId=data.get("userId").toString();
		noticeService.updateNotice(num, content);
		
	}
	
	@PostMapping("/notice/select/{num}")
	public @ResponseBody String selectNotice(HttpServletRequest request, HttpServletResponse response,@PathVariable("num") int num) throws UnsupportedEncodingException {
		System.out.println("noticeUpdate - POST");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		List<Map<String,Object>> noticeFiles=noticeService.selectNoticeFiles(num);
		
		
		return gson.toJson(noticeFiles);
	}
}
