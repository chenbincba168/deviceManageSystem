 package cn.tycoding.pojo;

import java.io.Serializable;

/**
 * 客户信息的JavaBean
 * @author tycoding
 * @date 21-5-15下午7:27
 */
public class Device implements Serializable {
    private Long id;
    private String deviceName;
    private String deviceSN;
    private String deviceIP;
    private String deviceDirection;
    private String deviceGroup;
    private String deviceProperty;
    private String deviceConnect;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public String getDeviceDirection() {
        return deviceDirection;
    }

    public void setDeviceDirection(String deviceDirection) {
        this.deviceDirection = deviceDirection;
    }

    public String getDeviceGroup() {
        return deviceGroup;
    }

    public void setDeviceGroup(String deviceGroup) {
        this.deviceGroup = deviceGroup;
    }

    public String getDeviceProperty() {
        return deviceProperty;
    }

    public void setDeviceProperty(String deviceProperty) {
        this.deviceProperty = deviceProperty;
    }

    public String getDeviceConnect() {
        return deviceConnect;
    }

    public void setDeviceConnect(String deviceConnect) {
        this.deviceConnect = deviceConnect;
    }
}
