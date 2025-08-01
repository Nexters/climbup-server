package com.climbup.climbup.recommendation.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class RecommendationNotFoundException extends BusinessException {
    public RecommendationNotFoundException() {
        super(ErrorCode.RECOMMENDATION_NOT_FOUND);
    }
}
