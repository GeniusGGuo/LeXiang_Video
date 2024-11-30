package cn.xiaoguo.service.impl.movieAndType;

import cn.xiaoguo.domain.entity.movieAndType.MovieAndYear;
import cn.xiaoguo.mapper.MovieAndYearMapper;
import cn.xiaoguo.service.movieAndType.MovieAndYearService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (MovieAndYear)表服务实现类
 *
 * @author makejava
 * @since 2024-07-31 22:02:39
 */
@Service("movieAndYearService")
public class MovieAndYearServiceImpl extends ServiceImpl<MovieAndYearMapper, MovieAndYear> implements MovieAndYearService {

    @Override
    public void addMovieYearOne(Integer videoId, Integer videoYearId) {
        MovieAndYear movieAndYear = new MovieAndYear();
        movieAndYear.setVideoId(videoId);
        movieAndYear.setVideoYearId(videoYearId);
        save(movieAndYear);
    }
}
