package cn.xiaoguo.mapper;

import cn.xiaoguo.domain.entity.video.VideoInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * (VideoInfo)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-31 21:58:36
 */
@Mapper
public interface VideoInfoMapper extends BaseMapper<VideoInfo> {

}
