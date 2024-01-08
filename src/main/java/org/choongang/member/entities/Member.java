package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Member extends Base {
    @Id @GeneratedValue
    private Long seq;

    @Column(length = 65, nullable = false)
    private String gid;

    @Column(length=80, nullable = false, unique = true)
    private String email;

    @Column(length=40, nullable = false, unique = true)
    private String userId;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=40, nullable = false)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Authorities> authorities = new ArrayList<>();

    /**
     * 임시 데이터 또는 계산된 데이터: 데이터베이스에 저장하지 않아도 되는 계산된 값을 나타내는 경우
     * 일시적인 데이터: 특정 시점에만 필요한 데이터로, 데이터베이스에 영구적으로 저장할 필요가 없는 경우
     * 불필요한 데이터: 특정 엔터티의 데이터베이스 매핑에는 필요하지만, 특정 사용 사례에서는 필요하지 않은 데이터
     *
     * 위와 같은 3가지의 경우일 때, Transient 사용!
     */
    @Transient // 이 필드는 데이터베이스에 매핑되지 않음 : 데이터베이스에 저장되지 않고, 데이터베이스에서 로드 및 업데이트 되지 않음
    private FileInfo profileImage;
}