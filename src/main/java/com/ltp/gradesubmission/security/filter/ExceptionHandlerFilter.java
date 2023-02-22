package com.ltp.gradesubmission.security.filter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ExceptionHandlerFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {
            try{
                filterChain.doFilter(request, response);
            } catch (RuntimeException e) {//Can have as many catch blocks as  needed
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //can add json body to the response as follows of leave it here
                response.getWriter().write("BAD REQUEST");
                response.getWriter().flush();
            }

         }
   
}
