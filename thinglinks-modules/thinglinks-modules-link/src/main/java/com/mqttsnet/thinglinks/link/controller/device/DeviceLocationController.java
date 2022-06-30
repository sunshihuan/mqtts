package com.mqttsnet.thinglinks.link.controller.device;

import com.mqttsnet.thinglinks.common.core.utils.poi.ExcelUtil;
import com.mqttsnet.thinglinks.common.core.web.controller.BaseController;
import com.mqttsnet.thinglinks.common.core.web.domain.AjaxResult;
import com.mqttsnet.thinglinks.common.core.web.page.TableDataInfo;
import com.mqttsnet.thinglinks.common.log.annotation.Log;
import com.mqttsnet.thinglinks.common.log.enums.BusinessType;
import com.mqttsnet.thinglinks.common.security.annotation.PreAuthorize;
import com.mqttsnet.thinglinks.link.api.domain.device.entity.DeviceLocation;
import com.mqttsnet.thinglinks.link.service.device.DeviceLocationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * (device_location)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("device_location")
public class DeviceLocationController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private DeviceLocationService deviceLocationService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public DeviceLocation selectOne(Integer id) {
        return deviceLocationService.selectByPrimaryKey(Long.valueOf(id));
    }

    /**
     * 查询设备位置列表
     */
    @PreAuthorize(hasPermi = "link:device_location:list")
    @GetMapping("/list")
    public TableDataInfo list(DeviceLocation deviceLocation)
    {
        startPage();
        List<DeviceLocation> list = deviceLocationService.selectDeviceLocationList(deviceLocation);
        return getDataTable(list);
    }

    /**
     * 导出设备位置列表
     */
    @PreAuthorize(hasPermi = "link:device_location:export")
    @Log(title = "设备位置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DeviceLocation deviceLocation) throws IOException
    {
        List<DeviceLocation> list = deviceLocationService.selectDeviceLocationList(deviceLocation);
        ExcelUtil<DeviceLocation> util = new ExcelUtil<DeviceLocation>(DeviceLocation.class);
        util.exportExcel(response, list, "设备位置数据");
    }

    /**
     * 新增设备位置
     */
    @PreAuthorize(hasPermi = "link:device_location:add")
    @Log(title = "设备位置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DeviceLocation deviceLocation)
    {
        return toAjax(deviceLocationService.insert(deviceLocation));
    }

    /**
     * 修改设备位置
     */
    @PreAuthorize(hasPermi = "link:device_location:edit")
    @Log(title = "设备位置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DeviceLocation deviceLocation)
    {
        return toAjax(deviceLocationService.updateByPrimaryKey(deviceLocation));
    }

    /**
     * 删除设备位置
     */
    @PreAuthorize(hasPermi = "link:device_location:remove")
    @Log(title = "设备位置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(deviceLocationService.deleteDeviceLocationByIds(ids));
    }
}
