package com.company.shop.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    /*
    * Member 테이블의 id 타입
    * */
    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String username);
}
