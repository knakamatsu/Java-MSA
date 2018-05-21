package com.domain;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

	//Todo
//	public String upload(MultipartFile multipartFile, String fileType) throws IOException {
//		// ファイルが空の場合は異常終了
//		if(multipartFile.isEmpty()){
//			// 異常終了時の処理
//		}
//
//		// ファイル種類から決まる値をセットする
//		StringBuffer filePath = new StringBuffer("/uploadfile")
//				.append(File.separator).append(fileType);   //ファイルパス
//
//		// アップロードファイルを格納するディレクトリを作成する
//		File uploadDir = mkdirs(filePath);
//
//		try {
//			// アップロードファイルを置く
//			File uploadFile = new File(uploadDir.getPath() + "/" + fileType);
//			byte[] bytes = multipartFile.getBytes();
//			BufferedOutputStream uploadFileStream =
//					new BufferedOutputStream(new FileOutputStream(uploadFile));
//			uploadFileStream.write(bytes);
//			uploadFileStream.close();
//
//			return "You successfully uploaded.";
//		} catch (Exception e) {
//			// 異常終了時の処理
//			return "You successfully uploaded.";
//		} catch (Throwable t) {
//			// 異常終了時の処理
//			return "You successfully uploaded.";
//		}
//	}
//
//	/**
//	 * アップロードファイルを格納するディレクトリを作成する
//	 *
//	 * @param filePath
//	 * @return
//	 */
//	private File mkdirs(StringBuffer filePath){
//		Date now = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		File uploadDir = new File(filePath.toString(), sdf.format(now));
//		// 既に存在する場合はプレフィックスをつける
//		int prefix = 0;
//		while(uploadDir.exists()){
//			prefix++;
//			uploadDir = new File(filePath.toString() + sdf.format(now) + "-" + String.valueOf(prefix));
//		}
//
//		// フォルダ作成
//		uploadDir.mkdirs();
//
//		return uploadDir;
//	}

}