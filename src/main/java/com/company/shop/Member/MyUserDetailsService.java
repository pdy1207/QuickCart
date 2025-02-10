package com.company.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor // memberRepository 가져오기
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        DB에서 username을 가진 유저를 찾아와서
//        return new User(유저아이디, 비번, 권한) 해주세요
        var result = memberRepository.findByUsername(username);
        System.out.println(result.get().getUsername());

        if(result.isEmpty()){
            throw  new UsernameNotFoundException("해당하는 아이디가 존재하지 않습니다.");
        }
        var user = result.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("일반유저"));
        // 관리자로서, 제한 가능

        var a = new CustomUser(user.getUsername(),user.getPassword(),authorities);
        a.displayName = user.getDisplayName();
//                new User(user.getUsername(),user.getPassword(),authorities);

        return a;
    }

}


class CustomUser extends User{
    public String displayName;
    public CustomUser(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, password, authorities);
    }
}
