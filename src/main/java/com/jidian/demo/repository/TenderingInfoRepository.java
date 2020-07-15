package com.jidian.demo.repository;

import com.jidian.demo.model.TenderingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenderingInfoRepository extends JpaRepository<TenderingInfo, Long> {
    TenderingInfo findOneByTuidAndAndCid(Long tuid, Long cid);
}
