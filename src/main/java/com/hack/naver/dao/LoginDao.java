package com.hack.naver.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hack.naver.connect.ConnectDB;
import com.hack.naver.model.User;

@SuppressWarnings("unchecked")
@Repository("LoginDao")
public class LoginDao extends ConnectDB{

	public Map<String,Object> selectOneUser(String id) {
		return (Map<String,Object>)selectOne("user.select_by_id", id);
	}

	public void insertOneUser(String id) {
		insert("user.insertOne", id);
		
	}

	public void insertUserElement(Map<String, Object> map) {
		insert("user_element.insert",map);
		
	}

	public void deleteUserElement(String id) {
		delete("user_element.delete", id);
		
	}

	public List<Map<String, Object>> getUserElement(String userId) {
		return (List<Map<String, Object>>)selectList("user_element.select", userId);
	}
}
