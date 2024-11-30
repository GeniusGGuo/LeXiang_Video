package cn.xiaoguo.domain.entity.crawler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (VideoHyCrawling)表实体类
 *
 * @author makejava
 * @since 2024-07-31 16:03:30
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("video_hy_crawling")
public class VideoHyCrawling  {
@TableId
    private Integer id;


    private String videoName;

    private String videoType;
    private Integer videoRegionId;



}
