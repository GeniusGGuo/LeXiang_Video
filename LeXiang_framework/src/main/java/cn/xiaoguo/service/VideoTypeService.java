package cn.xiaoguo.service;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.VideoType;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (VideoType)表服务接口
 *
 * @author makejava
 * @since 2024-06-24 10:57:34
 */
public interface VideoTypeService extends IService<VideoType> {

    ResponseResult getParentType();

    ResponseResult getClassify(int pid);
}

