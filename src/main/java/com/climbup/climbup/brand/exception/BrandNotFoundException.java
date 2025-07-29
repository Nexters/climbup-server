package com.climbup.climbup.brand.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class BrandNotFoundException extends BusinessException {
    public BrandNotFoundException() {
        super(ErrorCode.BRAND_NOT_FOUND);
    }

    public BrandNotFoundException(Long brandId) {
        super(ErrorCode.BRAND_NOT_FOUND, "암장 브랜드 ID: " + brandId);
    }
}