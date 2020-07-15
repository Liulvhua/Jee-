package com.jidian.demo.repository;

import com.jidian.demo.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findOneById(Long id);
}
