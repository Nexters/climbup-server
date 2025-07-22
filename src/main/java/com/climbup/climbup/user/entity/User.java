package com.climbup.climbup.user.entity;

import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.sr.entity.SRHistory;
import com.climbup.climbup.tier.entity.Tier;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", nullable = false, unique = true, length = 50)
    private String kakaoId;

    @Column(name = "nickname", nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(name = "sr", nullable = false, columnDefinition = "INT DEFAULT 600")
    private Integer sr = 600;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id", nullable = false)
    private Tier tier;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "onboarding_completed", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean onboardingCompleted = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSession> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserMissionAttempt> attempts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SRHistory> srHistories = new ArrayList<>();

    public void completeOnboarding(Tier selectedTier) {
        this.tier = selectedTier;
        this.onboardingCompleted = true;
    }

    public boolean needsOnboarding() {
        return !this.onboardingCompleted;
    }
}