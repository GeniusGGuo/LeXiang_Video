package cn.xiaoguo.controller;

import cn.xiaoguo.service.crawler.WebHYCrawlerService;
import cn.xiaoguo.service.impl.crawler.WebHYCrawlerServiceImpl;
import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.crawler.VideoHyCrawling;
import cn.xiaoguo.domain.entity.type.VideoRegionType;
import cn.xiaoguo.service.crawler.VideoHyCrawlingService;
import cn.xiaoguo.service.type.VideoTypeRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName crawlingController
 * @Description TODO
 * @Author 小果
 * @Date 2024/7/31 11:49
 * @Version 1.0
 */

@RestController
@RequestMapping("/crawler")
public class CrawlingController {

    @Autowired
    private WebHYCrawlerService webHYCrawlerService;
    @Autowired
    private VideoTypeRegionService videoTypeRegionService;
    @Autowired
    private VideoHyCrawlingService videoHyCrawlingService;

    @GetMapping("/HY")
    public ResponseResult HYCrawling(){
        ArrayList<VideoHyCrawling> resultList = new ArrayList<>();
        List<VideoRegionType> parentTypeList = videoTypeRegionService.getParentType();
        for (VideoRegionType parentType : parentTypeList) {
            Integer id = parentType.getId();
            String name = parentType.getTypeRegionName();
            for(int pageSum = 1 ; pageSum < 11 ;pageSum ++){
                String url = "https://www.huyazy.com/index.php/vod/type/id/" + id + "/page/" + pageSum + ".html?ac=detail";
                ArrayList<VideoHyCrawling> result = webHYCrawlerService.crawlHYVideoList(url);
                resultList.addAll(result);
                System.out.println(name+":::"+result.size());
            }
            }
        boolean b = videoHyCrawlingService.saveBatch(resultList);

        return ResponseResult.okResult(b);
    }

    @GetMapping("/movieInfo")
    public ResponseResult HYMovieInfo(){
       List<VideoHyCrawling>  allMovie  =videoHyCrawlingService.getAllMovie();
        return webHYCrawlerService.crawlingMovieInfo(allMovie);
    }

}
