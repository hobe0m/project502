package org.choongang.file.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final FileInfoService infoService;
    private final HttpServletResponse response;

    public void download(Long seq) {
        FileInfo data = infoService.get(seq);
        String filePath = data.getFilePath();

        // 파일명 -> 2바이트 인코딩으로 변경(윈도우즈 시스템에서 한글 깨짐 방지)
        String fileName = null;

        try {
            fileName = new String(data.getFileName().getBytes(), "ISO8859_1");
        } catch (UnsupportedEncodingException e) { }

        File file = new File(filePath);

        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
             OutputStream out = response.getOutputStream(); // 응답 Body에 출력
             
             // 파일 저장 시 파일 이름으로 저장
             response.setHeader("Content-Disposition", "attachment; filenamt = "
             + fileName);
             
             // 컨텐츠의 타입을 바이너리 타입(범용적인 타입)으로 설정, 구체적인 파일이 아니기 때문
             response.setHeader("Content-type", "application/octet-stream");

             // 만료 시간 X(만료 시간이 없음, 파일 용량이 큰 경우 다운로드를 위해서, 시간이 오래 걸려도 다운)
             response.setIntHeader("Expires", 0);

             // 파일명이 같더라도 동일한 자원인지 항상 확인하고, 동일한 자원이 있으면 캐시에 있는 것 사용한다.
             response.setHeader("Cache-Control", "must-revalidate");
             response.setHeader("Pragma", "public");

             // 파일 용량에 대한 정보
             response.setHeader("Content-length", String.valueOf(file.length()));

             while (bis.available() > 0) {
                 out.write(bis.read());
             }

             out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}