package com.company.shop.Member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity // 테이블 생성
@ToString
@Getter // private 가져다 사용하기 위한 getter setter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // 중복 허용 x
    private String username;
    private String password;
    private String displayName;

    // 기본 생성자 (필수)
    protected Member() {}


    public Member(String displayName, String username, String password) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
    }
}
