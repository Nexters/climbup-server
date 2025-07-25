package com.climbup.climbup.session.dto.response;

import com.climbup.climbup.session.entity.UserSession;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
public class UserSessionResponses {
    @Data
    public static class CreateUserSession {

        private Long id;

        private LocalDateTime startedAt;
        
        public static CreateUserSession toDto(UserSession session) {
            CreateUserSession response = new CreateUserSession();
            response.id = session.getId();
            response.startedAt = session.getStartedAt();
            return response;
        }
    }

    @Data
    public static class FinishUserSession {

        private LocalDate sessionDate;

        private LocalDateTime startedAt;

        private LocalDateTime endedAt;

        private Integer totalDuration;

        private Integer srGained;

        private Integer completedCount;

        private Integer attemptedCount;
        
        public static FinishUserSession toDto(UserSession session) {
            FinishUserSession response = new FinishUserSession();
            response.sessionDate = session.getSessionDate();
            response.startedAt = session.getStartedAt();
            response.endedAt = session.getEndedAt();
            response.totalDuration = session.getTotalDuration();
            response.srGained = session.getSrGained();
            response.completedCount = session.getCompletedCount();
            response.attemptedCount = session.getAttemptedCount();
            return response;
        }
    }
}
