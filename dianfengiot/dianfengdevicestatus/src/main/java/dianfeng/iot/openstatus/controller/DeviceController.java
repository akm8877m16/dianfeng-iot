package dianfeng.iot.openstatus.controller;

import dianfeng.iot.openstatus.bean.DeviceBean;
import dianfeng.iot.openstatus.entity.Device;
import dianfeng.iot.openstatus.service.DeviceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ywh.common.util.exception.DescribeException;
import ywh.common.util.response.Msg;
import ywh.common.util.response.ResultUtil;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "MQTT服务接口", description = "MQTT服务模块相关接口")
@RestController()
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @ApiOperation(value = "返回设备状态 废弃",notes = "openStatus 0: 关, 1: 开, name: 设备名称,用户可改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceName", value = "设备编号,不区分大小写", required = true, dataType = "String")
    })
    @RequestMapping(value = "/status/{deviceName}", method = RequestMethod.GET)
    public Msg getDeviceStatus(@PathVariable(name = "deviceName") String deviceName) {
        Device device = deviceService.findBySn(deviceName.toLowerCase());
        if(device != null){
            return ResultUtil.success(device);
        }
        return ResultUtil.success("device unknown");
    }

    @ApiOperation(value = "返回指定网关下所有设备的状态 废弃")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gateWayName", value = "网关编号,不区分大小写", required = true, dataType = "String")
    })
    @RequestMapping(value = "/status/all/{gateWayName}", method = RequestMethod.GET)
    public Msg resetDevice(@PathVariable(name = "gateWayName") String gateWayName) {
        Device[] devices = deviceService.findByGateWay(gateWayName);
        if(devices != null){
            return ResultUtil.success(devices);
        }else{
            return ResultUtil.success("no device available");
        }
    }

    @ApiOperation(value= "自定义设备名字 暂时停用")
    @PostMapping(value = "/rename")
    public Msg rename(@RequestBody @ApiParam(name="设备对象",value="传入json格式,都必填",required=true) DeviceBean deviceBean){
        String sn = deviceBean.getSn();
        String name = deviceBean.getName();
        if(sn == null){
            return  ResultUtil.success("sn can not be null");
        }
        if(name == null){
            return ResultUtil.success("name can not be null");
        }
        Device device = deviceService.findBySn(sn);
        if(device == null){
            return ResultUtil.success("no device available");
        }
        device.setName(deviceBean.getName());
        deviceService.update(device);
        return ResultUtil.success(device);

    }

    @ApiOperation(value = "返回设备状态 新",notes = "openStatus 0: 关, 1: 开, name: 设备名称,用户可改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceName", value = "设备编号,不区分大小写", required = true, dataType = "String")
    })
    @RequestMapping(value = "/status/new/{deviceName}", method = RequestMethod.GET)
    public Msg getDeviceStatusNew(@PathVariable(name = "deviceName") String deviceName) {
        try{
            boolean res = deviceService.findStatusBySn(deviceName.toLowerCase());
            return ResultUtil.success(res);
        }catch (DescribeException exp){
            return ResultUtil.error(exp.getCode(),exp.getMessage());
        }
    }

    @ApiOperation(value = "批量返回设备状态 新",notes = "openStatus 0: 关, 1: 开")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceList", value = "设备编号,用逗号分隔", required = true, dataType = "String",example = "xxxxxx,xxxxxx,xxxxxx")
    })
    @RequestMapping(value = "/status/new/batch", method = RequestMethod.POST)
    public Msg getDeviceStatusBatch(@RequestParam( value = "deviceList") String deviceList) {
            String[] devices = deviceList.split(",");
            Map res = new HashMap();
            boolean result = false;
            for(int i = 0;i < devices.length;i++){
                try{
                    result = deviceService.findStatusBySn(devices[i]);
                    res.put(devices[i],result);
                }catch (DescribeException exp){
                    res.put(devices[i],false);
                }
            }
            return ResultUtil.success(res);
    }
}