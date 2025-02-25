package com.company.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    csrf on 추가
//    @Bean
//    public CsrfTokenRepository csrfTokenRepository() {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//        repository.setHeaderName("X-XSRF-TOKEN");
//        return repository;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // off
        http.csrf((csrf) -> csrf.disable());
        // csrf 기능 on
//        http.csrf(csrf -> csrf.csrfTokenRepository(csrfTokenRepository())
//                .ignoringRequestMatchers("/login")
//        );
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll()
        );
        /* form login */
        http.formLogin((formLogin) -> formLogin.loginPage("/login")
                .defaultSuccessUrl("/")
//                .failureUrl("/fail") 실패시 보내는 url
        );

        http.logout(logout -> logout.logoutUrl("/logout"));

        return http.build();
    }
}