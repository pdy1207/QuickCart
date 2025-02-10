package com.company.shop.Member;

import com.sun.net.httpserver.Authenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor // MemberService 가져오기 위한 어노테이션
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/register")
    String register(Authentication auth){

        if (auth.isAuthenticated()){
//           로그인 가입 여부
            return "redirect:/list";
        }

        return "register.html";
    }

    @PostMapping("/member")
    @ResponseBody // ✅ JSON 응답을 반환하도록 설정
    public ResponseEntity<Map<String, String>> memberRegister(@RequestBody Map<String, String> formData) {
        Map<String, String> response = new HashMap<>();
        try {
            memberService.saveMember(formData);
            response.put("message", "회원가입 성공");
            response.put("redirectUrl", "/list"); // 성공 시 이동할 URL
            return ResponseEntity.ok(response); // 200 OK 응답
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
        }
    }

    @GetMapping("/login")
    String login(){
        return "login.html";
    }

    @GetMapping("/my-page")
    String myPage(Authentication auth){
//        System.out.println(auth);
//        System.out.println(auth.getName());
//        System.out.println(auth.isAuthenticated());
//        System.out.println(auth.getAuthorities().contains(new SimpleGrantedAuthority("일반유저")));
        /*
        * 권한 확인
        * */
        CustomUser result = (CustomUser) auth.getPrincipal();

        System.out.println(result.displayName);

        return "mypage.html";
    }

}
