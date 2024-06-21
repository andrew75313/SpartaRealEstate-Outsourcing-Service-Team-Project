package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.dto.ApartRequestDto;
import com.sparta.realestatefeed.dto.ApartResponseDto;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.service.ApartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aparts")
public class ApartController {
    private final ApartService apartService;

    public ApartController(ApartService apartService) {
        this.apartService = apartService;
    }

    @PostMapping
    public ResponseEntity<ApartResponseDto> createApart(@RequestBody ApartRequestDto apartRequestDto, @RequestParam User user) {
        ApartResponseDto responseDto = apartService.createApart(apartRequestDto, user);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartResponseDto> getApart(@PathVariable Long id) {
        ApartResponseDto responseDto = apartService.getApart(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ApartResponseDto>> getAllAparts() {
        List<ApartResponseDto> responseDtos = apartService.getAllAparts();
        return ResponseEntity.ok(responseDtos);
    }
}
