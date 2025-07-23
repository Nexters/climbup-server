package com.climbup.climbup.user.service;

import com.climbup.climbup.tier.entity.Tier;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.user.exception.UserOnboardingAlreadyCompleteException;
import com.climbup.climbup.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void setUserTier(Tier tier){
        Long userId = 1L;

        User user = this.getUserById(userId);

        if(user.getOnboardingCompleted()){
            throw new UserOnboardingAlreadyCompleteException("유저가 온보딩을 이미 완료했습니다.");
        }

        user.completeOnboarding(tier);
        userRepository.save(user);
    }
}
