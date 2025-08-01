package com.climbup.climbup.user.service;

import com.climbup.climbup.user.dto.response.UserStatusResponse;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.user.exception.UserNotFoundException;
import com.climbup.climbup.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    @Transactional(readOnly = true)
    public UserStatusResponse getUserStatus(Long userId) {
        User user = getUserById(userId);
        return UserStatusResponse.fromEntity(user);
    }
}