 package cn.tycoding.mapper;

import cn.tycoding.pojo.UserLogin;

/**
 * @author tycoding
 * @date 121-5-15下午9:15
 */
public interface UserLoginMapper {

    UserLogin login(String username);
}
