package com.example.DeviceManagerRedisGraphQL.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Device")
public class Device implements Serializable {
    @Id
    private String id;
    
    private String deviceType;
    private String description = "";
    private int quantity = 0;

    public Device( String deviceType) {
        this.deviceType = deviceType;
    }

    public Device(String id,  String deviceType, String description, int quantity) {
        this.id = id;
        this.deviceType = deviceType;
        this.description = description;
        this.quantity = quantity;
    }

    public Device() {
    }

    public String getId() {
        return this.id;
    }

    
    public  String getDeviceType() {
        return this.deviceType;
    }

    public String getDescription() {
        return this.description;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDeviceType(  String deviceType) {
        this.deviceType = deviceType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Device)) return false;
        final Device other = (Device) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$deviceType = this.getDeviceType();
        final Object other$deviceType = other.getDeviceType();
        if (this$deviceType == null ? other$deviceType != null : !this$deviceType.equals(other$deviceType))
            return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        if (this.getQuantity() != other.getQuantity()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Device;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $deviceType = this.getDeviceType();
        result = result * PRIME + ($deviceType == null ? 43 : $deviceType.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        result = result * PRIME + this.getQuantity();
        return result;
    }

    public String toString() {
        return "Device(id=" + this.getId() + ", deviceType=" + this.getDeviceType() + ", description=" + this.getDescription() + ", quantity=" + this.getQuantity() + ")";
    }
}
