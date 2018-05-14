package com.hack.naver.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hack.naver.connect.ConnectDB;
import com.hack.naver.model.Notice;
import com.hack.naver.model.Paging;
import com.hack.naver.model.User;

@SuppressWarnings("unchecked")
@Repository("NoticeDao")
public class NoticeDao extends ConnectDB{

	public List<Map<String, Object>> selectPaging(Map<String, Object> map) {
		return (List<Map<String, Object>>)selectList("notice.selectList", map);
	}

	public void insertNotice(Notice notice) {		
		insert("notice.insert",notice);		
	}

	public void insertNoticeElement(Map<String, Object> map) {
		insert("notice_element.insert",map);
	}

	public void deleteNoticeElement(int num) {
		delete("notice_element.delete", num);
		
	}

	public void deleteNotice(int num) {
		delete("notice.delete",num);
	}

}
