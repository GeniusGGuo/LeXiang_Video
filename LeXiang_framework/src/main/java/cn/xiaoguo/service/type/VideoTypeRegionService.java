package cn.xiaoguo.service.type;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.type.VideoRegionType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * (VideoType)表服务接口
 *
 * @author makejava
 * @since 2024-06-24 10:57:34
 */
public interface VideoTypeRegionService extends IService<VideoRegionType> {

    List<VideoRegionType> getParentType();

    List<VideoRegionType> getTypeRegion(int pid);

    ResponseResult addClassifyList(List<VideoRegionType> videoRegionTypeList);

    Integer getTypeId(String type);

}

