package com.climbup.climbup.sr.entity;

import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.session.entity.UserSession;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "sr_histories")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SRHistory {

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

    @CreatedDate
    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(name = "sr_before")
    private Integer srBefore;

    @Column(name = "sr_after", nullable = false)
    private Integer srAfter;

    public SRHistory(User user, UserSession session, RouteMission mission, Integer srBefore, Integer srAfter) {
        this.user = user;
        this.session = session;
        this.mission = mission;
        this.srBefore = srBefore;
        this.srAfter = srAfter;
    }
}