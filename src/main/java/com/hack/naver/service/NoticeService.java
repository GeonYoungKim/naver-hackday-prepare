package com.hack.naver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hack.naver.dao.NoticeDao;
import com.hack.naver.model.Notice;
import com.hack.naver.model.NoticeElement;

@Service("NoticeService")
public class NoticeService {
	@Resource(name = "NoticeDao")
	private NoticeDao noticeDao;

	public Map<String, Object> select(int pagingNo, int unit, List<Map<String, Object>> userElement,String id) {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> footerList = new ArrayList<Integer>();

		//int count= noticeDao.selectLogoutCount(id);
		System.out.println(pagingNo+"="+unit);
		
		
		map.put("no", (pagingNo - 1) * unit);
		map.put("unit", unit);
		map.put("userElement", userElement);
		map.put("id",id);
		List<Map<String, Object>> tableList = (userElement.size() == 0) ? new ArrayList<Map<String, Object>>() : noticeDao.selectPaging(map);
		Map<String, Object> allMap = (userElement.size() == 0) ? new HashMap<String, Object>() : noticeDao.selectAllPaging(map);

		System.out.println(allMap);
		if((Long)allMap.get("cnt")>(Integer)allMap.get("count")) {
			map.put("count", (Long) allMap.get("cnt"));
			noticeDao.updateCount(map);
			map.put("alarm", "YES");
		}else {
			map.put("alarm", "NO");
		}
		
		long allNo = ((Long) allMap.get("cnt")) / unit;
		
		int footerNo = pagingNo / 10;
		if((pagingNo%10)==0) {
			System.out.println("10단위 클릭");
			for(int i=(((footerNo-1)*10)+1);i<=(footerNo*10)&& i <= (allNo+1) ;i++) {
				footerList.add(i);
			}
		}else {
			for(int i=((footerNo*10)+1);i<=((footerNo+1)*10)&& i <= (allNo+1);i++) {
				footerList.add(i);
			}
		}

		map.put("allNo", allNo + 1);
		map.put("pagingNo", pagingNo);
		map.put("footerList", footerList);
		map.put("tableList", tableList);

		return map;
	}

	public String insertNotice(String id, List<String> elementList, String content) {
		Notice notice = new Notice();
		notice.setId(id);
		notice.setContent(content);
		noticeDao.insertNotice(notice);

		Map<String, Object> map = new HashMap<String, Object>();
		List<NoticeElement> list = new ArrayList<NoticeElement>();
		NoticeElement noticeElement;

		for (int i = 0; i < elementList.size(); i++) {
			if (!(elementList.get(i).equals("NO"))) {

				noticeElement = new NoticeElement();
				noticeElement.setNum(notice.getNum());
				noticeElement.setElement(elementList.get(i).toString());
				list.add(noticeElement);
			}
		}
		noticeElement = new NoticeElement();
		noticeElement.setNum(notice.getNum());
		noticeElement.setElement("NO");
		list.add(noticeElement);

		map.put("list", list);
		noticeDao.insertNoticeElement(map);
		noticeDao.updateUserCount(id);
		return notice.getNum() + "";
	}

	public void deleteNotice(int num) {
		noticeDao.deleteNoticeElement(num);
		noticeDao.deleteNotice(num);
	}

	public List<Map<String, Object>> selectNotice(int num) {
		return noticeDao.selectNotice(num);
	}

	public void updateNotice(int num, String content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", num);
		map.put("content", content);
		noticeDao.updateNotice(map);
	}

	public Map<String, Object> selectOneNotice(String num) {
		Map<String, Object> map = new HashMap<String, Object>();

		int noticeNum = Integer.parseInt(num);
		List<Map<String, Object>> notice = noticeDao.selectNotice(noticeNum);
		List<Map<String, Object>> file = noticeDao.selectFiles(noticeNum);
		map.put("notice", notice);
		map.put("file", file);
		return map;

	}

	public List<Map<String, Object>> selectNoticeFiles(int num) {
		return noticeDao.selectNoticeFiles(num);
	}
}
