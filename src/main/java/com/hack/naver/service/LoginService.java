package com.hack.naver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.stereotype.Service;

import com.hack.naver.dao.LoginDao;
import com.hack.naver.model.User;

@Service("LoginService")
public class LoginService {
	@Resource(name = "LoginDao")
	private LoginDao loginDao;

	private void insertUserElement(String id,List elementList) {
	
		Map<String, Object> map = new HashMap<String,Object>();
		List<User> list=new ArrayList<User>();
		User user;
		int count=0;
		for(int i=0;i<elementList.size();i++) {
			if(elementList.get(i)!="NO"){
				count++;
				user=new User();
				user.setId(id);
				user.setElement(elementList.get(i).toString());
				list.add(user);
			}
		}
		if(count==0) {
			return;
		}
		map.put("list", list);
		loginDao.insertUserElement(map);
	}
	
	public void login(String id,List elementList) {
		
		Map<String,Object> selectUser=loginDao.selectOneUser(id);
		if(selectUser!=null) {
			loginDao.deleteUserElement(id);
			insertUserElement(id,elementList);
		}else {
			loginDao.insertOneUser(id);
			insertUserElement(id,elementList);
		}
	}
}
