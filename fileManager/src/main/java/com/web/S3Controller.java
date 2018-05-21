package com.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.domain.S3Service;
import com.model.S3File;

@RestController
@RequestMapping("/s3")
public class S3Controller {

	@Autowired
	private S3Service service;

	@RequestMapping(method=RequestMethod.GET)
	public List<S3File> getFileList(Optional<String> searchText) throws IOException {
		return service.getFileList(searchText);
	}

	@RequestMapping(value="/pathList", method=RequestMethod.GET)
	public List<?> tree(Optional<String> searchText) throws IOException {
		return service.getPathList(searchText);
	}

	@RequestMapping(value="/download", method=RequestMethod.GET)
	public ResponseEntity<byte[]>  download(@RequestParam String filename) throws IOException {
		return service.download(filename);
	}

	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public void upload(@RequestParam MultipartFile multipartFile,@RequestParam Optional<String> uploadDir) throws IOException {
		service.upload(multipartFile, uploadDir);
	}
}