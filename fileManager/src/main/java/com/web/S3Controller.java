package com.web;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/s3")
public class S3Controller {

	@Autowired
	private S3Service s3Service;

	@RequestMapping(method=RequestMethod.GET)
	public String files() throws IOException {
		return s3Service.getFileList();
	}

	@RequestMapping(value="/download", method=RequestMethod.GET)
	public void download(@RequestParam String filename) throws IOException {
		s3Service.download(filename);
	}

	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public void upload(@RequestParam String filename) throws IOException {
		s3Service.upload(filename);
	}

	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search() throws IOException {
		return s3Service.search();
	}

}