package cn.xiaoguo.service.impl.type;

import cn.xiaoguo.constant.SystemConstants;
import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.movieAndType.MovieAndYear;
import cn.xiaoguo.domain.entity.type.VideoRegionType;
import cn.xiaoguo.domain.entity.type.VideoTypeStyle;
import cn.xiaoguo.domain.entity.type.VideoTypeYear;
import cn.xiaoguo.domain.entity.video.VideoInfo;
import cn.xiaoguo.mapper.VideoTypeYearMapper;
import cn.xiaoguo.service.movieAndType.MovieAndYearService;
import cn.xiaoguo.service.type.VideoTypeRegionService;
import cn.xiaoguo.service.type.VideoTypeYearService;
import cn.xiaoguo.service.video.VideoInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * (VideoTypeYear)表服务实现类
 *
 * @author makejava
 * @since 2024-07-15 18:16:37
 */
@Service("videoTypeYearService")
public class VideoTypeYearServiceImpl extends ServiceImpl<VideoTypeYearMapper, VideoTypeYear> implements VideoTypeYearService {

    @Override
    public ResponseResult getTypeYear(int id) {
        LambdaQueryWrapper<VideoTypeYear> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoTypeYear::getStatus, SystemConstants.VIDEO_TYPE_STATUS);
        wrapper.eq(VideoTypeYear::getTypePid, id);
        List<VideoTypeYear> list = list(wrapper);
        list.sort(Comparator.comparing(VideoTypeYear::getTypeYearName).reversed());
        List<VideoTypeYear> subbedList = list.subList(0, Math.min(list.size(), 15));
        return ResponseResult.okResult(subbedList);
    }

    @Override
    public Integer addYear(Integer pid, String movieYear) {
        VideoTypeYear videoTypeYear = new VideoTypeYear();
        videoTypeYear.setTypePid(pid);
        videoTypeYear.setTypeYearName(movieYear);
        save(videoTypeYear);
        LambdaQueryWrapper<VideoTypeYear> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoTypeYear::getTypeYearName,movieYear);
        wrapper.eq(VideoTypeYear::getTypePid,pid);
        VideoTypeYear one = getOne(wrapper);
        return one.getId();
    }




}
