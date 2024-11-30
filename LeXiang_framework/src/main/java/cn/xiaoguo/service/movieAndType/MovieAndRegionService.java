package cn.xiaoguo.service.movieAndType;

import cn.xiaoguo.domain.entity.movieAndType.MovieAndRegion;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (MovieAndRegion)表服务接口
 *
 * @author makejava
 * @since 2024-07-31 22:02:04
 */
public interface MovieAndRegionService extends IService<MovieAndRegion> {

    void addMovieRegion(Integer videoId, Integer pid);
}

