package com.climbup.climbup.route.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class RouteNotFoundException extends BusinessException {
    public RouteNotFoundException() {
        super(ErrorCode.ROUTE_NOT_FOUND);
    }
}