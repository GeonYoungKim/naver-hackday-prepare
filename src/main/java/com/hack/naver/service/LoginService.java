package com.hack.naver.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hack.naver.dao.LoginDao;
import com.hack.naver.model.User;



@Service("LoginService")
public class LoginService {
	@Resource(name = "LoginDao")
	private LoginDao loginDao;

	public void insertUser(String id, String a, String b, String c) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		List<User> list=new ArrayList<User>();
		User user;
		if(a!=null) {
			user=new User();
			user.setId(id);
			user.setElement("A");
			list.add(user);
		}
		if(b!=null) {
			user=new User();
			user.setId(id);
			user.setElement("B");
			list.add(user);
		}
		if(c!=null) {
			user=new User();
			user.setId(id);
			user.setElement("C");
			list.add(user);
		}
		map.put("list", list);
		loginDao.insertUser(map);
		
	}
}
