package com.hack.naver.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hack.naver.connect.ConnectDB;
import com.hack.naver.model.User;

@SuppressWarnings("unchecked")
@Repository("LoginDao")
public class LoginDao extends ConnectDB{

	public void insertUser(String id, Map<String, Object> map) {
		
		insert("user.membership", map);
		
	}

	
	

}
