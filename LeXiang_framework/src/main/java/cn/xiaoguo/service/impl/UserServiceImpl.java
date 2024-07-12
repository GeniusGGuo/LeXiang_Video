package cn.xiaoguo.service.impl;

import cn.xiaoguo.domain.entity.LoginUser;
import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.User;
import cn.xiaoguo.domain.vo.LoginUserVo;
import cn.xiaoguo.enums.AppHttpCodeEnum;
import cn.xiaoguo.exception.SystemException;
import cn.xiaoguo.mapper.UserMapper;
import cn.xiaoguo.service.UserService;
import cn.xiaoguo.utils.JwtUtil;
import cn.xiaoguo.utils.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Objects;

/**
 * @ClassName UserServiceImpl
 * @Description  用户服务实现类
 * @Author 小果
 * @Date 2024/6/7 19:36
 * @Version 1.0
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public ResponseResult login(User user) throws NoSuchAlgorithmException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authentication)){
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUser loginUser =  (LoginUser) authentication.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(id);
        //authenticate存入redis
        redisCache.setCacheObject("login:"+id,loginUser);
        LoginUserVo loginUserVo  = new LoginUserVo(jwt, loginUser.getUser());
        return new ResponseResult(200,"登陆成功",loginUserVo);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) authentication.getPrincipal();
        Long id = user.getUser().getId();
        redisCache.deleteObject("login:"+id);
        return new ResponseResult(200,"退出成功");
    }

    @Override
    public ResponseResult userRegister(User user) {
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }else if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }else if (!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }else if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(userNameEXist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }else if(nickNameEXist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }else if (emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
        return ResponseResult.okResult();
    }

    private boolean userNameEXist(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, userName);
        return count(wrapper)>0;
    }

    private boolean nickNameEXist(String nickName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNickName, nickName);
        return count(wrapper)>0;
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return count(wrapper)>0;
    }
}
