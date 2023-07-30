package com.umc.DongnaeFriend.domain.report.repository;

import com.umc.DongnaeFriend.domain.report.dto.ReportDto.ReportRequest;
import com.umc.DongnaeFriend.domain.report.entity.Report;
import java.util.Optional;

public interface ReportRepositoryCustom {

    Optional<Report> findByUserAndPost(Long userId, ReportRequest request);
}
