package com.jidian.demo.repository;

import com.jidian.demo.model.BiddingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BiddingRepository extends JpaRepository<BiddingInfo,Long> {
    BiddingInfo findOneById(Long id);
    BiddingInfo findOneByDidAndBuid(Long did, Long buid);
    Long countByDidAndBuid(Long did, Long buid);
    BiddingInfo findAllByBuid(Long buid);
    void deleteOneById(Long id);
}
