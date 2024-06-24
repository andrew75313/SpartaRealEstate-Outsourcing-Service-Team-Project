package com.sparta.realestatefeed.dto;

public class ValidationErrorResponseDto {
    private String errorMessage;

    public ValidationErrorResponseDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
