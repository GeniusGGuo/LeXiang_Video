package cn.xiaoguo.service.type;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.type.VideoTypeYear;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (VideoTypeYear)表服务接口
 *
 * @author makejava
 * @since 2024-07-15 18:16:37
 */
public interface VideoTypeYearService extends IService<VideoTypeYear> {

    ResponseResult getTypeYear(int id);

    Integer addYear(Integer pid, String movieYear);

}
