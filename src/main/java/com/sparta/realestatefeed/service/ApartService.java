package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.dto.ApartRequestDto;
import com.sparta.realestatefeed.dto.ApartResponseDto;
import com.sparta.realestatefeed.entity.Apart;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.repository.ApartRepository;
import com.sparta.realestatefeed.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ApartService {

    private final ApartRepository apartRepository;

    public ApartService(ApartRepository apartRepository) {
        this.apartRepository = apartRepository;
    }

    public ApartResponseDto createApart(ApartRequestDto requestDto, User user) {
        Apart apart = new Apart(requestDto, user); // 새 생성자를 사용하여 객체 생성
        Apart savedApart = apartRepository.save(apart);
        return new ApartResponseDto(savedApart);
    }

    public ApartResponseDto getApart(Long id) {
        Apart apart = apartRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid apart ID")
        );
        return new ApartResponseDto(apart);
    }

    public List<ApartResponseDto> getAllAparts() {
        List<Apart> aparts = apartRepository.findAll();
        return aparts.stream().map(ApartResponseDto::new).collect(Collectors.toList());
    }
}
