package cn.xiaoguo.controller;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.User;
import cn.xiaoguo.enums.AppHttpCodeEnum;
import cn.xiaoguo.exception.SystemException;
import cn.xiaoguo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

/**
 * @ClassName UserController
 * @Description 用户控制层
 * @Author 小果
 * @Date 2024/6/7 17:21
 * @Version 1.0
 */


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult UserLogin(@RequestBody User user) throws NoSuchAlgorithmException {
        if (!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return userService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult UserLogout(){
        return userService.logout();
    }

    @PostMapping("/register")
    public ResponseResult UserRegister(@RequestBody User user){
        return userService.userRegister(user);
    }

}
