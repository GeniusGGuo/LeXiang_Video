package cn.xiaoguo.service.crawler;

import cn.xiaoguo.domain.entity.crawler.VideoHyCrawling;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * (VideoHyCrawling)表服务接口
 *
 * @author makejava
 * @since 2024-07-31 17:28:49
 */
public interface VideoHyCrawlingService extends IService<VideoHyCrawling> {

    List<VideoHyCrawling> getAllMovie();
}

