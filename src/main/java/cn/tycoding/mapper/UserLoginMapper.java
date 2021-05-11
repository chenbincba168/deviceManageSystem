package cn.tycoding.mapper;

import cn.tycoding.pojo.UserLogin;

/**
 * @author tycoding
 * @date 18-4-7下午9:10
 */
public interface UserLoginMapper {

    UserLogin login(String username);
}
