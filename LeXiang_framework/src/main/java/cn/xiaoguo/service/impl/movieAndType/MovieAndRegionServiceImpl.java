package cn.xiaoguo.service.impl.movieAndType;

import cn.xiaoguo.domain.entity.movieAndType.MovieAndRegion;
import cn.xiaoguo.mapper.MovieAndRegionMapper;
import cn.xiaoguo.service.movieAndType.MovieAndRegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * (MovieAndRegion)表服务实现类
 *
 * @author makejava
 * @since 2024-07-31 22:02:04
 */
@Service("movieAndRegionService")
public class MovieAndRegionServiceImpl extends ServiceImpl<MovieAndRegionMapper, MovieAndRegion> implements MovieAndRegionService {

    @Override
    public void addMovieRegion(Integer videoId, Integer pid) {
        MovieAndRegion movieAndRegion = new MovieAndRegion();
        movieAndRegion.setVideoId(videoId);
        movieAndRegion.setVideoRegionId(pid);
        save(movieAndRegion);
    }
}
