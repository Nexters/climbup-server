package com.climbup.climbup.attempt.entity;

import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "user_mission_attempts", indexes = {
        @Index(name = "idx_attempt_user_session", columnList = "user_id, session_id")
})
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserMissionAttempt extends BaseEntity {

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

    @Column(name = "success", nullable = false)
    private Boolean success;

    @Column(name = "thumbnail_url", columnDefinition = "TEXT")
    private String thumbnailUrl;

    @Column(name = "video_url", columnDefinition = "TEXT")
    private String videoUrl;
}