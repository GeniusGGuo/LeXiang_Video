package cn.xiaoguo.service.type;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.type.VideoTypeStyle;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (VideoTypeStyle)表服务接口
 *
 * @author makejava
 * @since 2024-07-15 18:15:48
 */
public interface VideoTypeStyleService extends IService<VideoTypeStyle> {

    ResponseResult getTypeStyle(int id);

    Integer addStyle(String style, Integer pid);

}

