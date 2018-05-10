package com.hack.naver.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hack.naver.dao.PagingDao;

@Service("PagingService")
public class PagingService {
	@Resource(name = "PagingDao")
	private PagingDao pagingDao;

	public Map<String, Object> select(int pagingNo) {
		Map<String, Object> map=new HashMap<String, Object>();
		List<Integer> footerList=new ArrayList<Integer>();
		
		int footerNo=pagingNo/10;
		if(pagingNo%10==0) {
			for(int i=((footerNo-1)*10)+1;i<=(footerNo)*10;i++){
				footerList.add(i);
			}
		}else {
			for(int i=(footerNo*10)+1;i<=(footerNo+1)*10;i++){
				footerList.add(i);
			}	
		}
		
		List<Map<String,Object>> tableList=pagingDao.selectPaging((pagingNo-1)*10);
		
		map.put("pagingNo", pagingNo);
		map.put("footerList", footerList);
		map.put("tableList", tableList);
		
		return map;
	}

}
