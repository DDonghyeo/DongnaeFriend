package com.umc.DongnaeFriend.domain.account.book.dto;

import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.stream.Collectors;

public class MemoDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemoResponse{
        private Long memoId;
        private String memo;
        private Boolean done;

        public static MemoResponse from(Memo memo){
            return new MemoResponse(
                    memo.getId(),
                    memo.getMemo(),
                    memo.getDone());
        }
    }

    @Getter
    public static class MemoListResponse{
        private List<MemoResponse> memos;

        public MemoListResponse(List<MemoResponse> memos){
            this.memos = memos;
        }

        public static MemoListResponse of(List<Memo> memoList){
            List<MemoResponse> memoResponses = memoList
                    .stream()
                    .map(MemoResponse::from)
                    .collect(Collectors.toList());

            return new MemoListResponse(memoResponses);
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemoRequest{
        private String memo;
        private Boolean done;

        public Memo toEntity(AccountBook accountBook){
            return Memo.builder()
                    .accountBook(accountBook)
                    .memo(memo)
                    .done(done)
                    .build();
        }
    }
}
