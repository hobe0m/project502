package org.choongang.member.service;

import lombok.Builder;
import lombok.Data;
import org.choongang.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String userId;
    private String password;
    private Member member; // 추가적으로 필요한 항목이 있을 때 참조한다.

    private Collection<? extends GrantedAuthority> authorities;

    // 권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    // 비밀번호
    @Override
    public String getPassword() {
        return password;
    }

    // 아이디
    @Override
    public String getUsername() {
        return StringUtils.hasText(email) ? email : userId; // 1) email이 있으면, email로 하고 없으면 2) userId로 한다.
    }

    // 계정 만료 확인
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 확인
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 비밀번호 만료 확인
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 탈퇴 기능
    @Override
    public boolean isEnabled() {
        return true;
    }
}
