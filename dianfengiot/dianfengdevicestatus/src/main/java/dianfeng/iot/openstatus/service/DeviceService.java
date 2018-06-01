package dianfeng.iot.openstatus.service;

import dianfeng.iot.openstatus.entity.Device;

public interface DeviceService {

    Device findBySn(String sn);

    Device save(Device device);

    Device[] findByGateWay(String gateWay);

}
