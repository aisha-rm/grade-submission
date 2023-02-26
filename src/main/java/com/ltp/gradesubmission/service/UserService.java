package com.ltp.gradesubmission.service;

import org.springframework.stereotype.Service;

import com.ltp.gradesubmission.entity.User;

@Service
public interface UserService {
    User getUser(Long id);
    User getUser(String username);
    User saveUser(User user);
}