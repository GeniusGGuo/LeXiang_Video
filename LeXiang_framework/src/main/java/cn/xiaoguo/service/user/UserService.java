package cn.xiaoguo.service.user;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.user.User;

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

    ResponseResult selectOne();

    ResponseResult updateUser(Long id,User user);

}

