package com.climbup.climbup.route.service;

import com.climbup.climbup.route.dto.request.CreateRouteMissionRequest;
import com.climbup.climbup.route.dto.request.UpdateRouteMissionRequest;
import com.climbup.climbup.route.dto.response.RouteMissionListResponse;
import com.climbup.climbup.route.dto.response.RouteMissionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RouteMissionService {

    RouteMissionResponse createRouteMission(
            CreateRouteMissionRequest request,
            MultipartFile routeImage,
            MultipartFile guideVideo,
            MultipartFile videoThumbnail
    );

    RouteMissionResponse getRouteMission(Long missionId);

    List<RouteMissionListResponse> getRouteMissions(Long gymId, Long sectorId, String difficulty);

    RouteMissionResponse updateRouteMission(
            Long missionId,
            UpdateRouteMissionRequest request,
            MultipartFile routeImage,
            MultipartFile guideVideo,
            MultipartFile videoThumbnail
    );

    void deleteRouteMission(Long missionId);
}
