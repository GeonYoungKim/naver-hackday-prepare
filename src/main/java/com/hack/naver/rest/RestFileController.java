package com.hack.naver.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hack.naver.service.FileService;

@RestController
public class RestFileController {
	@Resource(name = "FileService")
	private FileService fileService;

	@PostMapping("/file/upload")
	public void upload(MultipartHttpServletRequest mHttpServletRequest, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
		System.out.println("upload");

		String fileRoute;
		String noticeNum = mHttpServletRequest.getParameter("noticeNum");
		List<MultipartFile> fileList = mHttpServletRequest.getFiles("file");

		for (MultipartFile multipartFile : fileList) {
			if (!multipartFile.isEmpty()) {
				String orginFileName = new String(multipartFile.getOriginalFilename().getBytes("8859_1"), "UTF-8");
				if (orginFileName.endsWith(".png") || orginFileName.endsWith(".xslx") || orginFileName.endsWith(".xls")
						|| orginFileName.endsWith(".txt") || orginFileName.endsWith(".csv")
						|| orginFileName.endsWith(".tsv ")) {
					String rootPath = "C:\\Users\\c2619\\Desktop\\gunyoungkim";
					File dir = new File(rootPath + File.separator + "naverFiles");
					if (!dir.exists()) {
						dir.mkdirs();
					}

					File serverFile = new File(dir.getAbsolutePath() + File.separator + orginFileName);
					fileRoute = dir.getAbsolutePath() + File.separator + orginFileName;
					try {
						try (InputStream is = multipartFile.getInputStream();
								BufferedOutputStream stream = new BufferedOutputStream(
										new FileOutputStream(serverFile))) {
							int i;
							while ((i = is.read()) != -1) {
								stream.write(i);
							}
							stream.flush();
							fileService.insertFile(noticeNum, fileRoute);
						}
					} catch (IOException e) {
						System.out.println("error : " + e.getMessage());
					}
				}
			}
		}
	}
	@GetMapping("/file/download/{fileNum}")
	public void download(HttpServletRequest request, HttpServletResponse response,@PathVariable("fileNum") String fileNum) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		System.out.println("file download controller");

		Map<String, Object> fileMap = fileService.selectOneFile(Integer.parseInt(fileNum));
		String route = fileMap.get("file_route").toString();

		String downloadFileName = route.substring(route.lastIndexOf("\\") + 1);
		System.out.println(downloadFileName);
		InputStream in = null;
		OutputStream os = null;
		File file = null;
		boolean skip = false;
		String client = "";
		try {
			// 파일을 읽어 스트림에 담기
			try {
				file = new File(route);
				in = new FileInputStream(file);
			} catch (FileNotFoundException fe) {
				skip = true;
			}
			client = request.getHeader("User-Agent");

			// 파일 다운로드 헤더 지정
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "JSP Generated Data");

			if (!skip) {
				// IE
				if (client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition",
							"attachment; filename=" + new String(downloadFileName.getBytes("KSC5601"), "ISO8859_1"));

				} else {
					// 한글 파일명 처리
					downloadFileName = new String(downloadFileName.getBytes("utf-8"), "iso-8859-1");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFileName + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				}

				response.setHeader("Content-Length", "" + file.length());

				os = response.getOutputStream();
				byte b[] = new byte[(int) file.length()];
				int leng = 0;

				while ((leng = in.read(b)) > 0) {
					os.write(b, 0, leng);
				}

			} else {
				response.setContentType("text/html;charset=UTF-8");
			}
			in.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@PostMapping("/file/delete/{num}")
	public void deleteFile(HttpServletRequest request, HttpServletResponse response,@PathVariable("num") int num) throws UnsupportedEncodingException {
		System.out.println("noticeUpdate - POST");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		fileService.deleteFile(num);
	}
	
	
}
