package com.sivalabs.techbuzz.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] PUBLIC_RESOURCES = {
        "/webjars/**",
        "/resources/**",
        "/static/**",
        "/assets/**",
        "/favicon.ico",
        "/",
        "/error",
        "/403",
        "/404",
        "/login",
        "/registration",
        "/registrationStatus",
        "/verifyEmail",
        "/c/**"
    };

    private final OAuth2AuthenticationSuccessHandler authenticationSuccessHandler;

    private final CustomOAuth2UserService oauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers(PUBLIC_RESOURCES)
                .permitAll()
                .anyRequest()
                .authenticated();

        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .logoutSuccessUrl("/");

        http.oauth2Login()
                .successHandler(authenticationSuccessHandler)
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauth2UserService);

        return http.build();
    }
}
