package cn.xiaoguo.domain.entity.movieAndType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (MovieAndStyle)表实体类
 *
 * @author makejava
 * @since 2024-07-31 22:02:24
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("movie_and_style")
public class MovieAndStyle  {
@TableId
    private Integer id;


    private Integer videoId;

    private Integer videoStyleId;



}
