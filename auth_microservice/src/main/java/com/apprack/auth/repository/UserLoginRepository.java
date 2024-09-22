package com.apprack.auth.repository;

import com.apprack.auth.model.RegisterUser;
import com.apprack.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
