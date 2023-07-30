package com.umc.DongnaeFriend.domain.report.repository;

import com.umc.DongnaeFriend.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long>, ReportRepositoryCustom {

}
