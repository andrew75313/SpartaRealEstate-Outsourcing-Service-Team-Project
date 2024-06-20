package com.sparta.realestatefeed.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonDto<T>  {

    private final Integer status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    @Builder
    public CommonDto(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
