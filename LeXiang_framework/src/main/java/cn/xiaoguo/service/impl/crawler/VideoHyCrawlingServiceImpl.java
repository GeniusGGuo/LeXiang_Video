package cn.xiaoguo.service.impl.crawler;

import cn.xiaoguo.domain.entity.crawler.VideoHyCrawling;
import cn.xiaoguo.mapper.VideoHyCrawlingMapper;
import cn.xiaoguo.service.crawler.VideoHyCrawlingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (VideoHyCrawling)表服务实现类
 *
 * @author makejava
 * @since 2024-07-31 17:30:23
 */
@Service("videoHyCrawlingService")
public class VideoHyCrawlingServiceImpl extends ServiceImpl<VideoHyCrawlingMapper, VideoHyCrawling> implements VideoHyCrawlingService {

    @Override
    public List<VideoHyCrawling> getAllMovie() {
        List<VideoHyCrawling> list = list();
        return list;
    }
}
