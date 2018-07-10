package dianfeng.iot.openstatus.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ywh.common.mqtt.MQTTSubscriber;

@Component
public class MessageListener implements Runnable{

    @Autowired
    MQTTSubscriberImpl mqttSubscriber;

    @Override
    public void run() {
        mqttSubscriber.subscribeTopic("M/#");//receive data M  send data D
        while(true) {

        }
    }
}
