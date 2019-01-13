package dianfeng.iot.openstatus.service.rao;

import dianfeng.iot.openstatus.entity.Device;
import ywh.common.redis.StringCacheRao;
import ywh.common.redis.impl.AbstractGenericInfoRao;

public class DeviceRaoImpl  extends AbstractGenericInfoRao<Device,String> {

    public String findStatusBySn(String sn) {
        StringCacheRao rao = this.getStringCacheRao();
        return rao.get(sn);
    }


    public boolean saveStatus(String sn, boolean status){
        StringCacheRao rao = this.getStringCacheRao();
        rao.set(sn,"",String.valueOf(status),this.getSeconds());
        return true;
    }

}
