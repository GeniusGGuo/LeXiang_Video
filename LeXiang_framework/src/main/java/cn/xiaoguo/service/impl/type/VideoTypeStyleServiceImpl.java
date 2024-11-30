package cn.xiaoguo.service.impl.type;

import cn.xiaoguo.constant.SystemConstants;
import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.type.VideoRegionType;
import cn.xiaoguo.domain.entity.type.VideoTypeStyle;
import cn.xiaoguo.domain.entity.type.VideoTypeYear;
import cn.xiaoguo.domain.entity.video.VideoInfo;
import cn.xiaoguo.mapper.VideoTypeStyleMapper;
import cn.xiaoguo.service.movieAndType.MovieAndStyleService;
import cn.xiaoguo.service.type.VideoTypeRegionService;
import cn.xiaoguo.service.type.VideoTypeStyleService;
import cn.xiaoguo.service.video.VideoInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * (VideoTypeStyle)表服务实现类
 *
 * @author makejava
 * @since 2024-07-15 18:15:48
 */
@Service("videoTypeStyleService")
public class VideoTypeStyleServiceImpl extends ServiceImpl<VideoTypeStyleMapper, VideoTypeStyle> implements VideoTypeStyleService {


    @Override
    public ResponseResult getTypeStyle(int id) {
        LambdaQueryWrapper<VideoTypeStyle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoTypeStyle::getStatus, SystemConstants.VIDEO_TYPE_STATUS);
        wrapper.eq(VideoTypeStyle::getTypePid, id);
        List<VideoTypeStyle> list = list(wrapper);
        return ResponseResult.okResult(list);
    }

    @Override
    public Integer addStyle(String style, Integer pid) {
        VideoTypeStyle videoTypeStyle = new VideoTypeStyle();
        videoTypeStyle.setTypePid(pid);
        videoTypeStyle.setTypeStyleName(style);
        save(videoTypeStyle);
        LambdaQueryWrapper<VideoTypeStyle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoTypeStyle::getTypePid,1);
        wrapper.eq(VideoTypeStyle::getTypeStyleName,style);
        VideoTypeStyle typeStyle = getOne(wrapper);
        return typeStyle.getId();
    }

}
