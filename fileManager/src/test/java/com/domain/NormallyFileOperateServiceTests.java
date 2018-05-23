package com.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
public class NormallyFileOperateServiceTests {

	@InjectMocks
	private NormallyFileOperateService service;

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();
	@Rule
	public TemporaryFolder f = new TemporaryFolder();

	@Test
	public void parseMultipartToFile() throws IOException {

		File expect = f.newFile();
		MockMultipartFile m = new MockMultipartFile("file", expect.getPath(), "multipart/form-data", FileUtils.openInputStream(expect) );

		File acutal = service.parseMultipartToFile(m);
		assertThat(acutal, is(expect));
	}

}
