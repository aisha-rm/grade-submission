package com.ltp.gradesubmission.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.AllArgsConstructor;

import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http        
            .headers().frameOptions().disable() // spring Security prevents h2 rendering within an iframe. disables its prevention.
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/h2/**").permitAll() // allows access the h2 console without the need to authenticate. ' ** '  instead of ' * ' because multiple path levels will follow /h2.
            .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll() //all all ost requests to path register
            .anyRequest().authenticated() //all other requests need authentication
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
    
}