package com.evaza.etwitch;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.sql.DataSource;

// if not using the openfeign configuration, comment out the configuration, then the
// query like "http://localhost:8080/game" would jump to "http://localhost:8080/login"
// and showing "Login with OAuth 2.0"
@Configuration
public class AppConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers(HttpMethod.GET, "/", "/index.html", "/*.json", "/*.png", "/static/**").permitAll()
                                .requestMatchers("/hello/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/login", "/register", "/logout").permitAll()
                                .requestMatchers(HttpMethod.GET, "/recommendation", "/game").permitAll()
                                .anyRequest().authenticated()
                )
                // 前端是用 react 写的，前端处理这些logic, 就不用跳转到登陆界面，这里就是禁止跳转到登陆界面
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()

                // Form based authentication (Session-based authentication, 支持用户名-密码登录)
                .formLogin()
                .successHandler((req, res, auth) -> res.setStatus(HttpStatus.NO_CONTENT.value()))
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout()

                // logout 成功之后，也不要自动跳转到登陆界面
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT));
        return http.build();
    }

    // 2 dependencies are provided for Spring Security to use. It can also be used by you
    // "UserDetailsManager" is provided for spring security
    // datasource in yml file is linking your database (to this user-password part) in spring
    @Bean
    UserDetailsManager users(DataSource dataSource) {
        // JDBC knows how to query to your username and password now
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // all kinds of password encoder, be default there is BcryptPasswordEncoder.
        // BcryptPasswordEncoder - 单向加密，程序员不可还原；如果忘记了，不能找回，只能重新设置
        // DelegatingPasswordEncoder - 内部就是 BcryptPasswordEncoder
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
