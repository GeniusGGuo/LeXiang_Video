package cn.xiaoguo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName movieInfoVo
 * @Description TODO
 * @Author 小果
 * @Date 2024/8/1 18:24
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieInfoVo {
    //主键@TableId
    private Integer id;

    //影片父id
    private Integer Pid;
    //虎牙资源采集id
    private String hyId;
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
    //影片链接
    private String link;
    //影片概要
    private String movieSynopsis;
    //状态(0为正常1为禁用)
    private Integer movieStatus;


}
