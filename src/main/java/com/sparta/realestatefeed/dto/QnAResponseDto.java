package com.sparta.realestatefeed.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class QnAResponseDto {
    String content;

    public QnAResponseDto(String s) {
        this.content = s;
    }
}
