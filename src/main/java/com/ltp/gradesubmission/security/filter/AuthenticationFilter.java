package com.ltp.gradesubmission.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltp.gradesubmission.entity.User;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
     throws AuthenticationException {
        try{
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        //retrieve body of request by input stream,
        //deserialize the json into a java user object.readValue wh expects the two
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
        } catch (IOException e) {
            throw new RuntimeException();   //if invalid details in request, so 400 response is sent
        }
        
        
        return super.attemptAuthentication(request, response);
    }
}
