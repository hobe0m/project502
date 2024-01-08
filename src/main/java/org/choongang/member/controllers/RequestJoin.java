package org.choongang.member.controllers;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestJoin {
    @NotBlank @Email
    private String email;
    
    // 값이 없으면 기본값으로 랜덤한 유니크 아이디 만들기
    // UUID(Universally Unique Identifier)
    // Universally : 전세계적으로, 따라서 전세계적으로 유일한 식별자라는 뜻
    private String gid = UUID.randomUUID().toString();
    
    @NotBlank
    @Size(min=6)
    private String userId;

    @NotBlank
    @Size(min=8)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String name;

    @AssertTrue
    private boolean agree;

}