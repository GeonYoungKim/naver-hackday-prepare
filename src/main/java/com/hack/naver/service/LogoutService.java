package com.hack.naver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hack.naver.model.User;

@Service("LogoutService")
public class LogoutService {

	@Resource(name="LogoutDao")
	private LogoutDao logoutDao;

	public void insertUserCount(String id) {
		List<Map<String,Object>> userElementList=logoutDao.selectUserElement(id);
		System.out.println(userElementList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", id);
		map.put("list", userElementList);
		logoutDao.insertUserCount(map);		
	}	
}
