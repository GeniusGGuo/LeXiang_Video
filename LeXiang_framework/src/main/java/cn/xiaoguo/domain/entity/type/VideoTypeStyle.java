package cn.xiaoguo.domain.entity.type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (VideoTypeStyle)表实体类
 *
 * @author makejava
 * @since 2024-07-15 18:15:47
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("video_type_style")
public class VideoTypeStyle  {
//id@TableId
    private Integer id;

//父id
    private Integer typePid;
//风格名
    private String typeStyleName;
//状态（0正常1禁用）
    private Integer status;



}
