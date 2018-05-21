package com.domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NormallyFileOperateService {

	public File parseMultipartToFile(MultipartFile multipartFile) throws IOException {
	    File convFile = new File(multipartFile.getOriginalFilename());
	    convFile.createNewFile();
	    try ( FileOutputStream fos = new FileOutputStream(convFile) ){
	    	fos.write(multipartFile.getBytes());
	    }
	    return convFile;
	}

}
