package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.QnA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepository extends JpaRepository<QnA, Long> {

    List<QnA> findByApartId(Long apartId);

}
