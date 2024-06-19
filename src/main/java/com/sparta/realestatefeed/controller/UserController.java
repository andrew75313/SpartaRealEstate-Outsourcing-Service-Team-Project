package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.dto.QnAResponseDto;
import com.sparta.realestatefeed.dto.Response;
import com.sparta.realestatefeed.util.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/a")
    public Response<QnAResponseDto> get() {
        return ApiUtils.success(HttpStatus.OK, "댓글 리스트 조회 성공", new QnAResponseDto("test"));
    }

}
