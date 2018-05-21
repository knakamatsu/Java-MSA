package com.domain;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.util.IOUtils;
import com.model.S3File;

@Service
public class S3Service {

	@Autowired
	private S3FileOperateService operator;

	public List<S3File> getFileList(Optional<String> searchText) throws IOException {

		Resource[] resources = operator.getS3Resources(searchText);
		List<S3File> fileList = operator.resourcesConvrtToS3(resources);

		return fileList;
	}

	public ResponseEntity<byte[]> download(String filename) throws IOException {

		Resource resource = operator.getS3Resource(filename);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.add("Content-Disposition", "attachment;filename=\"" + resource.getFilename() +"\"");
		byte[] bytes = IOUtils.toByteArray(resource.getInputStream());

		return new ResponseEntity<>(bytes , headers, HttpStatus.OK );
	}

	public void upload(MultipartFile multipartFile, Optional<String> uploadDir) throws IOException {
		operator.uploadToS3(multipartFile, uploadDir);
	}

	public List<String> getPathList(Optional<String> searchText) throws IOException {
		Resource[] resources = operator.getS3Resources(searchText);
		List<S3File> fileList = operator.resourcesConvrtToS3(resources);
		List<String> pathList = fileList.stream()
		.map(f -> f.getDownloadPath())
		.collect(Collectors.toList());
		return pathList;
	}

	public void delete(String filepath) throws IOException {
		operator.delete(filepath);
	}
}