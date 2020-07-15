package com.jidian.demo.repository;

import com.jidian.demo.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findOneById(Long id);
    Admin findOneByUsername(String username);
    Long countByUsernameAndPassword(String username, String password);
}
