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
import com.hack.naver.model.Paging;

@Service("PagingService")
public class PagingService {
	@Resource(name = "PagingDao")
	private PagingDao pagingDao;

	public Map<String, Object> select(int pagingNo,int unit) {
		Paging paging=new Paging();
		Map<String, Object> map=new HashMap<String, Object>();
		List<Integer> footerList=new ArrayList<Integer>();
		
		int footerNo=pagingNo/unit;
		if(pagingNo%10==0) {
			for(int i=((footerNo-1)*unit)+1;i<=(footerNo)*unit;i++){
				footerList.add(i);
			}
		}else {
			for(int i=(footerNo*unit)+1;i<=(footerNo+1)*unit;i++){
				footerList.add(i);
			}	
		}
		paging.setNo((pagingNo-1)*unit);
		paging.setUnit(unit);
		List<Map<String,Object>> tableList=pagingDao.selectPaging(paging);
		
		map.put("pagingNo", pagingNo);
		map.put("footerList", footerList);
		map.put("tableList", tableList);
		
		return map;
	}

}
