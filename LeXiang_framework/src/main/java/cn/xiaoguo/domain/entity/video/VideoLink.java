package cn.xiaoguo.domain.entity.video;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (VideoLink)表实体类
 *
 * @author makejava
 * @since 2024-08-06 19:21:25
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("video_link")
public class VideoLink  {
@TableId
    private Integer id;

//videoInfo的主键
    private Integer videoId;
//视频链接
    private String link;
//链接名字
    private String linkName;



}
