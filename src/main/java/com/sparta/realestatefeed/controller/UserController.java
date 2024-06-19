package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.dto.CommonDto;
import com.sparta.realestatefeed.dto.QnAResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/a")
    public ResponseEntity<CommonDto<QnAResponseDto>> get() {
        // 전달하고 싶은 DTO -> CommonDto에 담기 -> ResponseEntity로 상태와 함께 응답
        CommonDto<QnAResponseDto> response = new CommonDto<>(HttpStatus.OK.value(), "댓글 리스트 조회 성공", new QnAResponseDto("test"));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
