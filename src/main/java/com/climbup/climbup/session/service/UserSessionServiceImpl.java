package com.climbup.climbup.session.service;

import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.session.exception.UserSessionAlreadyFinishedException;
import com.climbup.climbup.session.exception.UserSessionNotFoundException;
import com.climbup.climbup.session.exception.UserSessionNotYetFinishedException;
import com.climbup.climbup.session.repository.UserSessionRepository;
import com.climbup.climbup.user.entity.User;
import com.climbup.climbup.user.exception.UserNotFoundException;
import com.climbup.climbup.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {
    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserSession startSession(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        var incompleteSessions = user.getSessions().stream().filter(userSession -> userSession.getEndedAt() == null).toList();

        if(!incompleteSessions.isEmpty()) {
            throw new UserSessionNotYetFinishedException("아직 마무리되지 않은 세션이 존재합니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        
        UserSession session = UserSession.builder()
                .user(user)
                .gym(user.getGym())
                .sessionDate(today)
                .startedAt(now)
                .srGained(0)
                .completedCount(0)
                .attemptedCount(0)
                .build();
        
        return userSessionRepository.save(session);
    }

    @Override
    @Transactional
    public UserSession finishSession(Long userId, Long sessionId) {
        UserSession session = userSessionRepository.findById(sessionId).orElseThrow(() -> new UserSessionNotFoundException("세션이 존재하지 않습니다."));

        if(!session.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("해당 세션에 접근할 수 없습니다.");
        }

        if(session.getEndedAt() != null) {
            throw new UserSessionAlreadyFinishedException("이미 종료된 세션입니다.");
        }

        LocalDateTime endTime = LocalDateTime.now();
        session.setEndedAt(endTime);

        Duration duration = Duration.between(session.getStartedAt(), endTime);
        session.setTotalDuration((int) duration.toMinutes());
        
        userSessionRepository.save(session);

        return session;
    }
}
