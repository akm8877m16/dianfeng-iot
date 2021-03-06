package dianfeng.iot.openstatus.mqtt;

import dianfeng.iot.openstatus.entity.Device;
import dianfeng.iot.openstatus.service.DeviceService;
import dianfeng.iot.openstatus.util.PayloadUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import ywh.common.mqtt.MQTTSubscriber;
import ywh.common.mqtt.MqttConfig;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.*;

@Component
public class MQTTSubscriberImpl implements MQTTSubscriber,MqttConfig,MqttCallback,InitializingBean {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ywh.mqtt.broker:127.0.0.1}")
    private String broker = "39.104.49.84";

    @Value("${ywh.mqtt.qos:1}")
    private int qos = 1;

    @Value("${ywh.mqtt.port:1883}")
    private int port = 1883;

    @Value("${ywh.mqtt.userName:}")
    private String userName;

    @Value("${ywh.mqtt.password:}")
    private String password;

    private final String TCP = "tcp://";

    private ThreadPoolExecutor executor;

    @Autowired
    DeviceService deviceService;

    //@Resource
    //Executor taskExecutor;

    private String brokerUrl = null;
    final private String colon = ":";

    private MqttClient mqttClient = null;
    private MqttConnectOptions connectionOptions = null;
    private MemoryPersistence persistence = null;

    public MQTTSubscriberImpl() {
        this.config();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.
     * Throwable)
     */
    @Override
    public void connectionLost(Throwable cause) {
        logger.info("Connection Lost: " + cause.toString());
        cause.printStackTrace();
        try {
            this.mqttClient = new MqttClient(brokerUrl, "deviceStatus"+ UUID.randomUUID().toString(), persistence);
            this.mqttClient.setCallback(this);
            this.mqttClient.connect(this.connectionOptions);
            this.subscribeTopic("M/#");//receive data M  send data D
        }catch (MqttException e){
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String,
     * org.eclipse.paho.client.mqttv3.MqttMessage)
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
                MessageListener messageListener = new MessageListener(topic,message,deviceService);
                executor.execute(messageListener);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho
     * .client.mqttv3.IMqttDeliveryToken)
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Leave it blank for subscriber

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.monirthought.mqtt.subscriber.MQTTSubscriberBase#subscribeMessage(java.
     * lang.String)
     */
    @Override
    public void subscribeTopic(String topic) {
        try {
            this.mqttClient.subscribe(topic, this.qos);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    @Override
    public void unsubscribeTopic(String topic) {
        try {
            this.mqttClient.unsubscribe(topic);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.monirthought.mqtt.subscriber.MQTTSubscriberBase#disconnect()
     */
    public void disconnect() {
        try {
            this.mqttClient.disconnect();
            executor.awaitTermination(5,TimeUnit.SECONDS);
        } catch (MqttException exp) {
            logger.error("ERROR", exp);
        }catch (InterruptedException exp){
            logger.error("executor shutdown interruptedException",exp);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.monirthought.config.MQTTConfig#config(java.lang.String,
     * java.lang.Integer, java.lang.Boolean, java.lang.Boolean)
     */
    @Override
    public void config(String broker, Integer port, Boolean withUserNamePass) {

        String protocal = this.TCP;
        this.brokerUrl = protocal + this.broker + colon + port;
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();

        try {
            this.mqttClient = new MqttClient(brokerUrl, "deviceStatus"+ UUID.randomUUID().toString(), persistence);
            this.connectionOptions.setCleanSession(true);
            if (true == withUserNamePass) {
                if (!this.password.isEmpty()) {
                    this.connectionOptions.setPassword(this.password.toCharArray());
                }
                if (!this.userName.isEmpty()) {
                    this.connectionOptions.setUserName(this.userName);
                }
            }
            this.mqttClient.setCallback(this);
            this.mqttClient.connect(this.connectionOptions);
            this.subscribeTopic("M/#");//receive data M  send data D
        } catch (MqttException me) {
            me.printStackTrace();
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see com.monirthought.config.MQTTConfig#config()
     */
    @Override
    public void config() {

        this.brokerUrl = this.TCP + this.broker + colon + this.port;
        this.persistence = new MemoryPersistence();
        this.connectionOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(brokerUrl, MqttClient.generateClientId(), persistence);
            this.connectionOptions.setCleanSession(true);
            this.connectionOptions.setKeepAliveInterval(10);
            this.connectionOptions.setConnectionTimeout(20);
            this.connectionOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            this.mqttClient.setCallback(this);
            this.mqttClient.connect(this.connectionOptions);
            this.subscribeTopic("M/#");//receive data M  send data D
        } catch (MqttException me) {
            me.printStackTrace();
        }

    }

    @Override
    public void afterPropertiesSet() {
        executor = new ThreadPoolExecutor(10, 15, 1, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000), new DefaultThreadFactory("mqtt_message_handle_pool"), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    logger.error("too many mqtt messages");
            }
        });
    }

    @PreDestroy
    public void destroy(){
        logger.info("destroy method");
        if(mqttClient.isConnected()){
            disconnect();
        }
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }
}
