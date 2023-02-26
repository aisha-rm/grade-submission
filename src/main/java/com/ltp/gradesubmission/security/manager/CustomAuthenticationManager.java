package com.ltp.gradesubmission.security.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private UserService userServiceImpl;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userServiceImpl.getUser(authentication.getName()); //get the user using the auth obj that was sent
        if (!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {   //check if the password/credential in auth obj sent matched the user password on storage
            throw new BadCredentialsException("wrong password");   //if no match
        }
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
    }
    
}
