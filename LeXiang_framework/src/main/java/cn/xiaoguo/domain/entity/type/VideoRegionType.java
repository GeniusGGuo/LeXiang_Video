package cn.xiaoguo.domain.entity.type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (VideoType)表实体类
 *
 * @author makejava
 * @since 2024-06-24 10:57:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("video_type_region")
public class VideoRegionType {
@TableId
    private Integer id;

//分类名
    private String typeRegionName;
//父id(没有为0)
    private Integer typePid;
//状态（0为正常1为禁用）
    private Integer status;



}
