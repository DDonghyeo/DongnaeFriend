package com.umc.DongnaeFriend.domain.report.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.umc.DongnaeFriend.domain.report.entity.QReport.report;

import com.umc.DongnaeFriend.domain.report.dto.ReportDto.ReportRequest;
import com.umc.DongnaeFriend.domain.report.entity.Report;
import java.util.Optional;
import javax.persistence.EntityManager;

public class ReportRepositoryImpl implements ReportRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ReportRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Report> findByUserAndPost(Long userId, ReportRequest request) {

        Report result = queryFactory
            .selectFrom(report)
            .where(
                userEq(userId),
                dongnaeBoardEq(request.getDongnaeBoardId()),
                sharingBoardEq(request.getSharingBoardId()),
                dongnaeCommentEq(request.getDongnaeCommentId()),
                sharingCommentEq(request.getSharingCommentId())
            )
            .fetchOne();
        return Optional.ofNullable(result);
    }

    private BooleanExpression userEq(Long userId) {
        return report.user.id.eq(userId);
    }

    private BooleanExpression dongnaeBoardEq(Long dongnaeBoardId) {
        return dongnaeBoardId == null ? null : report.dongnaeBoard.id.eq(dongnaeBoardId);
    }

    private BooleanExpression sharingBoardEq(Long sharingBoardId) {
        return sharingBoardId == null ? null : report.sharingBoard.id.eq(sharingBoardId);
    }

    private BooleanExpression dongnaeCommentEq(Long dongnaeCommentId) {
        return dongnaeCommentId == null ? null : report.dongnaeComment.id.eq(dongnaeCommentId);
    }

    private BooleanExpression sharingCommentEq(Long sharingCommentId) {
        return sharingCommentId == null ? null : report.sharingComment.id.eq(sharingCommentId);
    }

}
