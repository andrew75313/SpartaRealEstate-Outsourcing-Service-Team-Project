package com.sparta.realestatefeed.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class QnARequestDto {

    @NotBlank
    private String content;

}
