package dianfeng.iot.openstatus.common;

import ywh.common.util.exception.DescribeException;
import ywh.common.util.exception.ExceptionEnum;

import java.util.HashMap;
import java.util.Map;

public enum DeviceType {
    unknown(0,"unknown"),
    新风机(1,"新风机"),
    空调(2,"空调"),
    地暖(3,"地暖"),
    主机(4,"主机"),
    热水器(5,"热水器"),
    电表(6,"电表");

    private static Map<Integer, DeviceType> map = new HashMap<Integer, DeviceType>();
    static{
        for(DeviceType indexType : DeviceType.values()){
            map.put(indexType.getValue(), indexType);
        }
    }

    private int value;
    private String alias;

    private DeviceType(int value, String alias){
        this.value = value;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public static DeviceType get(String name){
        try {
            return DeviceType.valueOf(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static DeviceType convert(int value){
        DeviceType res = map.get(value);
        if(res == null) throw new DescribeException("unsupport device typee" + value, ExceptionEnum.UNKNOW_ERROR.getCode());
        return res;
    }


}
