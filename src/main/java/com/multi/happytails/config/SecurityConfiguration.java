package com.multi.happytails.config;

import com.multi.happytails.authentication.model.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationService authenticationService;

    @Autowired
    public SecurityConfiguration(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/member/login").not().fullyAuthenticated()
                        .requestMatchers("/member/mypage").authenticated()
                        .requestMatchers("/member/kakaoLogin").permitAll()
                        .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/error/**").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
//                        .requestMatchers("/subadmin/**").hasRole("ROLE_SUB_ADMIN")
                        .anyRequest().permitAll()
                );
        http
                .formLogin(form -> form
                        .loginPage("/member/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/member/mypage", true)
                        .failureForwardUrl("/error/login")
//                        .permitAll()
                );
//                .headers(headers -> headers
//                        .frameOptions().sameOrigin());
        http
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .logoutSuccessUrl("/")
                )
                .exceptionHandling(exception -> exception.accessDeniedPage("/error/denied"));
        
        return http.build();
    }
}
