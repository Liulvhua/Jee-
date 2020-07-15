package com.jidian.demo.repository;

import com.jidian.demo.model.Tenderunit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenderUnitRepository extends JpaRepository<Tenderunit, Long> {
    Tenderunit findOneById(Long id);
}
