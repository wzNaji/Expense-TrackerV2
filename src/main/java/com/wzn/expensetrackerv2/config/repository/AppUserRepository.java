package com.wzn.expensetrackerv2.config.repository;

import com.wzn.expensetrackerv2.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername(String username);
}

