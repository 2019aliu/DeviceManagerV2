package com.example.DeviceManagerRedisGraphQL.model;

import java.util.List;

public interface DeviceDao {
    public Boolean saveDevice(Device device);
    public List<Device> findAllDevices();
    public Device findById(String id);
    public List<Device> findByDeviceType(String deviceType);
    public Boolean updateDevice(Device device);
    public void deleteDevice(String id);
}
