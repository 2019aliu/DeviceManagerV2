package com.example.DeviceManagerRedisGraphQL.service;

import com.example.DeviceManagerRedisGraphQL.model.Device;

import java.util.List;

public interface DeviceManagerService {
    public Boolean saveDevice(Device device);
    public List<Device> findAllDevices();
    public Device findById(String id);
    public List<Device> findByDeviceType(String deviceType);
    public Boolean updateDevice(Device device);
    public void deleteDevice(String id);
}
