package cn.xiaoguo.service.movieAndType;

import cn.xiaoguo.domain.entity.movieAndType.MovieAndStyle;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (MovieAndStyle)表服务接口
 *
 * @author makejava
 * @since 2024-07-31 22:02:24
 */
public interface MovieAndStyleService extends IService<MovieAndStyle> {

    void addMovieStyleOne(Integer videoId, Integer styleId);
}

