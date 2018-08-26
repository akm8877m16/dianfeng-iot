package dianfeng.iot.openstatus.entity;

import dianfeng.iot.openstatus.common.DeviceType;
import io.swagger.models.auth.In;
import ywh.common.redis.annotation.RedisField;
import ywh.common.redis.annotation.RedisId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "device_status")
public class Device implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RedisId
    private Long id;

    @Column(nullable = false)//name defined by app user
    @RedisField
    private String name;

    @RedisField
    private Boolean openStatus;

    @Column(unique = true)//device name
    @RedisField(inUniqueKey = true)
    private String sn;

    @Column(nullable = false)
    @RedisField
    private Integer deviceType;

    @RedisField
    private long startTime ;

    @Column(nullable = false)
    @RedisField(inUniqueKey = true)
    private String gateWay;

    @RedisField
    private Long updateTime;

    public Device(){}

    public Device(String name, long startTime, int type, String sn, String gateWay){
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

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
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
        return "name: " + this.name + " type: " + this.deviceType + " sn: " + this.sn +
                " gateWay: " + this.gateWay +" openStatus: "+this.openStatus+" updateTime: " + this.updateTime;
    }

}
