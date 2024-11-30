package cn.xiaoguo.domain.entity.type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (VideoTypeYear)表实体类
 *
 * @author makejava
 * @since 2024-07-15 18:16:37
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("video_type_year")
public class VideoTypeYear  {
//id@TableId
    private Integer id;

//父id
    private Integer typePid;
//年份分类名
    private String typeYearName;
//状态（0正常1禁用）
    private Integer status;



}
