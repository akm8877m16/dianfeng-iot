package dianfeng.iot.openstatus.common;

import java.util.HashMap;
import java.util.Map;

public enum DeviceType {
    unknown,
    新风机,
    空调,
    地暖,
    主机,
    热水器,
    电表;

    // Implementing a fromString method on an enum type
    private static final Map<String, DeviceType> stringToEnum = new HashMap <String, DeviceType>();
    static {
        // Initialize map from constant name to enum constant
        for(DeviceType deviceType : values()) {
            stringToEnum.put(deviceType.toString(), deviceType);
        }
    }

    // Returns deviceType for string, or null if string is invalid
    public static DeviceType fromString(String symbol) {
        return stringToEnum.get(symbol);
    }

    @Override
    public String toString(){
        return this.name();
    }


}
