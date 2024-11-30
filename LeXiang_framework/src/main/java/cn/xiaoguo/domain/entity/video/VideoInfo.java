package cn.xiaoguo.domain.entity.video;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (VideoInfo)表实体类
 *
 * @author makejava
 * @since 2024-07-31 21:58:36
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("video_info")
public class VideoInfo  {
//主键@TableId
    private Integer id;

//虎牙资源采集id
    private Integer hyId;
//影视名称
    private String movieName;
    //影片别名
    private String movieAlias;
//导演名字
    private String directorName;
//演员名字
    private String performerName;
 //影片集数
    private String movieEpisodes;
//影片类型
    private String movieStyle;
//影片地区
    private String movieRegion;
//上映年份
    private String movieYear;
//影片图像
    private String movieImg;
//影片概要
    private String movieSynopsis;
//状态(0为正常1为禁用)
    private Integer movieStatus;



}
