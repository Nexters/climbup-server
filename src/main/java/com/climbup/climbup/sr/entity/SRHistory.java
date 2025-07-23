package com.climbup.climbup.sr.entity;

import com.climbup.climbup.common.entity.BaseEntity;
import com.climbup.climbup.route.entity.RouteMission;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.session.entity.UserSession;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "sr_histories")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class SRHistory extends BaseEntity {

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

    @Column(name = "sr_before", nullable = false)
    private Integer srBefore;

    @Column(name = "sr_after", nullable = false)
    private Integer srAfter;
}