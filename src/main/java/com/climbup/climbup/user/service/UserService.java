package com.climbup.climbup.user.service;

import com.climbup.climbup.tier.entity.Tier;
import com.climbup.climbup.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getUserById(Long id);

    void setUserTier(Tier tier);
}
