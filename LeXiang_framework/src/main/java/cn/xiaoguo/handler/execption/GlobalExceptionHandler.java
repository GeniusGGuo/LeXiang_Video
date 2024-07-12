package cn.xiaoguo.handler.execption;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.enums.AppHttpCodeEnum;
import cn.xiaoguo.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName GlobalExceptionHandler
 * @Description TODO
 * @Author 小果
 * @Date 2024/6/14 23:05
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常！{}",e);
        //从异常对象中获取信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult ExceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了异常！{}",e);
        //从异常对象中获取信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }

}
