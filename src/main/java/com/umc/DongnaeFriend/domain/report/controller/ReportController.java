package com.umc.DongnaeFriend.domain.report.controller;

import com.umc.DongnaeFriend.domain.report.dto.ReportDto;
import com.umc.DongnaeFriend.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<?> reportBoard(@RequestBody ReportDto.ReportRequest request) {
        reportService.report(request);
        return ResponseEntity.ok().build();
    }
}
