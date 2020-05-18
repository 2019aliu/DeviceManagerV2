package com.example.DeviceManagerRedisGraphQL.controller;

import com.example.DeviceManagerRedisGraphQL.model.Device;
import com.example.DeviceManagerRedisGraphQL.service.DeviceManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> addDevice(@RequestBody Device device) throws URISyntaxException {
        if (service.findByDeviceType(device.getDeviceType()).size() > 0) {
            return ResponseEntity.badRequest().body(
                    String.format("A device of type %s has already been created", device.getDeviceType()));
        }
        Boolean result = service.saveDevice(device);
        if (result) {
            return ResponseEntity.created(new URI(BASEURL + "/devices?deviceType=" + device.getDeviceType())).build();
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
        Boolean result = service.updateDevice(device);
        if (result) {
            return ResponseEntity.ok(device);
        } else {
            return ResponseEntity.badRequest().body("Error updating the device");
        }
    }

    @DeleteMapping("/device")
    public ResponseEntity<?> deleteDevice(@RequestBody Device device) {
        service.deleteDevice(device.getId());
        return ResponseEntity.noContent().build();
    }
}
