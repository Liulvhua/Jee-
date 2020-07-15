package com.jidian.demo.repository;

import com.jidian.demo.model.BidUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidingUnitRepository extends JpaRepository<BidUnit, Long> {
    //登录成功获取用户信息
    BidUnit findOneByUsername(String username);
    //注册判断
    BidUnit findOneByUsernameAndPassword(String username, String password);
    BidUnit findOneById(Long id);
    //通过id删除招标单位信息
    void deleteOneById(Long id);
    Long countByUsernameAndPassword(String username, String password);
}
