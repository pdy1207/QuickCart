package com.company.shop.Member;

import com.company.shop.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveMember(Map<String, String> formData) {
        if (formData == null || !formData.containsKey("displayName") || !formData.containsKey("username") || !formData.containsKey("password")) {
            throw new IllegalArgumentException("필수 값(displayName, username,password)이 누락되었습니다.");
        }

        String displayName = formData.get("displayName");

        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("displayName 값이 비어있습니다.");
        }

        String username = formData.get("username");
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("username 값이 비어있습니다.");
        }

        // 🔹 username 중복 체크
        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("이미 존재하는 username 입니다.");
        }

        String password = formData.get("password");
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("password 값이 비어있습니다.");
        }

        String encryptedPassword = passwordEncoder.encode(password);

        memberRepository.save(new Member(displayName, username, encryptedPassword));
    }

}
