package cn.tycoding.service.impl;

import cn.tycoding.mapper.DeviceMapper;
import cn.tycoding.pojo.Device;
import cn.tycoding.pojo.PageBean;
import cn.tycoding.service.DeviceService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户的service层
 * @author tycoding
 * @date 21-5-15
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    //注入
    @Autowired
    private DeviceMapper deviceMapper;

    public void create(Device device) {
        deviceMapper.create(device);
    }

    public void delete(Long id) {
        deviceMapper.delete(id);
    }

    public List<Device> findAll() {
        return null;
    }

    public Device findById(Long id) {
        return deviceMapper.findById(id);
    }

    public void update(Device device) {
        deviceMapper.update(device);
    }

    /**
     * 分页查询-条件查询方法
     *
     * @param device 查询条件
     * @param pageCode 当前页
     * @param pageSize 每页的记录数
     * @return
     */
    public PageBean findByPage(Device device, int pageCode, int pageSize) {
        //使用Mybatis分页插件
        PageHelper.startPage(pageCode, pageSize);

        //调用分页查询方法，其实就是查询所有数据，mybatis自动帮我们进行分页计算
        Page<Device> page = deviceMapper.findByPage(device);

        return new PageBean(pageCode, (int)Math.ceil((double)(page.getTotal() / (double)pageSize)), (int)page.getTotal(), pageSize, page.getResult());
    }
}
