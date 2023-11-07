package com.example.shoppingmall.Service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class FileService {
    //저장할 경로, 파일명, 데이터값
    public String uploadFile(String uploadPath, String originalFileName,
                             byte[] filedata) throws Exception {
        UUID uuid = UUID.randomUUID();  //랜덤 문자열 생성

        String extension = originalFileName.substring(
                originalFileName.lastIndexOf(".")
        );  //확장자 문자열 분리

        String saveFileName = uuid.toString() + extension;  //새로운 파일명

        String uploadFullUrl = uploadPath + saveFileName;  //저장위치 + 파일명

        //하드디스크에 파일 저장
        FileOutputStream fos = new FileOutputStream(uploadFullUrl);
        fos.write(filedata);
        fos.close();

        return saveFileName;  //데이터베이스에 저장할 파일명을 전달
    }

    //파일삭제(상품을 수정시 기존파일을 삭제하고 새로운 파일을 저장)
    public void deleteFile(String uploadPath, String fileName) throws Exception {
        String deleteFileName = uploadPath + fileName;

        File deleteFile = new File(deleteFileName);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }
}
