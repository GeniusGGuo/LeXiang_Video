package cn.xiaoguo.service.impl.video;

import cn.xiaoguo.domain.entity.video.VideoLink;
import cn.xiaoguo.mapper.VideoLinkMapper;
import cn.xiaoguo.service.video.VideoLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * (VideoLink)表服务实现类
 *
 * @author makejava
 * @since 2024-08-06 19:21:26
 */
@Service("videoLinkService")
public class VideoLinkServiceImpl extends ServiceImpl<VideoLinkMapper, VideoLink> implements VideoLinkService {

    @Override
    public void addLinkList(String link,Integer videoId) {
        String[] linkList = link.split(" ");
        List<VideoLink> videoLinks = IntStream.range(0, linkList.length)
                .mapToObj(i -> new VideoLink(null, videoId, linkList[i], "第" + (i + 1) + "集"))
                .toList();

        saveBatch(videoLinks);
    }
}
