package dianfeng.iot.openstatus.util;

import dianfeng.iot.openstatus.common.DeviceType;

public class PayloadUtil {
    public static final int PAYLOAD_HEAD_AIRCONDITION = 106;
    public static final int PAYLOAD_LENGTH_STATUS_AIRCONDITION =28 ;
    public static final int PAYLOAD_LENGTH_OFF_AIRCONDITION =8 ;

    public static final int PAYLOAD_HEAD_NEWFAN = 74;
    public static final int PAYLOAD_LENGTH_STATUS_NEWFAN = 14;
    public static final int PAYLOAD_LENGTH_OFF_NEWFAN = 9;

    public static final int PAYLOAD_HEAD_HOST = 26;
    public static final int PAYLOAD_LENGTH_STATUS_HOST = 18;
    public static final int PAYLOAD_LENGTH_OFF_HOST = 8;

    public static final int PAYLOAD_HEAD_HEAT = 90;
    public static final int PAYLOAD_LENGTH_STATUS_HEAT = 12;
    public static final int PAYLOAD_LENGTH_OFF_HEAT = 8;

    public PayloadUtil(){}

    public static final boolean deviceTypeCheck(int head){
        if(head == PAYLOAD_HEAD_HOST || head == PAYLOAD_HEAD_HEAT || head == PAYLOAD_HEAD_NEWFAN || head == PAYLOAD_HEAD_AIRCONDITION){
            return true;
        }
        return false;
    }

    public static  DeviceType getDeviceType(int head){
        switch (head){
            case PAYLOAD_HEAD_AIRCONDITION:
                return DeviceType.空调;
            case PAYLOAD_HEAD_HEAT:
                return DeviceType.地暖;
            case PAYLOAD_HEAD_HOST:
                return DeviceType.主机;
            case PAYLOAD_HEAD_NEWFAN:
                return DeviceType.新风机;
            default:
                return DeviceType.unknown;
        }
    }

    public static final boolean getOpenStatus(byte[] payload){
        int head = payload[0];
        if(head == PAYLOAD_HEAD_AIRCONDITION){
            if(payload.length >= PAYLOAD_LENGTH_STATUS_AIRCONDITION){
                return int2Bool(payload[7]);
            }

            if(payload.length >= PAYLOAD_LENGTH_OFF_AIRCONDITION){
                return int2Bool(payload[6]);
            }

        }
        if(head == PAYLOAD_HEAD_NEWFAN){
            if(payload.length >= PAYLOAD_LENGTH_STATUS_NEWFAN){
                return int2Bool(payload[6]);
            }

            if(payload.length == PAYLOAD_LENGTH_OFF_NEWFAN){
                if(payload[5] == 0 && payload[6] == 255){
                    return false;
                }else{
                    return true;
                }
            }

        }
        if(head == PAYLOAD_HEAD_HEAT){
            if(payload.length >= PAYLOAD_LENGTH_STATUS_HEAT){
                return int2Bool(payload[6]);
            }

            if(payload.length >= PAYLOAD_LENGTH_OFF_HEAT){
                return int2Bool(payload[6]);
            }

        }
        if(head == PAYLOAD_HEAD_HOST){
            if(payload.length >= PAYLOAD_LENGTH_STATUS_HOST){
                return int2Bool(payload[9]);
            }

            if(payload.length >= PAYLOAD_LENGTH_OFF_HOST){
                return int2Bool(payload[6]);
            }

        }

        return false;
    }

    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     * * @param src byte[] data
     * @return hex string
     */
    public static final String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static final String getDeviceName(byte[] src){
        byte[] device=new byte[4];
        System.arraycopy(src, 1, device, 0, 4);
        return bytesToHexString(device);
    }

    private static final Boolean int2Bool(int value){
        return value > 0 ? true:false;
    }

}