package com.epam.songservice.configuration;

import com.epam.songservice.component.AuthorizationHeaderFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.header.HeaderWriterFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Configuration
@Slf4j
public class SecurityConfigurationNew extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthorizationHeaderFilter filter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
//                .csrf().disable().cors()
//
//                .and()
                .authorizeRequests()
//                .mvcMatchers("/**").permitAll()
//                .antMatchers("/api/v2/api-docs","/v2/api-docs","/api/swagger-resources/**", "/swagger-resources/**", "/api/swagger-ui.html**", "/api/webjars/**").permitAll()
//                .antMatchers("/auth/**").permitAll()
//                .antMatchers("/songs/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        String errorMessage = e.getMessage();

                        log.error(errorMessage, e);

                        new ObjectMapper().writeValue(httpServletResponse.getWriter(), errorMessage);
                    }
                })//check roles
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

                        String errorMessage = e.getMessage();

                        log.error(errorMessage, e);

                        new ObjectMapper().writeValue(httpServletResponse.getWriter(), errorMessage);
                    }
                }) //check autorization //либо authenticationEntryPoint либо loginPage

                .and()
                .oauth2ResourceServer()
                .jwt()
        ;

        http
                .addFilterBefore(filter, HeaderWriterFilter.class);

    }
}