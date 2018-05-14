package com.hack.naver.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class UploadController {

	// @RequestMapping(value = "/upload-file", method =
	// RequestMethod.POST,produces="text/plain;charset=UTF-8")
	// public void uploadFileHandler(@RequestParam("file") MultipartFile file)
	// throws UnsupportedEncodingException {
	//
	// if (!file.isEmpty()) {
	// String rootPath = "C:\\Users\\c2619\\Desktop\\gunyoungkim";
	// File dir = new File(rootPath + File.separator + "tmpFiles");
	// if (!dir.exists())
	// dir.mkdirs();
	//
	// // Create the file on server
	// String orginFileName = new
	// String(file.getOriginalFilename().getBytes("8859_1"),"UTF-8");
	// File serverFile = new File(dir.getAbsolutePath() + File.separator +
	// orginFileName);
	//
	// try {
	// try (InputStream is = file.getInputStream();
	// BufferedOutputStream stream = new BufferedOutputStream(new
	// FileOutputStream(serverFile))) {
	// int i;
	// while ((i = is.read()) != -1) {
	// stream.write(i);
	// }
	// stream.flush();
	// }
	// } catch (IOException e) {
	// System.out.println("error : " + e.getMessage());
	// }
	// }
	//
	// }
	@RequestMapping(value = "/upload-file", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String uploadFile(MultipartHttpServletRequest mHttpServletRequest) throws UnsupportedEncodingException {
		System.out.println("uploaa");
		List<MultipartFile> fileList = mHttpServletRequest.getFiles("file");

		for (MultipartFile multipartFile : fileList) {
			if (!multipartFile.isEmpty()) {
				String orginFileName = new String(multipartFile.getOriginalFilename().getBytes("8859_1"), "UTF-8");
				if (orginFileName.endsWith(".png") || orginFileName.endsWith(".xslx") || orginFileName.endsWith(".xls")
						|| orginFileName.endsWith(".txt") || orginFileName.endsWith(".csv")
						|| orginFileName.endsWith(".tsv ")) {
					String rootPath = "C:\\Users\\c2619\\Desktop\\gunyoungkim";
					File dir = new File(rootPath + File.separator + "naverFiles");
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server

					File serverFile = new File(dir.getAbsolutePath() + File.separator + orginFileName);

					try {
						try (InputStream is = multipartFile.getInputStream();
								BufferedOutputStream stream = new BufferedOutputStream(
										new FileOutputStream(serverFile))) {
							int i;
							while ((i = is.read()) != -1) {
								stream.write(i);
							}
							stream.flush();
						}
					} catch (IOException e) {
						System.out.println("error : " + e.getMessage());
					}
				}

			}
		}

		return "redirect:/upload-file-form";
	}

	@RequestMapping(value = "/upload-file-form")
	public String uploadFileForm() {
		return "upload_file_form";
	}
}