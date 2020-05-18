package com.example.DeviceManagerRedisGraphQL.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DeviceDaoImpl implements DeviceDao {
    private final Logger log = LoggerFactory.getLogger(DeviceDaoImpl.class);
    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    RedisTemplate redisTemplate;

    private static final String KEY = "device";

    public Boolean saveDevice(Device device) {
        try {
            Map deviceHash = new ObjectMapper().convertValue(device, Map.class);
            redisTemplate.opsForHash().putIfAbsent(KEY, device.getId(), deviceHash);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Device> findAllDevices() {
        List<Device> devices = (List<Device>) redisTemplate.opsForHash().values(KEY);
        return devices;
    }

    public Device findById(String id) {
        Map deviceMap = (Map) redisTemplate.opsForHash().get(KEY, id);
        Device device = new ObjectMapper().convertValue(deviceMap, Device.class);
        return device;
    }

    public List<Device> findByDeviceType(String deviceType) {
        List<Device> deviceList = new ArrayList<>();
        for (Object deviceMap: redisTemplate.opsForHash().values(KEY)) {
            Device device = new ObjectMapper().convertValue(deviceMap, Device.class);
            if (device.getDeviceType().equals(deviceType)) {
                deviceList.add(device);
            }
        }
        return deviceList;
    }

    public Boolean updateDevice(Device device) {
        Map deviceHash = new ObjectMapper().convertValue(device, Map.class);
        redisTemplate.opsForHash().put(KEY, device.getId(), deviceHash);
        return true;
    }

    public void deleteDevice(String id) {
        redisTemplate.opsForHash().delete(KEY, id);
    }
}
