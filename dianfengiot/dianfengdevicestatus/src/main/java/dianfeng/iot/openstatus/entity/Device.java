package dianfeng.iot.openstatus.entity;

import dianfeng.iot.openstatus.common.DeviceType;

import javax.persistence.*;

@Entity
@Table(name = "device_status")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)//name defined by app user
    private String name;

    private Boolean openStatus;

    @Column(unique = true)           //device name
    private String sn;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)   //device type
    private DeviceType deviceType;

    private long startTime ;

    @Column(nullable = false)
    private String gateWay;

    private Long updateTime;

    public Device(){}

    public Device(String name, long startTime, DeviceType type, String sn, String gateWay){
        this.name = name;
        this.startTime = startTime;
        this.deviceType = type;
        this.sn = sn;
        this.gateWay = gateWay;
        this.updateTime = 1l;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Boolean openStatus) {
        this.openStatus = openStatus;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getGateWay() {
        return gateWay;
    }

    public void setGateWay(String gateWay) {
        this.gateWay = gateWay;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString(){
        return "name: " + this.name + " type: " + this.deviceType.toString() + " sn: " + this.sn +
                " gateWay: " + this.gateWay;
    }

}
