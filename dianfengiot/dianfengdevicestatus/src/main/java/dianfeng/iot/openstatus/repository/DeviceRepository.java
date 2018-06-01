package dianfeng.iot.openstatus.repository;

import dianfeng.iot.openstatus.entity.Device;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    // 使用 p0 做key  这里碰巧设备名称就可以作为key 来唯一标识
    @Cacheable(value = "devicesDetail", key = "'device:status:'+#p0", unless="#result==null")
    Device findBySn(String name);


    @Override
    @CachePut(value = "devicesDetail", key = "'device:status:'+#p0.sn")
    Device save(Device device);


    @Cacheable(value = "gateWayDetail", key = "'gateway:device:'+#p0", unless="#result==null")
    Device[] findByGateWay(String gateWay);

}
