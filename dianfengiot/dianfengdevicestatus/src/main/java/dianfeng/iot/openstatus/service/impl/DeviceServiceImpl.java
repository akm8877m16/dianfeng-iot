package dianfeng.iot.openstatus.service.impl;

import dianfeng.iot.openstatus.entity.Device;
import dianfeng.iot.openstatus.mqtt.MQTTSubscriberImpl;
import dianfeng.iot.openstatus.repository.DeviceRepository;
import dianfeng.iot.openstatus.service.DeviceService;
import dianfeng.iot.openstatus.service.rao.DeviceRaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ywh.common.redis.globalIdGenerator.IdGenerator;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Resource
    private DeviceRaoImpl deviceRao;

    @Resource
    private IdGenerator idGenerator;

    @Override
    public Device findBySn(String name){

        List<Device> deviceList = deviceRao.findByKeyPattern("*"+name+"*");
        if(deviceList.size()>0){
            return deviceList.get(0);
        }
        //get it from dao and add to redis
        //Device result = deviceRepository.findBySn(name);
        //if(result != null){
        //    deviceRao.add(result);
        //    return result;
        //}
        return null;
    }

    @Override
    public Device findByUnique(Device device){
        Device result = deviceRao.getUnique(device);
        return result;
    }

    @Override
    public void save(Device device){
        //Device result = deviceRepository.save(device);
        long id = idGenerator.generatNewId();
        device.setId(id);
        deviceRao.add(device);
    }

    @Override
    public void update(Device device){
        deviceRao.update(device);
    }

    @Override
    public Device[] findByGateWay(String gateWay){
        //currently do not go to database because rao and dao are not unified
        Iterable<Device> devices = deviceRao.findByKeyPattern("*"+gateWay+"*");
        Integer count = deviceRao.getKeysRao().keysCount("*"+gateWay+"*");
        Device[] result = new Device[count];
        List<Device> deviceList = new ArrayList<>();
        for(Device deviceFound : devices){
            deviceList.add(deviceFound);
        }
        deviceList.toArray(result);
        return result;

    }
}