package com.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.mock.web.MockMultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.config.S3config;
import com.model.S3File;

@SpringBootTest
public class S3FileOperateServiceTests {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();
	@Rule
	public TemporaryFolder t = new TemporaryFolder();

	@Spy
	@InjectMocks
	private S3FileOperateService service;

	@Mock
	private S3config s3config;
	@Mock
	AmazonS3 amazonS3;
	@Mock
	private NormallyFileOperateService fileService;
	@Mock
	private ResourcePatternResolver resolver;
	@Mock
	private ResourceLoader resourceLoader;
	@Mock
	private TransferManager transferManager;

	@Test
	public void getS3ResourcesTest() throws IOException{

		//setup
		Resource[] expect = {new FileSystemResource(t.newFile().getPath()), new FileSystemResource(t.newFile().getPath())};
		when(resolver.getResources(any())).thenReturn(expect);

		//run
		Resource[] acutal = service.getS3Resources(Optional.of("test"));

		//verify
		assertThat(acutal, is(expect));
		verify(resolver, times(1)).getResources(any());
	}

	@Test
	public void getS3ResourceTest() throws IOException{

		//setup
		Resource expect = new FileSystemResource(t.newFile().getPath());
		when(resourceLoader.getResource(any())).thenReturn(expect);

		//run
		Resource actual = service.getS3Resource("test");

		//verify
		assertThat(actual, is(expect));
		verify(resourceLoader, times(1)).getResource(any());
	}

	@Test
	public void resourcesConvrtToS3Test() throws IOException{

		//setup
		S3File e = new S3File();
		e.setName("testing");
		doReturn(e).when(service).resourceConvrtToS3(any());
		Resource[] r = {new FileSystemResource(t.newFile().getPath())};
		List<S3File> expect = Arrays.asList(e);

		//run
		List<S3File> acutal = service.resourcesConvrtToS3(r);

		//verify
		assertThat(acutal, is( expect ));
		verify(service, times(1)).resourceConvrtToS3(any());
	}

	@Test
	public void resourceConvrtToS3() throws IOException{

		//setup
		Resource r = new FileSystemResource(t.newFile().getPath());
		S3File expect = new S3File();
		expect.setSize(r.contentLength());
		expect.setDisplaySize(FileUtils.byteCountToDisplaySize(r.contentLength()));
		expect.setDownloadPath(r.getURL().getPath());
		expect.setLastUpdated( new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date(r.lastModified())) );
		expect.setName(r.getFilename());

		//run
		S3File acutal = service.resourceConvrtToS3(r);

		//verify
		assertThat(acutal, is( SamePropertyValuesAs.samePropertyValuesAs(expect)  ));
	}

	@Test
	public void uploadToS3Test() throws IOException {

		//setup
		File expect = t.newFile();
		when(fileService.parseMultipartToFile(any())).thenReturn(expect);
		TransferManager mock = mock(TransferManager.class);
		doReturn(mock).when(service).getTransferManager();
		MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", FileUtils.openInputStream(expect) );

		//run
		service.uploadToS3(file, Optional.of("example"));

		//verify
		verify(mock, times(1)).upload(anyString(),eq("example/" + expect.getName()),eq(expect));
	}

	@Test
	public void deleteTest() {

		//run
		service.delete("example");

		//verify
		verify(amazonS3, times(1)).deleteObject(any(),eq("example"));
	}

}
