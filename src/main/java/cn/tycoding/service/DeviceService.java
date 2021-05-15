package cn.tycoding.service;

import cn.tycoding.pojo.Device;
import cn.tycoding.pojo.PageBean;

/**
 * @author tycoding
 * @date 21-5-15
 */
public interface DeviceService extends BaseService<Device> {

    /**
     * 分页查询
     * @param device 查询条件
     * @param pageCode 当前页
     * @param pageSize 每页的记录数
     * @return
     */
    PageBean findByPage(Device device, int pageCode, int pageSize);

}
