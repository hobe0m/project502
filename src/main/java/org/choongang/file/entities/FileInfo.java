package org.choongang.file.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.commons.entities.BaseMember;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "idx_fInfo_gid", columnList = "gid"),
        @Index(name = "idx_fInfo_gid", columnList = "gid,location")
})
public class FileInfo extends BaseMember {

    @Id
    @GeneratedValue
    private Long seq; // 파일 등록 번호, 서버에 업로드 하는 파일명 기준

    @Column(length = 65, nullable = false)
    private String gid = UUID.randomUUID().toString(); // 랜덤한 유니크(고유의) 아이디를 중복되지 않게 문자열로 만든다.

    @Column(length = 65)
    private String location; // 하나의 그룹 안에서 위치를 확인해 그룹 내 파일들을 구분할 수 있게 해준다.

    @Column(length = 80) // length 80이면 한글은 26자 정도(1/3)
    private String fileName; // 원래 올렸던 파일의 이름

    @Column(length = 30)
    private String extension; // 확장자

    @Column(length = 65)
    private String filetype; // 파일 타입

    @Transient
    private String filePath; // 서버에 실제로 올라간 경로
    
    private String  fileUrl; // 브라우저 주소창에 입력해서 접근할 수 있는 경로

    @Transient
    private List<String> thumbsPath; // 썸네일 이미지 경로

    @Transient
    private List<String> thumbsUrl; // 브라우저 주소창에 입력해서 접근할 수 있는 경로


    private boolean done; // 작업의 완료 상태를 확인(안쓰는 건 삭제, 완료한 건 올리기)할 수 있게 해준다.

}