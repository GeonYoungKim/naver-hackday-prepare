package com.hack.naver.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hack.naver.connect.ConnectDB;

@SuppressWarnings("unchecked")
@Repository("FileDao")
public class FileDao extends ConnectDB {

	public void insertFile(Map<String, Object> map) {
		insert("file.insert", map);
	}

	public Map<String, Object> selectOneFile(int fileNum) {		
		return (Map<String, Object>)selectOne("file.selectOneFile", fileNum);
	}

	public void deleteFile(int num) {
		delete("file.delete", num);
	}
}
