package cn.xiaoguo.listener;

import cn.xiaoguo.constant.SystemConstants;
import cn.xiaoguo.domain.entity.movieAndType.MovieAndStyle;
import cn.xiaoguo.domain.entity.type.VideoRegionType;
import cn.xiaoguo.domain.entity.type.VideoTypeStyle;
import cn.xiaoguo.domain.vo.MovieInfoVo;
import cn.xiaoguo.service.movieAndType.MovieAndRegionService;
import cn.xiaoguo.service.movieAndType.MovieAndStyleService;
import cn.xiaoguo.service.movieAndType.MovieAndYearService;
import cn.xiaoguo.service.type.VideoTypeRegionService;
import cn.xiaoguo.service.type.VideoTypeStyleService;
import cn.xiaoguo.service.type.VideoTypeYearService;
import cn.xiaoguo.service.upload.UploadService;
import cn.xiaoguo.service.video.VideoInfoService;
import cn.xiaoguo.service.video.VideoLinkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static cn.xiaoguo.constant.SystemConstants.*;


/**
 * @ClassName RabbitMQListener
 * @Description TODO
 * @Author 小果
 * @Date 2024/7/31 10:36
 * @Version 1.0
 */

@Component
@Slf4j
public class RabbitMQListener {

    @Autowired
    private VideoInfoService videoInfoService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private MovieAndStyleService movieAndStyleService;
    @Autowired
    private MovieAndYearService movieAndYearService;
    @Autowired
    private MovieAndRegionService movieAndRegionService;
    @Autowired
    private VideoTypeStyleService videoTypeStyleService;
    @Autowired
    private VideoTypeRegionService videoTypeRegionService;
    @Autowired
    private VideoTypeYearService videoTypeYearService;
    @Autowired
    private VideoLinkService videoLinkService;

    HashMap<Integer,String> regionMap = new HashMap<>();
    HashMap<Integer,Integer> regionPidMap = new HashMap<>();
    HashMap<String,Integer> styleMap = new HashMap<>();
    HashMap<String,Integer> yearMap = new HashMap<>();


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = QUEUE_NAME_HY,durable = "true"),
            exchange = @Exchange(value = EXCHANGE_DIRECT_HY),
            key = {ROUTING_KEY_HY})
    )
    public void processHYVideoCrawling(String dataString , Message message, Channel channel) throws IOException {
        //获取当前消息的deliverTag
          long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MovieInfoVo movieInfoVo = objectMapper.readValue(dataString, MovieInfoVo.class);
            //设置影片的region分类字段
            if(regionMap.isEmpty()){
              List<VideoRegionType> regionTypeList =videoTypeRegionService.list();
                for (VideoRegionType videoRegionType : regionTypeList) {
                    regionMap.put(videoRegionType.getId(),videoRegionType.getTypeRegionName());
                    regionPidMap.put(videoRegionType.getId(),videoRegionType.getTypePid());
                }
            }
            movieInfoVo.setMovieRegion(regionMap.get(movieInfoVo.getPid()));
            //保存影片图像
            String url = uploadService.uploadMovieImg(movieInfoVo.getMovieImg(), movieInfoVo.getHyId(), regionMap.get(movieInfoVo.getPid()));
            movieInfoVo.setMovieImg(url);
            Integer videoId = videoInfoService.addMovies(movieInfoVo);
            if (styleMap.isEmpty()){
                List<VideoTypeStyle> videoTypeStyleList = videoTypeStyleService.list();
                for (VideoTypeStyle typeStyle : videoTypeStyleList) {
                    styleMap.put(typeStyle.getTypeStyleName(),typeStyle.getId());
                }
            }
            if (hasComma(movieInfoVo.getMovieStyle())){
                String[] styles = movieInfoVo.getMovieStyle().split(",");
                for (int i=0 ; i<styles.length-1;i++){
                    if(!styleMap.containsKey(styles[i])){
                        //如果不存在 这新增并返回它的id
                        Integer styleId = videoTypeStyleService.addStyle(styles[i],regionPidMap.get(movieInfoVo.getPid()));
                        styleMap.put(styles[i],styleId);
                    }
                        movieAndStyleService.addMovieStyleOne(videoId, styleMap.get(styles[i]));
                }
            }
            if(!isBlank(movieInfoVo.getMovieYear())){
                if (!yearMap.containsKey(movieInfoVo.getMovieYear())){
                    Integer yearId = videoTypeYearService.addYear(regionPidMap.get(movieInfoVo.getPid()), movieInfoVo.getMovieYear());
                    yearMap.put(movieInfoVo.getMovieYear(),yearId);
                }
                movieAndYearService.addMovieYearOne(videoId,yearMap.get(movieInfoVo.getMovieYear()));
            }
            movieAndRegionService.addMovieRegion(videoId,movieInfoVo.getPid());
            videoLinkService.addLinkList(movieInfoVo.getLink(),videoId);
            //业务操作成功：返回ACK消息
            channel.basicAck(deliveryTag,false);
        } catch (IOException e) {
            //获取当前消息是否为重复投递
            // redelivered 为true表示消息已经重复投递 为false表示消息第一次投递
            Boolean redelivered = message.getMessageProperties().getRedelivered();
            //业务操作失败：返回NACK消息
            //requeue参数 ：控制消息是否重新放回队列
            // 取值为true重新放回队列broker会重新投递这个消息
            // 取值为false，broker会把这个消息丢弃
            if(redelivered){
                //如果消息已经是重复投递的
                channel.basicNack(deliveryTag,false,false);
            }else {
                //如果消息不是重复投递的
                channel.basicNack(deliveryTag,false,true);
            }
        }


    }

    public static boolean hasComma(String s) {
        return s.contains(",");
    }

    public boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

}
