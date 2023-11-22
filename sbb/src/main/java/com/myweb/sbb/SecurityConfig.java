package com.myweb.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //모든 인증되지 않은 요청을 허락 -> 로그인을 하지 않아도 페이지에 접근 가능함
        http
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
            //h2콘솔은 스프링과 상관없는 일반 애플리케이션이기때문에 csrf 토큰을 발행하는 기능이 없으므로 예외 처리해줌
            .csrf((csrf) -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
            //h2콘솔은 frame 구조로 작성되어있기때문에 x-Frame-Options 헤더값을 사용하는 스프링 시큐리티에 의해 오류가 발생
            //헤더값을 sameOrigin으로 설정하여 오류가 발생하지 않도록 해줌
            .headers((headers) -> headers
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
            ;


        return http.build();
    }
}
