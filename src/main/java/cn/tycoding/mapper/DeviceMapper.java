 package cn.tycoding.mapper;

import cn.tycoding.pojo.Device;
import com.github.pagehelper.Page;

/**
 * @author tycoding
 * @date 21-5-15下午9:15
        */
public interface DeviceMapper {

    void create(Device device);

    void delete(Long id);

    Device findById(Long id);

    void update(Device device);

    Page<Device> findByPage(Device device);

//    int selectCount();

//    List<Customer> findCondition(Map<String,Object> conMap);
}
