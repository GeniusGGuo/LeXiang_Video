package cn.xiaoguo.mapper;

import cn.xiaoguo.domain.entity.movieAndType.MovieAndYear;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * (MovieAndYear)表数据库访问层
 *
 * @author makejava
 * @since 2024-07-31 22:02:39
 */
@Mapper
public interface MovieAndYearMapper extends BaseMapper<MovieAndYear> {

}
