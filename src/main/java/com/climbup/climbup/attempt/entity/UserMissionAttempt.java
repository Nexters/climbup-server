package com.climbup.climbup.attempt.entity;

import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_mission_attempts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMissionAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private UserSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private RouteMission mission;

    @Column(name = "attempted_at", nullable = false)
    private LocalDateTime attemptedAt;

    @Column(name = "success", nullable = false)
    private Boolean success;

    @Column(name = "video_url", columnDefinition = "TEXT")
    private String videoUrl;

    public UserMissionAttempt(User user, UserSession session, RouteMission mission, Boolean success) {
        this.user = user;
        this.session = session;
        this.mission = mission;
        this.success = success;
        this.attemptedAt = LocalDateTime.now();
    }
}