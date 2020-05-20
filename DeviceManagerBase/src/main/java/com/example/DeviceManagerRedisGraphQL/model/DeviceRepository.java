package com.example.DeviceManagerRedisGraphQL.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Long> {
    public List<Device> findByDeviceType(String deviceType);
    public List<Device> findByDeviceTypeAndDescription(String deviceType, String description);
}
