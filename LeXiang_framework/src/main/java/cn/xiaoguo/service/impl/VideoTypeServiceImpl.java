package cn.xiaoguo.service.impl;

import cn.xiaoguo.constant.SystemConstants;
import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.VideoType;
import cn.xiaoguo.mapper.VideoTypeMapper;
import cn.xiaoguo.service.VideoTypeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (VideoType)表服务实现类
 *
 * @author makejava
 * @since 2024-06-24 10:57:52
 */
@Service("videoTypeService")
public class VideoTypeServiceImpl extends ServiceImpl<VideoTypeMapper, VideoType> implements VideoTypeService {

    /**
     *
     * @author LuoGuo
     * @date 2024/7/7 9:20
     *
     * 获取主分类
     */
    @Override
    public ResponseResult getParentType() {
        LambdaQueryWrapper<VideoType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoType::getStatus, SystemConstants.VIDEO_TYPE_STATUS);
        wrapper.eq(VideoType::getTypePid,SystemConstants.VIDEO_PARENT_ID);
        List<VideoType> list = list(wrapper);
        return ResponseResult.okResult(list);
    }

    /**
     *
     * @author LuoGuo
     * @date 2024/7/7 9:21
     * 更具父分类id获取子分类
     */
    @Override
    public ResponseResult getClassify(int pid) {
        LambdaQueryWrapper<VideoType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoType::getTypePid,pid);
        wrapper.eq(VideoType::getStatus, SystemConstants.VIDEO_TYPE_STATUS);
        List<VideoType> typeList = list(wrapper);
        return ResponseResult.okResult(typeList);
    }


}

