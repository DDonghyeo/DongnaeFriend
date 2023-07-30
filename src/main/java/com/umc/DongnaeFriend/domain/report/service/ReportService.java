package com.umc.DongnaeFriend.domain.report.service;

import static com.umc.DongnaeFriend.global.exception.ErrorCode.COMMENT_NOT_EXISTS;
import static com.umc.DongnaeFriend.global.exception.ErrorCode.POST_NOT_EXISTS;
import static com.umc.DongnaeFriend.global.exception.ErrorCode.REPORT_ALREADY_EXISTS;
import static com.umc.DongnaeFriend.global.exception.ErrorCode.USER_NOT_FOUND;

import com.umc.DongnaeFriend.domain.account.sharing.entity.QSharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingBoardRepository;
import com.umc.DongnaeFriend.domain.account.sharing.repository.SharingCommentRepository;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeBoardRepository;
import com.umc.DongnaeFriend.domain.dongnae.respository.DongnaeCommentRepository;
import com.umc.DongnaeFriend.domain.report.dto.ReportDto;
import com.umc.DongnaeFriend.domain.report.entity.Report;
import com.umc.DongnaeFriend.domain.report.repository.ReportRepository;
import com.umc.DongnaeFriend.domain.user.entity.User;
import com.umc.DongnaeFriend.domain.user.repository.UserRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final DongnaeBoardRepository dongnaeBoardRepository;
    private final DongnaeCommentRepository dongnaeCommentRepository;
    private final SharingBoardRepository sharingBoardRepository;
    private final SharingCommentRepository sharingCommentRepository;

    public void report(ReportDto.ReportRequest request) {
        User user = userRepository.findById(getId())
            .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        reportDuplicateCheck(user.getId(), request);

        request.toString();

        User reportUser = null;
        DongnaeBoard db = null;
        SharingBoard sb = null;
        DongnaeComment dc = null;
        SharingComment sc = null;

        if (request.getReportUserId() != null) {
            reportUser = userRepository.findById(request.getReportUserId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        } else if (request.getDongnaeBoardId() != null) {
            db = dongnaeBoardRepository.findById(request.getDongnaeBoardId())
                .orElseThrow(() -> new CustomException(POST_NOT_EXISTS));
        } else if (request.getSharingBoardId() != null) {
            sb = sharingBoardRepository.findById(request.getSharingBoardId())
                .orElseThrow(() -> new CustomException(POST_NOT_EXISTS));
        } else if (request.getDongnaeCommentId() != null) {
            dc = dongnaeCommentRepository.findById(request.getDongnaeCommentId())
                .orElseThrow(() -> new CustomException(COMMENT_NOT_EXISTS));
        } else {
            sc = sharingCommentRepository.findById(request.getSharingCommentId())
                .orElseThrow(() -> new CustomException(COMMENT_NOT_EXISTS));
        }

        Report report = request.toEntity(user, reportUser, db, sb, dc, sc, request.getContent());
        reportRepository.save(report);
    }

    public Long getId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 신고 중복체크
     */
    public void reportDuplicateCheck(Long userId, ReportDto.ReportRequest request) {
        reportRepository.findByUserAndPost(userId, request).ifPresent(report ->
        {
            throw new CustomException(REPORT_ALREADY_EXISTS);
        });
    }
}
