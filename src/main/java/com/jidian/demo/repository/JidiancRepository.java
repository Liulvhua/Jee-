package com.jidian.demo.repository;

import com.jidian.demo.model.JidianCtory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JidiancRepository extends JpaRepository<JidianCtory,Long>{
    JidianCtory findOneById(Long id);
    //登录判断
    Long countByUsernameAndPassword(String name, String Password);
    //登录成功获取用户信息
    JidianCtory findOneByUsername(String username);
    void deleteOneById(Long id);
}
