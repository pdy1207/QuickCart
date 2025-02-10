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
    private final MemberRepository memberRepository;

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

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser(){
        var a = memberRepository.findById(1L);
        var result = a.get();
        /*
        * 패스워드 없이 보내기
        * */
//        var map = new HashMap<>();
//        map.put(); Map 형식

//        var data =new DataDto();
//        data.username = result.getUsername();
//        data.displayName = result.getDisplayName(); DTO 형식 object

        var data = new MemberDto(result.getUsername(), result.getDisplayName());
        return data;
    }
}

// DTO 는 별 다른 파일로 옮기는게 좋고, Getter를 사용하면 public 없어도 됨.
// 보내는 데이터의 타입체크가 쉽다.
// 재사용이 쉽다.
class MemberDto { // Data Transfer Object(데이터 변환용 클래스)
    public String username;
    public String displayName;
    public Long Id;
    // constructor
    MemberDto(String a, String b){
        this.username = a;
        this.displayName = b;
    }
}
