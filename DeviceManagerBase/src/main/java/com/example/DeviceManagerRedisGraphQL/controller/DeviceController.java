package com.example.DeviceManagerRedisGraphQL.controller;

import com.example.DeviceManagerRedisGraphQL.model.Device;
import com.example.DeviceManagerRedisGraphQL.service.DeviceManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DeviceController {
    private final String BASEURL = "/api";
    private final Logger log = LoggerFactory.getLogger(DeviceController.class);
    @Autowired
    DeviceManagerService service;

    @PostMapping("/device")
    public ResponseEntity<?> addDevice(@RequestBody Device device) throws URISyntaxException {
        List<Device> existingDevices = service.findByDeviceType(device.getDeviceType());
        if (existingDevices != null && existingDevices.size() > 0) {
            return ResponseEntity.badRequest().body(
                    String.format("A device of type %s has already been created", device.getDeviceType()));
        }
        Device result = service.saveDevice(device);
        if (result != null) {
            return ResponseEntity.created(new URI(BASEURL + "/devices?deviceType=" + device.getDeviceType()))
                    .body(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/devices")
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(service.findAllDevices());
    }

    @GetMapping("/device")
    public ResponseEntity<Device> getDevice(@RequestParam(name = "deviceType", required = false) String deviceType) {
        Device result = service.findByDeviceType(deviceType).get(0);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/device")
    public ResponseEntity<?> updateDevice(@RequestBody Device device) {
        if (device.getId() == null) {
            return ResponseEntity.badRequest().body("No device ID was provided");
        }
        if (device.getDeviceType() == null) {
            return ResponseEntity.badRequest().body("No device type was specified");
        }
        Device result = service.updateDevice(device);
        if (result != null) {
            return ResponseEntity.ok(device);
        } else {
            return ResponseEntity.badRequest().body("Error updating the device");
        }
    }

    @DeleteMapping("/device")
    public ResponseEntity<Device> deleteDevice(@RequestBody Device device) {
        Device deletedDevice = service.findById(device.getId());
        service.deleteDevice(device.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedDevice);
    }
}
