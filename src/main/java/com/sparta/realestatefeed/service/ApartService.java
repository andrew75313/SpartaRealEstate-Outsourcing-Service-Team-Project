package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.dto.ApartRequestDto;
import com.sparta.realestatefeed.dto.ApartResponseDto;
import com.sparta.realestatefeed.dto.CommonDto;
import com.sparta.realestatefeed.entity.Apart;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.repository.ApartRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class ApartService {

    private final ApartRepository apartRepository;

    public ApartService(ApartRepository apartRepository) {
        this.apartRepository = apartRepository;
    }

    @Transactional
    public CommonDto<ApartResponseDto> createApart(ApartRequestDto requestDto, User user) {
        Apart apart = new Apart(requestDto, user);
        Apart savedApart = apartRepository.save(apart);
        ApartResponseDto responseDto = new ApartResponseDto(savedApart);
        return new CommonDto<>(HttpStatus.OK.value(), "아파트 생성에 성공하였습니다.", responseDto);
    }

    public CommonDto<ApartResponseDto> getApart(Long id) {
        Apart apart = apartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 아파트 ID입니다."));
        ApartResponseDto responseDto = new ApartResponseDto(apart);
        return new CommonDto<>(HttpStatus.OK.value(), "아파트 조회에 성공하였습니다.", responseDto);
    }

    public CommonDto<List<ApartResponseDto>> getAllAparts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Apart> apartsPage = apartRepository.findAllByOrderByModifiedAtDesc(pageable);
        List<ApartResponseDto> responseDtos = apartsPage.stream()
                .map(ApartResponseDto::new)
                .collect(Collectors.toList());
        return new CommonDto<>(HttpStatus.OK.value(), "모든 아파트 조회에 성공하였습니다.", responseDtos);
    }

    @Transactional
    public CommonDto<ApartResponseDto> updateApart(Long id, ApartRequestDto requestDto, User user) {
        Apart apart = apartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 아파트 ID입니다."));

        if (!apart.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }

        apart.update(requestDto);
        Apart updatedApart = apartRepository.save(apart);
        ApartResponseDto responseDto = new ApartResponseDto(updatedApart);
        return new CommonDto<>(HttpStatus.OK.value(), "아파트 수정에 성공하였습니다.", responseDto);
    }

    public CommonDto<String> deleteApart(Long id, User user) {
        Apart apart = apartRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 아파트 ID입니다."));

        if (!apart.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }

        apartRepository.delete(apart);
        return new CommonDto<>(HttpStatus.OK.value(), "아파트 삭제에 성공하였습니다.", null);
    }
}
