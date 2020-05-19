package com.example.DeviceManagerRedisGraphQL.service;

import com.example.DeviceManagerRedisGraphQL.model.Device;
import com.example.DeviceManagerRedisGraphQL.model.DeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceManagerServiceImpl implements DeviceManagerService {
    @Autowired
    DeviceDao deviceDao;

    public Device saveDevice(Device device) {
        String generatedID = UUID.randomUUID().toString();
        device.setId(generatedID);
        return deviceDao.saveDevice(device);
    }

    public List<Device> findAllDevices() {
        return deviceDao.findAllDevices();
    }

    public Device findById(String id) {
        if (findAllDevices().size() == 0) {
            return null;
        }
        return deviceDao.findById(id);
    }

    @Override
    public List<Device> findByDeviceType(String deviceType) {
        if (findAllDevices().size() == 0) {
            return null;
        }
        return deviceDao.findByDeviceType(deviceType);
    }

    public Device updateDevice(Device device) {
        return deviceDao.updateDevice(device);
    }

    public void deleteDevice(String id) {
        deviceDao.deleteDevice(id);
    }
}
