package com.climbup.climbup.session.dto.response;

import com.climbup.climbup.session.entity.UserSession;
import com.climbup.climbup.user.entity.User;
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

        private Long id;
        
        public static FinishUserSession toDto(UserSession session) {
            FinishUserSession response = new FinishUserSession();
            response.id = session.getId();
            return response;
        }
    }

    @Data
    public static class UserSessionState {
        private LocalDate sessionDate;

        private LocalDateTime startedAt;

        private LocalDateTime endedAt;

        private Integer totalDuration;

        private Integer srGained;

        private Integer currentSr;

        private Integer previousSr;

        private Integer completedCount;

        private Integer attemptedCount;

        public static UserSessionState toDto(UserSession session, User user) {
            UserSessionState response = new UserSessionState();
            response.sessionDate = session.getSessionDate();
            response.startedAt = session.getStartedAt();
            response.endedAt = session.getEndedAt();
            response.totalDuration = session.getTotalDuration();
            response.srGained = session.getSrGained();
            response.currentSr = user.getSr() + session.getSrGained();
            response.previousSr = user.getSr();
            response.completedCount = session.getCompletedCount();
            response.attemptedCount = session.getAttemptedCount();
            return response;
        }
    }

    @Data
    public static class UserActiveSession {
        private Long id;
        private LocalDateTime startedAt;

        public static UserActiveSession toDto(UserSession session) {
            UserActiveSession response = new UserActiveSession();
            response.id = session.getId();
            response.startedAt = session.getStartedAt();
            return response;
        }
    }
}
