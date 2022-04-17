 package cn.tycoding.mapper;

import cn.tycoding.pojo.Customer;
import com.github.pagehelper.Page;

/**
 * @author tycoding
 * @date 21-5-15下午9:15
 */
public interface CustomerMapper {

    void create(Customer customer);

    void delete(Long id);

    Customer findById(Long id);

    void update(Customer customer);

    Page<Customer> findByPage(Customer customer);

//    int selectCount();

//    List<Customer> findCondition(Map<String,Object> conMap);
}
