package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.Apart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ApartRepository extends JpaRepository<Apart, Long> {

    Page<Apart> findAllByOrderByModifiedAtDesc(Pageable pageable);
    Page<Apart> findByArea(String area, Pageable pageable);
}
