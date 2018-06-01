package dianfeng.iot.openstatus.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="设备对象",description="用于修改设备名称")
public class DeviceBean {
    @ApiModelProperty(value="设备编号",name="sn",required = true)
    private String sn;

    @ApiModelProperty(value="自定义设备名称",name="name",required = true)
    private String name;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
