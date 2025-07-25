package com.climbup.climbup.user.service;

import com.climbup.climbup.user.dto.response.UserStatusResponse;
import com.climbup.climbup.user.entity.User;

public interface UserService {
    User getUserById(Long id);
    UserStatusResponse getUserStatus(Long userId);
}