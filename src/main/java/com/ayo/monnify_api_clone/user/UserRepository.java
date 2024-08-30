package com.ayo.monnify_api_clone.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity>findByApiKey(String apiKey);
}