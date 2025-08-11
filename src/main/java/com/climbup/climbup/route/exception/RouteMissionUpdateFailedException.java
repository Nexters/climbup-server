package com.climbup.climbup.route.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class RouteMissionUpdateFailedException extends BusinessException {
    public RouteMissionUpdateFailedException(Long missionId, Throwable cause) {
        super(ErrorCode.ROUTE_MISSION_UPDATE_FAILED, cause, "미션 ID: " + missionId);
    }
}