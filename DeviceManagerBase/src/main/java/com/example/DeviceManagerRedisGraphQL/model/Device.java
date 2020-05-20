package com.example.DeviceManagerRedisGraphQL.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@RedisHash("Device")
public class Device implements Serializable {
    @Id
    @Generated
    private String id;
    @NonNull
    private String deviceType;
    private String description = "";
    private int quantity = 0;
}
