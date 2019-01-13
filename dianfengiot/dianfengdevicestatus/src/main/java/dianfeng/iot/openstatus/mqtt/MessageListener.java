package dianfeng.iot.openstatus.mqtt;

import dianfeng.iot.openstatus.entity.Device;
import dianfeng.iot.openstatus.service.DeviceService;
import dianfeng.iot.openstatus.util.PayloadUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageListener implements Runnable{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    String topic;
    MqttMessage message;
    DeviceService deviceService;

    public MessageListener(String topic, MqttMessage message,DeviceService deviceService){
        this.topic = topic;
        this.message = message;
        this.deviceService = deviceService;
    }

    @Override
    public void run() {
        String gateway = topic.substring(2).toUpperCase();
        //air condition
        byte[] data = message.getPayload();

        if(PayloadUtil.deviceTypeCheck(data[0])){
            boolean openStatus = PayloadUtil.getOpenStatus(data);
            String deviceName = PayloadUtil.getDeviceName(message.getPayload());
            deviceService.saveStatus(deviceName,openStatus);
            logger.info(gateway+" message handle success");
        }
    }
}
