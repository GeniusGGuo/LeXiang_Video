package cn.xiaoguo.service;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.User;

import java.security.NoSuchAlgorithmException;


/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2024-06-07 17:20:28
 */

public interface UserService {

    ResponseResult login(User user) throws NoSuchAlgorithmException;

    ResponseResult logout();

    ResponseResult userRegister(User user);
}

