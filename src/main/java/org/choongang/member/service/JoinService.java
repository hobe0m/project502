package org.choongang.member.service;


import lombok.RequiredArgsConstructor;
import org.choongang.commons.validators.PasswordValidator;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 회원 가입 기능
@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final JoinValidator Validator;
    private final PasswordEncoder encoder;

    public void process(RequestJoin form, Errors errors) {
        Validator.validate(form, errors);
        if (errors.hasErrors()) {
            return;
        }

        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());

        // Member member = new ModelMapper().map(form, Member.class);
        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setName(form.getName());
        member.setPassword(hash); // 위에서 비밀번호를 해시화해놨기 때문에 사용 가능
        member.setUserId(form.getUserId());

        process(member);
    }

    public void process(Member member) {
        memberRepository.saveAndFlush(member);
    }

}
