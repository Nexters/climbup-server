package com.climbup.climbup.user.controller;

import com.climbup.climbup.tier.entity.Tier;
import com.climbup.climbup.tier.service.TierService;
import com.climbup.climbup.user.dto.request.SetUserTierRequest;
import com.climbup.climbup.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private final TierService tierService;

    @PostMapping("/set-tier")
    public ResponseEntity<Void> setUserTier(@RequestBody() SetUserTierRequest request) {
        Tier tier = tierService.getTierById(request.getTierId());
        userService.setUserTier(tier);

        return ResponseEntity.accepted().build();
    }
}
