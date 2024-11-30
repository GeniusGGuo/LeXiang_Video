package cn.xiaoguo.domain.entity.movieAndType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (MovieAndYear)表实体类
 *
 * @author makejava
 * @since 2024-07-31 22:02:39
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("movie_and_year")
public class MovieAndYear  {
@TableId
    private Integer id;


    private Integer videoId;

    private Integer videoYearId;



}
