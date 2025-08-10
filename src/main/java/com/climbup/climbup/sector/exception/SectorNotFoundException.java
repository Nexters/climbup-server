package com.climbup.climbup.sector.exception;

import com.climbup.climbup.common.exception.BusinessException;
import com.climbup.climbup.common.exception.ErrorCode;

public class SectorNotFoundException extends BusinessException {
    public SectorNotFoundException() {
        super(ErrorCode.SECTOR_NOT_FOUND);
    }

    public SectorNotFoundException(Long sectorId) {
        super(ErrorCode.SECTOR_NOT_FOUND, "섹터 ID: " + sectorId);
    }
}