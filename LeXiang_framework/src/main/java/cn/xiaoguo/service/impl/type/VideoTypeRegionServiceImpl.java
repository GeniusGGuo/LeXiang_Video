package cn.xiaoguo.service.impl.type;

import cn.xiaoguo.constant.SystemConstants;
import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.type.VideoRegionType;
import cn.xiaoguo.mapper.VideoTypeRegionMapper;
import cn.xiaoguo.service.type.VideoTypeRegionService;
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
public class VideoTypeRegionServiceImpl extends ServiceImpl<VideoTypeRegionMapper, VideoRegionType> implements VideoTypeRegionService {

    /**
     *
     * @author LuoGuo
     * @date 2024/7/7 9:20
     *
     * 获取主分类
     */
    @Override
    public List<VideoRegionType> getParentType() {
        LambdaQueryWrapper<VideoRegionType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoRegionType::getStatus, SystemConstants.VIDEO_TYPE_STATUS);
        wrapper.eq(VideoRegionType::getTypePid,SystemConstants.VIDEO_PARENT_ID);
        List<VideoRegionType> list = list(wrapper);
        return list;
    }

    /**
     *
     * @author LuoGuo
     * @date 2024/7/7 9:21
     * 更具父分类id获取子分类
     */
    @Override
    public List<VideoRegionType> getTypeRegion(int pid) {
        LambdaQueryWrapper<VideoRegionType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoRegionType::getTypePid,pid);
        wrapper.eq(VideoRegionType::getStatus, SystemConstants.VIDEO_TYPE_STATUS);
        List<VideoRegionType> typeList = list(wrapper);
        return typeList;
    }

    @Override
    public ResponseResult addClassifyList(List<VideoRegionType> videoRegionTypeList) {
        for (VideoRegionType videoRegionType : videoRegionTypeList) {
            save(videoRegionType);
        }
        return ResponseResult.okResult();
    }

    @Override
    public Integer getTypeId(String type) {
        LambdaQueryWrapper<VideoRegionType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoRegionType::getTypeRegionName,type);
        VideoRegionType one = getOne(wrapper);
        return one.getId();
    }




}

