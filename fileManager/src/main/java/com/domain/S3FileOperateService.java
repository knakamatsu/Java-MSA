package com.domain;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.config.S3config;
import com.model.S3File;

@Service
public class S3FileOperateService {

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

	@Autowired
	private S3config s3config;
	@Autowired
	private NormallyFileOperateService fileService;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private ResourcePatternResolver resolver;
	@Autowired
	private AmazonS3 amazonS3;

	public Resource[] getS3Resources(Optional<String> searchText) throws IOException{
		String searchFile =  searchText.orElse("*");
		return this.resolver.getResources("s3://"+s3config.getBucketname()+"/**/*" + searchFile + "*");
	}

	public Resource getS3Resource(String filename) throws IOException{
		String objKey = filename.startsWith("/") ? filename : "/" + filename;
		String resourceKey = "s3://" + s3config.getBucketname() + objKey;
		return this.resourceLoader.getResource(resourceKey);
	}

	public List<S3File>  resourcesConvrtToS3(Resource[] resources) throws IOException{
		return resourcesConvrtToS3(Arrays.asList(resources));
	}

	public List<S3File>  resourcesConvrtToS3(List<Resource> resources) throws IOException{
		List<S3File> fileList = new ArrayList<>();
		for(Resource r : resources){
			S3File f = resourceConvrtToS3(r);
			fileList.add(f);
		}
		return fileList;
	}

	public S3File resourceConvrtToS3(Resource r) throws IOException{
		S3File f = new S3File();
		f.setName(r.getFilename());
		f.setDownloadPath(r.getURL().getPath().replaceAll(s3config.getBucketname() + "/", "") );
		f.setSize(r.contentLength());
		Long size = r.contentLength();
		f.setDisplaySize(FileUtils.byteCountToDisplaySize(size));
		f.setSize(size);
		f.setLastUpdated(sdf.format(new Date(r.lastModified()))); // UNIX時間からの変換
		return f;
	}

	public void uploadToS3(MultipartFile multipartFile, Optional<String> uploadDir) throws IOException {
		File toUploadFile = fileService.parseMultipartToFile(multipartFile);

		String uploadKey = uploadDir
				.map(s -> {
					String sResult;
					if(s.endsWith("/")){
						sResult = s + toUploadFile.getName();
					} else {
						sResult = s + "/" + toUploadFile.getName();
					}
					return sResult;
				})
				.orElse(toUploadFile.getName());


		TransferManager transferManager = new TransferManager(this.amazonS3);
		transferManager.upload(s3config.getBucketname(), uploadKey,toUploadFile);
	}

	public void delete(String filepath) {
		this.amazonS3.deleteObject(s3config.getBucketname(), filepath);
	}

}
