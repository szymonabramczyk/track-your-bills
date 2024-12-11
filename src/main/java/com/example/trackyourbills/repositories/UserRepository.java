package com.example.trackyourbills.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.trackyourbills.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}