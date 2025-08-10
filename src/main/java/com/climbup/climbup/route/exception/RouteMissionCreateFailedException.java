package com.climbup.climbup.route.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class RouteMissionCreateFailedException extends BusinessException {
    public RouteMissionCreateFailedException(Throwable cause) {
        super(ErrorCode.ROUTE_MISSION_CREATE_FAILED, cause);
    }
}