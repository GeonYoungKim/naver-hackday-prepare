package com.hack.naver.dao;

import java.util.ArrayList;
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
		return (List<Map<String, Object>>)selectList("notice.selectTableList", map);
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

	public List<Map<String, Object>> selectNotice(int num) {
		return (List<Map<String, Object>>)selectList("notice.selectOne",num);
	}

	public void updateNotice(Map<String, Object> map) {
		update("notice.update", map);		
	}

	public Map<String, Object> selectAllPaging(Map<String, Object> map) {
		return (Map<String, Object>)selectOne("notice.selectAllList", map);
	}

	public int selectLogoutCount(String id) {
		return (Integer)selectOne("user.selectUserCount",id);
	}

	public void updateUserCount(String id) {
		update("user.updateCount", id);
	}

	public void updateCount(Map<String, Object> map) {
		update("user.updateLogoutCount", map);		
	}

}
