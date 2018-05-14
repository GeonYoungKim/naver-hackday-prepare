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
import com.hack.naver.model.Paging;

@Service("NoticeService")
public class NoticeService {
	@Resource(name = "NoticeDao")
	private NoticeDao noticeDao;


	
	private void footerCompareAdd(int a, int b,List<Integer> footerList) {
		if(a<b) {
			footerList.add(a);
		}
	}
	public Map<String, Object> select(int pagingNo, int unit, List<Map<String, Object>> userElement) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> footerList = new ArrayList<Integer>();

		map.put("no", (pagingNo - 1) * unit);
		map.put("unit", unit);
		map.put("userElement", userElement);
		
		List<Map<String, Object>> tableList = (userElement.size()==0)?new ArrayList<Map<String,Object>>():noticeDao.selectPaging(map);

		int footerNo = pagingNo / unit;
		if (pagingNo % 10 == 0) {
			for (int i = ((footerNo - 1) * unit) + 1; i <= (footerNo) * unit; i++) {
				footerCompareAdd(i,tableList.size(),footerList);
			}
		} else {
			for (int i = (footerNo * unit) + 1; i <= (footerNo + 1) * unit; i++) {
				footerCompareAdd(i,tableList.size(),footerList);
			}
		}
		if(tableList.size()==1) {
			footerList.add(1);
		}
		map.put("pagingNo", pagingNo);
		map.put("footerList", footerList);
		map.put("tableList", tableList);

		return map;
	}
	
	public void insertNotice(String id, List<String> elementList, String content) {
		Notice notice = new Notice();
		notice.setId(id);
		notice.setContent(content);
		noticeDao.insertNotice(notice);

		Map<String, Object> map = new HashMap<String, Object>();
		List<NoticeElement> list = new ArrayList<NoticeElement>();
		NoticeElement noticeElement;
		int count = 0;
		for (int i = 0; i < elementList.size(); i++) {
			if (elementList.get(i) != "NO") {
				count++;
				noticeElement = new NoticeElement();
				noticeElement.setNum(notice.getNum());
				noticeElement.setElement(elementList.get(i).toString());
				list.add(noticeElement);
			}
		}
		if (count == 0) {
			return;
		}
		map.put("list", list);
		noticeDao.insertNoticeElement(map);

	}
	public void deleteNotice(int num) {
		noticeDao.deleteNoticeElement(num);
		noticeDao.deleteNotice(num);
		
	}

}
