package cn.xiaoguo.service.impl.movieAndType;

import cn.xiaoguo.domain.entity.movieAndType.MovieAndStyle;
import cn.xiaoguo.mapper.MovieAndStyleMapper;
import cn.xiaoguo.service.movieAndType.MovieAndStyleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (MovieAndStyle)表服务实现类
 *
 * @author makejava
 * @since 2024-07-31 22:02:24
 */
@Service("movieAndStyleService")
public class MovieAndStyleServiceImpl extends ServiceImpl<MovieAndStyleMapper, MovieAndStyle> implements MovieAndStyleService {

    @Override
    public void addMovieStyleOne(Integer videoId, Integer styleId) {
        MovieAndStyle movieAndStyle = new MovieAndStyle();
        movieAndStyle.setVideoId(videoId);
        movieAndStyle.setVideoStyleId(styleId);
        save(movieAndStyle);
    }
}
