package com.domain;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import com.amazonaws.util.IOUtils;
import com.model.S3File;

@SpringBootTest
public class S3ServiceTests {

	@Mock
	private S3FileOperateService operator;

	@InjectMocks
	private S3Service service;

    @Rule
    public MockitoRule mockito = MockitoJUnit.rule();

    @Rule
	public TemporaryFolder t = new TemporaryFolder();

	@Test
	public void getFileListTest() throws IOException{

		//setup
		S3File expect = new S3File();
		expect.setName("testing");
		List<S3File> expectList = Arrays.asList(expect);
		when(operator.resourcesConvrtToS3(any()) )
		.thenReturn(expectList);

		//run
		List<S3File> acutal = service.getFileList(any());

		//verify
		assertThat("is match return list", acutal, is(expectList));
		verify(operator, times(1)).getS3Resources(any());
		verify(operator, times(1)).resourcesConvrtToS3(any());
	}

	@Test
	public void downloadTest() throws IOException {

		//setup
		Resource resource = new FileSystemResource(t.newFile().getPath());
		byte[] expect = IOUtils.toByteArray(resource.getInputStream());
		when(operator.getS3Resource(any())).thenReturn(resource);

		//run
		ResponseEntity<byte[]> actualRes = service.download(any());
		byte[] acutal = actualRes.getBody();

		//verify
		assertThat(acutal, is(expect));
		verify(operator, times(1)).getS3Resource(any());
	}

	@Test
	public void uploadTest() throws IOException {
		service.upload(any(), any());

		verify(operator, times(1)).uploadToS3(any(),any());
	}

	@Test
	public void getPathListTest() throws IOException {

		//setup
		S3File s = new S3File();
		S3File s2 = new S3File();
		s.setDownloadPath("testing");
		s2.setDownloadPath("test/example.txt");
		List<S3File> expectList = Arrays.asList(s,s2);
		List<String> expect = Arrays.asList("testing","test/example.txt");

		when(operator.resourcesConvrtToS3(any()) )
		.thenReturn(expectList);

		//run
		List<String> acutal = service.getPathList(any());

		//verify
		assertThat("is match return list", acutal, is(expect));
		verify(operator, times(1)).getS3Resources(any());
		verify(operator, times(1)).resourcesConvrtToS3(any());

	}

	@Test
	public void delete() throws IOException {
		service.delete(any());
		verify(operator, times(1)).delete(any());

	}
}