package com.trackplus.trackplusbackend.repository;

import com.trackplus.trackplusbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}