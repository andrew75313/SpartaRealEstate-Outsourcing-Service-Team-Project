package com.sparta.realestatefeed.dto;

import com.sparta.realestatefeed.entity.QnA;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class QnAResponseDto {

    private Long qnaId;
    private String content;

    public QnAResponseDto(QnA qna) {
        this.qnaId = qna.getQndId();
        this.content = qna.getContent();
    }
}
