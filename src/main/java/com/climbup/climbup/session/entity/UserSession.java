package com.climbup.climbup.session.entity;

import com.climbup.climbup.attempt.entity.UserMissionAttempt;
import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.gym.entity.ClimbingGym;
import com.climbup.climbup.sr.entity.SRHistory;
import com.climbup.climbup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_sessions", indexes = {
        @Index(name = "idx_session_user_date", columnList = "user_id, session_date"),
        @Index(name = "idx_session_gym_date", columnList = "gym_id, session_date"),
        @Index(name = "idx_session_user_gym", columnList = "user_id, gym_id")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class UserSession extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id", nullable = false)
    private ClimbingGym gym;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(name = "total_duration")
    private Integer totalDuration;

    @Column(name = "sr_gained", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer srGained = 0;

    @Column(name = "completed_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer completedCount = 0;

    @Column(name = "attempted_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer attemptedCount = 0;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<UserMissionAttempt> attempts = new ArrayList<>();

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<SRHistory> srHistories = new ArrayList<>();
}