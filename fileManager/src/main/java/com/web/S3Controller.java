package com.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.domain.S3Service;
import com.model.S3File;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Validated
@RestController
@RequestMapping("/s3")
@Api(value="S3 operate API")
public class S3Controller {

	@Autowired
	private S3Service service;

	@ApiOperation(value = "ファイルリスト取得", response = S3File.class)
	@RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
	public List<S3File> getFileList(Optional<String> searchText) throws IOException {
		return service.getFileList(searchText);
	}

	@ApiOperation(value= "S3パスリスト取得")
	@RequestMapping(value="/pathList", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> tree(Optional<String> searchText) throws IOException {
		return service.getPathList(searchText);
	}

	@ApiOperation(value= "ファイルダウンロード")
	@RequestMapping(value="/download", method=RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
	public ResponseEntity<byte[]>  download(@RequestParam @Valid @NotBlank @ApiParam(value="ダウンロード対象ファイル名", example = "test.txt", required = true) String filename) throws IOException {
		return service.download(filename);
	}

	@ApiOperation(value= "ファイルアップロード")
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public void upload(@RequestPart @Valid @NotBlank MultipartFile multipartFile,@RequestParam @ApiParam(value="ダウンロード対象ディレクトリ(パス)") Optional<String> uploadDir) throws IOException {
		service.upload(multipartFile, uploadDir);
	}

	@ApiOperation(value= "ファイル削除")
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public void delete(@RequestParam @ApiParam(value="削除対象ファイル名（フルパス）", example = "test/test.txt", required = true) String filepath) throws IOException {
		service.delete(filepath);
	}
}