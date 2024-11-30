package cn.xiaoguo.service.video;

import cn.xiaoguo.domain.entity.video.VideoLink;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * (VideoLink)表服务接口
 *
 * @author makejava
 * @since 2024-08-06 19:21:26
 */
public interface VideoLinkService extends IService<VideoLink> {

    void addLinkList(String link,Integer videoId);
}

