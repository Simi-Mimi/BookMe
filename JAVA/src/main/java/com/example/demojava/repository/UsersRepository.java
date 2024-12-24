package com.example.demojava.repository;

import com.example.demojava.models.Professional;
import com.example.demojava.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository <Users, Long>{
    Optional<Users> findByEmail(String email);
}