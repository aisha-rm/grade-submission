package com.ltp.gradesubmission.security.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.security.SecurityConstants;
import com.ltp.gradesubmission.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    
    
    private CustomAuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
     throws AuthenticationException {
        try{
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
        //retrieve body of request by input stream,
        //deserialize the json into a java user object.readValue wh expects the two
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return authenticationManager.authenticate(authentication);  //return auth obj upon succesful authentication
        } catch (IOException e) {
            throw new RuntimeException();   //if invalid details eg incorrect parameter spelling in request, so 400 response is sent, handled in exception filter
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) 
        throws IOException, ServletException {
        String token = JWT.create()
            .withSubject(authResult.getName())    //username to add to jwt payload
            .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
            .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
            
        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);
        }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) 
        throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    //set status(i.e so 200 not returened)
            response.getWriter().write(failed.getMessage());    //error message from exception thrown in auth manager
            response.getWriter().flush();

        }
}
    