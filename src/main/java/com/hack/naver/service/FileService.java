package com.hack.naver.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hack.naver.dao.FileDao;

@Service("FileService")
public class FileService {
	@Resource(name="FileDao")
	private FileDao fileDao;

	public void insertFile(String noticeNum, String fileRoute) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("noticeNum", Integer.parseInt(noticeNum));
		map.put("fileRoute", fileRoute);
		
		fileDao.insertFile(map);		
	}
	
	
	
}
