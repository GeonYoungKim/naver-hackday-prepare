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

	private void insertUserElement(String id, List elementList) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<User> list = new ArrayList<User>();
		User user;

		for (int i = 0; i < elementList.size(); i++) {
			if (!(elementList.get(i).equals("NO"))) {
				user = new User();
				user.setId(id);
				user.setElement(elementList.get(i).toString());
				list.add(user);
			}
		}

		user = new User();
		user.setId(id);
		user.setElement("NO");
		list.add(user);

		map.put("list", list);
		loginDao.insertUserElement(map);
	}

	public void login(String id, List elementList) {

		Map<String, Object> selectUser = loginDao.selectOneUser(id);
		if (selectUser != null) {
			loginDao.deleteUserElement(id);
			insertUserElement(id, elementList);
		} else {
			loginDao.insertOneUser(id);
			insertUserElement(id, elementList);
		}
	}

	public List<Map<String, Object>> getUserElement(String userId) {
		return loginDao.getUserElement(userId);
	}

	public String selectOneUserGroup(String myId, String noticeId, Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();

		if (!(myId.equals(noticeId))) {
			for (Object object : params.values()) {
				list.add(object.toString());
			}
			map.put("list", list);
			map.put("id", myId);
			List<Map<String, Object>> result = loginDao.selectOneUserGroup(map);
			if (result.size() == 0) {
				return "false";
			} else {
				return "true";
			}
		} else {
			return "false";
		}
	}
}
