package cn.xiaoguo.service.video;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.type.VideoRegionType;
import cn.xiaoguo.domain.entity.video.VideoInfo;
import cn.xiaoguo.domain.vo.MovieInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * (VideoInfo)表服务接口
 *
 * @author makejava
 * @since 2024-07-31 21:58:39
 */
public interface VideoInfoService extends IService<VideoInfo> {

    Integer addMovies(MovieInfoVo movieInfoVo);

    ResponseResult getVideoInfoListByRegionName(List<VideoRegionType> typeRegion, Integer pageNum, Integer pageSize,String style,String year,String region);
}

