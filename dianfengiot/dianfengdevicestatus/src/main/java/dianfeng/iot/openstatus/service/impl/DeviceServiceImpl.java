package dianfeng.iot.openstatus.service.impl;

import dianfeng.iot.openstatus.entity.Device;
import dianfeng.iot.openstatus.mqtt.MQTTSubscriberImpl;
import dianfeng.iot.openstatus.repository.DeviceRepository;
import dianfeng.iot.openstatus.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public Device findBySn(String name){
        return deviceRepository.findBySn(name);
    }

    @Override
    public Device save(Device device){
        return deviceRepository.save(device);
    }

    @Override
    public Device[] findByGateWay(String gateWay){
        return  deviceRepository.findByGateWay(gateWay);
    }
}