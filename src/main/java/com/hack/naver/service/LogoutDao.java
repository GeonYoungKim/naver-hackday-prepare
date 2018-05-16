package com.hack.naver.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hack.naver.connect.ConnectDB;


@SuppressWarnings("unchecked")
@Repository("LogoutDao")
public class LogoutDao extends ConnectDB{

	public List<Map<String, Object>> selectUserElement(String id) {
		return (List<Map<String, Object>>)selectList("user_element.select", id);
	}

	public void insertUserCount(Map<String, Object> map) {
		selectOne("logout.insertUserCount", map);
		
	}
	
	

}
