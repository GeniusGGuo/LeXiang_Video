package cn.xiaoguo.service.impl.crawler;

import cn.xiaoguo.constant.SystemConstants;
import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.crawler.VideoHyCrawling;
import cn.xiaoguo.domain.vo.MovieInfoVo;
import cn.xiaoguo.service.crawler.WebHYCrawlerService;
import cn.xiaoguo.service.type.VideoTypeRegionService;
import cn.xiaoguo.utils.FastJsonRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName WebCrawlerService
 * @Description TODO
 * @Author 小果
 * @Date 2024/7/31 13:29
 * @Version 1.0
 */
@Service
@Slf4j
public class WebHYCrawlerServiceImpl  implements WebHYCrawlerService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private VideoTypeRegionService videoTypeRegionService;
    HashMap<String, Integer> map = new HashMap<>();

    @Override
    public ArrayList<VideoHyCrawling> crawlHYVideoList(String url){
        ArrayList<VideoHyCrawling> hyCrawlingArrayList = new ArrayList<>();
        //编译正则表达式
        Pattern pattern = Pattern.compile("(\\d+)");
        try {
            Document doc = Jsoup.connect(url).get();
            Elements contents = doc.select("li.liboder");
            for (Element content : contents) {
                if (hyCrawlingArrayList.size()<=500) {
                    String link = content.select("a[href]").attr("href");
                    //创建matcher对象
                    Matcher matcher = pattern.matcher(link);
                    if (matcher.find()) {
                        VideoHyCrawling hyCrawling = new VideoHyCrawling();
                        String type = content.select("span.xing_vb5").text();
                        hyCrawling.setId(Integer.parseInt(matcher.group()));
                        hyCrawling.setVideoName(content.select("span.xing_vb4").text().split(" ")[0]);

                        if(!map.containsKey(type)) {
                            Integer typeId = videoTypeRegionService.getTypeId(type);
                            map.put(type,typeId);
                        }
                        hyCrawling.setVideoType(type);
                        hyCrawling.setVideoRegionId(map.get(type));
                        hyCrawlingArrayList.add(hyCrawling);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return hyCrawlingArrayList;
    }

    @Override
    public ResponseResult crawlingMovieInfo(List<VideoHyCrawling> allMovie) {
        //创建一个固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(30);
        for (VideoHyCrawling movie : allMovie) {
            String movieUrl =generateMovieUrl(movie.getId());
            WebCrawlerTask task = new WebCrawlerTask(movieUrl,movie.getId().toString(),movie.getVideoRegionId(),rabbitTemplate);
            executor.submit(task);
        }
        executor.shutdown();
        try {
            // 等待所有任务完成，或者等待最多60秒
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                // 超时后，可以选择取消所有未完成的任务
                executor.shutdownNow();
                // 可能需要处理未完成的任务或记录日志等操作
            }
        } catch (InterruptedException e) {
            // 如果当前线程在等待过程中被中断，则执行shutdownNow来取消所有未完成的任务
            executor.shutdownNow();
            // 保留中断状态并重新抛出InterruptedException，以便上层代码能够处理中断
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        return ResponseResult.okResult();
    }
    private String generateMovieUrl(Integer id) {
        return String.format(SystemConstants.HY_MOVIE_URL,id);
    }

    static class WebCrawlerTask implements Runnable{
        private final String url;
        private final String id;
        private final Integer pId;
        private RabbitTemplate rabbitTemplate;

        public WebCrawlerTask(String url,String id,Integer pId,RabbitTemplate rabbitTemplate){
            this.url=url;
            this.id=id;
            this.pId=pId;
            this.rabbitTemplate=rabbitTemplate;
        }
        @Override
        public void run() {
            try {
                Document document = Jsoup.connect(url).get();
                Elements contents = document.select("div.vodInfo");
                Elements title = contents.select("div.vodh");
                Elements infoData = contents.select("div.vodinfobox");
                //影片名字
                String movieName = title.select("h2").text();
                //集数
                String movieEpisodes = (title.select("span").text());
                //影片别名
                String movieAlias = infoData.select("span").first().text();
                //导演名字
                String directorName = infoData.select("span").get(1).text();
                //演员名字
                String performerName = infoData.select("span").get(2).text();
                //影片类型
                String movieStyle = infoData.select("span").get(3).text();
                //上映年份
                String movieYear = infoData.select("span").get(6).text();
                //影片概要
                String movieSynopsis = document.select("div.vodplayinfo").first().text();
                //影片连接
                Elements lis = document.getElementById("play_1").select("li");
                StringBuilder link = new StringBuilder();
                for (Element li : lis) {
                    String href = li.select("a[href]").attr("href");
                    link.append(href).append(" ");
                }
                //影片图像
                String img = document.select("img.lazy").attr("src");
                MovieInfoVo movieInfoVo = new MovieInfoVo();
                movieInfoVo.setHyId(id);
                movieInfoVo.setMovieName(movieName);
                movieInfoVo.setMovieAlias(movieAlias);
                movieInfoVo.setMovieEpisodes(movieEpisodes);
                movieInfoVo.setDirectorName(directorName);
                movieInfoVo.setPerformerName(performerName);
                movieInfoVo.setMovieStyle(movieStyle);
                movieInfoVo.setMovieYear(movieYear);
                movieInfoVo.setMovieSynopsis(movieSynopsis);
                movieInfoVo.setLink(link.toString());
                movieInfoVo.setMovieImg(img);
                movieInfoVo.setPid(pId);
                // 序列化MovieInfoVo对象
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(movieInfoVo);
                System.out.println(json); // 输出序列化后的JSON字符串
                rabbitTemplate.convertAndSend(SystemConstants.EXCHANGE_DIRECT_HY,SystemConstants.ROUTING_KEY_HY,json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
