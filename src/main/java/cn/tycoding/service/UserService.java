package cn.tycoding.service;

import cn.tycoding.pojo.UserLogin;

/**
 * @author tycoding
 * @date 21-5-15下午9:09
 */
public interface UserService extends BaseService<UserLogin> {

    UserLogin login(String username);
}
