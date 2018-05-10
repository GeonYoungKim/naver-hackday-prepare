package com.hack.naver.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hack.naver.connect.ConnectDB;
import com.hack.naver.model.User;

@SuppressWarnings("unchecked")
@Repository("PagingDao")
public class PagingDao extends ConnectDB{

	public List<Map<String, Object>> selectPaging(int no) {
		
		return (List<Map<String, Object>>)selectList("paging.selectList", no);
	}

}
