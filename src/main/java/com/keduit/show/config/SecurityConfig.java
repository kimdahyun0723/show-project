package com.keduit.show.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        // CSRF 설정
        http.csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); // Cookie에 CSRF 토큰 저장

        System.out.println("------------------------ SecurityFilterChain -----------------------");
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("id")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/members/logout"),
                        new AntPathRequestMatcher("/kakao/logout")
                ))
                .invalidateHttpSession(true)  // 세션 무효화
                .deleteCookies("JSESSIONID")  // JSESSIONID 쿠키 삭제
                .clearAuthentication(true) // 인증 정보도 삭제
                .logoutSuccessUrl("/");

        // permitAll() : 모든 사용자가 인증없이 해당 경로에 접근 가능
        // hasRole("ADMIN") : 관리자의 경우 /admin/로 접근하는 경로를 통과시킴
        // .anyRequest().authenticated() : 위의 경우 이외의 페이지는 인증절차가 필요함
        http.authorizeRequests()
                .mvcMatchers("/", "/members/**", "/item/**",
                        "/profileImages/**", "/images/**", "error", "favicon.ico", "/kakao/**", "/shows/**", "/show/**", "/board/**", "/genreFilter", "/test", "/loadMoreShows").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();




        // 인증되지 않은 사용자가 리소스에 접근하여 실패했을 때 처리하는 헨들러 등록
        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());
        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // /resources/static 폴더의 하위 파일은 인증에서 제외
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}
