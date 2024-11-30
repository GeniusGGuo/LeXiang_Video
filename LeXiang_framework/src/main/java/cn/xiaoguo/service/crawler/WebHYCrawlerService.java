package cn.xiaoguo.service.crawler;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.crawler.VideoHyCrawling;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName WebHYCrawlerService
 * @Description TODO
 * @Author 小果
 * @Date 2024/7/31 21:54
 * @Version 1.0
 */
public interface WebHYCrawlerService {
    ArrayList<VideoHyCrawling> crawlHYVideoList(String url);

    ResponseResult crawlingMovieInfo(List<VideoHyCrawling> allMovie);
}
