package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.Apart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartRepository extends JpaRepository<Apart, Long> {
    List<Apart> findAllByOrderByModifiedAtDesc();
}
