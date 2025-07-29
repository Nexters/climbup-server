package com.climbup.climbup.user.entity;

import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.gym.entity.GymLevel;
import com.climbup.climbup.gym.exception.GymLevelNotFoundException;
import com.climbup.climbup.gym.exception.GymNotFoundException;
import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.sr.entity.SRHistory;
import com.climbup.climbup.level.entity.Level;
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

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "nickname", nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(name = "sr", nullable = false, columnDefinition = "INT DEFAULT 600")
    private Integer sr = 600;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_level_id")
    private GymLevel gymLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id")
    private ClimbingGym gym;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserSession> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserMissionAttempt> attempts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SRHistory> srHistories = new ArrayList<>();

    public void selectGymLevel(GymLevel gymLevel) {
        if (gymLevel == null) {
            throw new GymLevelNotFoundException();
        }
        this.gymLevel = gymLevel;
    }

    public void selectGym(ClimbingGym gym) {
        if (gym == null) {
            throw new GymNotFoundException();
        }
        this.gym = gym;
    }

    public void updateSr(Integer newSr) {
        if (newSr < 0) {
            throw new IllegalArgumentException("SR은 0보다 작을 수 없습니다.");
        }
        this.sr = newSr;
    }

    public boolean hasCompletedOnboarding() {
        return this.gym != null && this.gymLevel != null;
    }
}