package cn.xiaoguo.service.movieAndType;

import cn.xiaoguo.domain.entity.movieAndType.MovieAndYear;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (MovieAndYear)表服务接口
 *
 * @author makejava
 * @since 2024-07-31 22:02:39
 */
public interface MovieAndYearService extends IService<MovieAndYear> {

    void addMovieYearOne(Integer videoId, Integer videoYearId);
}

