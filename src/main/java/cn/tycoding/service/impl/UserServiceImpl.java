package cn.tycoding.service.impl;

import cn.tycoding.mapper.UserLoginMapper;
import cn.tycoding.pojo.UserLogin;
import cn.tycoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tycoding
 * @date
 */
@Service
public class UserServiceImpl implements UserService {

    //注入
    @Autowired
    private UserLoginMapper userMapper;

    /**
     * 用户登录的方法
     */
    public UserLogin login(String username) {
        return userMapper.login(username);
    }

    public List<UserLogin> findAll() {
        return null;
    }

    public UserLogin findById(Long id) {
        return null;
    }

    public void create(UserLogin user) {

    }

    public void delete(Long id) {

    }

    public void update(UserLogin user) {

    }
}
