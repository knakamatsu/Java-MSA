package com.web;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.config.S3config;

@Service
public class S3Service {

	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private S3config s3config;
	@Autowired
	private ResourcePatternResolver resolver;

	public String getFileList() throws IOException {
		Resource[] resources = this.resolver.getResources("s3://"+s3config.getBucketname()+"/**/*");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < resources.length; i++) {
			if (i!=0) {
				builder.append(", ");
			}
			builder.append(resources[i].toString());
		}
		return builder.toString();
	}


	public void download(String filename) throws IOException {
		Resource resource = this.resourceLoader.getResource("s3://"+s3config.getBucketname()+"/" + filename);
		try ( InputStream input = resource.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input)); ){
			String str = new String();
			StringBuilder builder = new StringBuilder();
			while ((str = reader.readLine()) != null) {
				builder.append(str);
			};
			File file = new File("./download.txt");
			try ( FileWriter writer = new FileWriter(file); ){
				writer.write(builder.toString());
			}
		}
	}

	public void upload(String filename) throws IOException {
		File file = new File("アップロードするファイルのディレクトリ" + filename);
		FileInputStream input = new FileInputStream(file);

		try ( BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
			String str = new String();
			StringBuilder builder = new StringBuilder();
			while ((str = reader.readLine()) != null) {
				builder.append(str);
			};

			Resource resource = this.resourceLoader.getResource("s3://"+s3config.getBucketname()+"/" + filename );
			WritableResource writableResource = (WritableResource) resource;
			try (OutputStream output = writableResource.getOutputStream();) {
				output.write(builder.toString().getBytes());
			}
		}
	}

	public String search() throws IOException {
		Resource[] resources = this.resolver.getResources("s3://"+s3config.getBucketname()+"/**/*.txt");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < resources.length; i++) {
			if (i!=0) {
				builder.append(", ");
			}
			builder.append(resources[i].toString());
		}

		return builder.toString();
	}

}