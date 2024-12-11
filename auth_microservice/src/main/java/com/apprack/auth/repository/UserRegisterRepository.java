package com.apprack.auth.repository;

import com.apprack.auth.model.RegisterUser;
import com.apprack.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRegisterRepository extends JpaRepository<RegisterUser, Long> {
    Optional<RegisterUser> findByUsername(String username);
    List<RegisterUser> findAll();

}

