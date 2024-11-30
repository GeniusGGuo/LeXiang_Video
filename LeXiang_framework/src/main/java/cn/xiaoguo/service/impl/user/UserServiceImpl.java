package cn.xiaoguo.service.impl.user;

import cn.xiaoguo.domain.entity.user.LoginUser;
import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.user.User;
import cn.xiaoguo.domain.vo.LoginUserVo;
import cn.xiaoguo.enums.AppHttpCodeEnum;
import cn.xiaoguo.exception.SystemException;
import cn.xiaoguo.mapper.UserMapper;
import cn.xiaoguo.service.user.UserService;
import cn.xiaoguo.utils.JwtUtil;
import cn.xiaoguo.utils.RedisCache;
import cn.xiaoguo.utils.UuidUtil;
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
import java.util.Objects;
import java.util.UUID;

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
        String id = user.getUser().getId();
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
        user.setId(UuidUtil.getUUID());
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectOne() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        User user = getById(loginUser.getUser().getId());
        return ResponseResult.okResult(user);
    }

    @Override
    public ResponseResult updateUser(Long id,User user) {
        User user1 = getById(id);
        if(user1.getUserName().equals(user.getUserName())){
            user1.setUserName(user.getUserName());
        }else if (userNameEXist(user.getUserName())) {
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }else {
            user1.setUserName(user.getUserName());
        }
        if (user1.getNickName().equals(user.getNickName())){
            user1.setNickName(user.getNickName());
        }else if(nickNameEXist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }else {
            user1.setNickName(user.getNickName());
        }
        if (user1.getEmail().equals(user.getEmail())){
            user1.setEmail(user.getEmail());
        }else if (emailExist(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }else {
            user1.setEmail(user.getEmail());
        }

        user1.setSex(user.getSex());
        updateById(user1);
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
