package com.umc.DongnaeFriend.config;


import com.umc.DongnaeFriend.global.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").permitAll() // 인증 없이 접근 허용하는 URL
                .antMatchers("/user/reissuance").permitAll() // 인증 없이 접근 허용하는 URL
                .antMatchers("/kakao").permitAll() // 카카오 토큰 추출(임시)
                .antMatchers("/callback").permitAll() // 카카오 토큰 추출(임시)
                .anyRequest().authenticated(); // 그 외의 URL은 인증 필요
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // 나머지 코드는 이전 예제와 동일
}
