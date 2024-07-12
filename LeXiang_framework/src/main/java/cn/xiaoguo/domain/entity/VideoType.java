package cn.xiaoguo.domain.entity;


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
@TableName("video_type")
public class VideoType  {
@TableId
    private Long typeId;

//分类名
    private String typeName;
//父id(没有为0)
    private Long typePid;
//状态（0为正常1为禁用）
    private Integer status;



}
