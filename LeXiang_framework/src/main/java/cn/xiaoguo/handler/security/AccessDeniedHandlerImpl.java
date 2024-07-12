package cn.xiaoguo.handler.security;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.enums.AppHttpCodeEnum;
import cn.xiaoguo.utils.WebUtils;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName AccessDeniedHandlerImpl
 * @Description   没有权限访问时，返回的json数据
 * @Author 小果
 * @Date 2024/6/14 23:03
 * @Version 1.0
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }




}
