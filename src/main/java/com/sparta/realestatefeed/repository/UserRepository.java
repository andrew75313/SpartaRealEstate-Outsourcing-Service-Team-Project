package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
