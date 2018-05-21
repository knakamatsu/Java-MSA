package com.domain;

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

import com.config.S3config;
import com.model.S3File;

@Service
public class S3FileOperateService {

	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private S3config s3config;
	@Autowired
	private ResourcePatternResolver resolver;

	public Resource[] getS3Resources(Optional<String> searchText) throws IOException{
		String searchFile =  searchText.orElse("*");
		return this.resolver.getResources("s3://"+s3config.getBucketname()+"/**/" + searchFile + "*");
	}

	public Resource getS3Resource(String filename) throws IOException{
		return this.resourceLoader.getResource("s3://"+s3config.getBucketname()+"/" + filename);
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
}
