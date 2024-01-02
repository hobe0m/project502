package org.choongang.member.repositories;

import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member> {

    // Optinal : 값이 존재할 수도 있고, 존재하지 않을 수도 있는 상황에서 사용
    // 'null'을 다루는 데 유용한 방법 제공, 값의 존재 여부를 나타내는 래퍼(wrapper) 역할 수행
    // 반환 타입이 Optinal<Member>로 설정되어 있다
    Optional<Member> findByEmail(String email);
    Optional<Member> findByUserId(String userId);

    default boolean existsByEmail(String email) {
        QMember member = QMember.member;

        return exists(member.email.eq(email));
    }

    default boolean existsByUserId(String userId) {
        QMember member = QMember.member;

        return exists(member.userId.eq(userId));
        // eq = equals
    }
}
