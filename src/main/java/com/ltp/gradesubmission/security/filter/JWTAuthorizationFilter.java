package com.ltp.gradesubmission.security.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ltp.gradesubmission.security.SecurityConstants;

public class JWTAuthorizationFilter extends OncePerRequestFilter{

    //Authorization : Bearer JWT (i.e expected in JWT header)
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");  //returns bearer JWT
        
        if (header == null || !header.startsWith(SecurityConstants.BEARER)) {
            filterChain.doFilter(request, response);    //redirect to the next filter or user req as this is the last filter
            return; //rest o codes below need not run
        }
        
        String token = header.replace(SecurityConstants.BEARER, "");  //remove the bearer dtrig in front of the token
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))   //verify jwt token using same algo and secret used for authenticating
            .build()
            .verify(token)
            .getSubject();  //getting username from decoded jwt returned by verify above - wh is stored in user var as well
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, (GrantedAuthority[]) Arrays.asList(null)); //null bcoz the token would usually not hold sensitive data
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);    //redirect back SFC, as this is the last filer, the client requests run next

    }
    
}
