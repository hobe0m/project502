package org.choongang.file.service;

import lombok.RequiredArgsConstructor;
import org.choongang.configs.FileProperties;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.repositories.FileInfoRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(FileProperties.class)
public class FileUploadService {

    private final FileProperties fileProperties;
    private final FileInfoRepository repository;

    public List<FileInfo>
    upload(MultipartFile[] files, String gid, String location) {
        /**
         * 1. 파일 정보 저장
         * 2. 서버쪽에 파일 업로드 처리
         */

        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();

        String uploadPath = fileProperties.getPath(); // 파일 업로드 시 기본 경로

        for (MultipartFile file : files) {

            /* 파일 정보 저장 S */
            String fileName = file.getOriginalFilename(); // 업로드 시 원파일명
            // 파일명.확장자 image.png, image.1.png
            // subString(파일명.indexOf("끊고 싶은 위치(찾고 싶은)") : 문자열 찾기
            // subString(파일명.lastIndexOf("끊고 싶은 위치(찾고 싶은)") : 역순으로 문자열 찾기
            // 확장자
            String extension = fileName.substring(fileName.lastIndexOf("."));


            String fileType = file.getContentType();
            // getContentType()을 통해 파일의 종류를 알 수 있음, img, png...
            FileInfo fileInfo = FileInfo.builder()
                    .gid(gid)
                    .location(location)
                    .filename(fileName)
                    .extension(extension)
                    .filetype(fileType)
                    .build();

            repository.saveAndFlush(fileInfo);
            /* 파일 정보 저장 E */
            
            /* 파일 업로드 처리 S */

            long seq = fileInfo.getSeq();
            File dir = new File(uploadPath + (seq % 10));
            if(!dir.exists()) { // 디렉토리가 없으면 생성
                dir.mkdir();
            }

            File uploadFile = new File(dir, seq + "." + extension); // 증감번호.확장자 형태
            try {
                file.transferTo(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* 파일 업로드 처리 E */
        }
        return null; // 임시
    }
}