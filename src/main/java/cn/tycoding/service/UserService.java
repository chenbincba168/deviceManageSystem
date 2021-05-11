package cn.tycoding.service;

import cn.tycoding.pojo.UserLogin;

/**
 * @author tycoding
 * @date 18-4-7下午9:09
 */
public interface UserService extends BaseService<UserLogin> {

    UserLogin login(String username);
}
