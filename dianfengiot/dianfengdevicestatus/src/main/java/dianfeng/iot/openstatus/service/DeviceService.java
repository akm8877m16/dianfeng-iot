package dianfeng.iot.openstatus.service;

import dianfeng.iot.openstatus.entity.Device;

public interface DeviceService {

    Device findBySn(String sn);

    void save(Device device);

    void update(Device device);

    Device[] findByGateWay(String gateWay);

    Device findByUnique(Device device);

}
