package cn.xiaoguo.controller;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.type.VideoRegionType;
import cn.xiaoguo.domain.entity.video.VideoInfo;
import cn.xiaoguo.domain.vo.PageVo;
import cn.xiaoguo.service.type.VideoTypeRegionService;
import cn.xiaoguo.service.video.VideoInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @ClassName VideoInfoController
 * @Description TODO
 * @Author 小果
 * @Date 2024/8/12 18:10
 * @Version 1.0
 */

@RequestMapping("/videoInfo")
@RestController
public class VideoInfoController {

    @Autowired
    private VideoTypeRegionService videoTypeRegionService;
    @Autowired
    private VideoInfoService videoInfoService;

    @GetMapping("/getPidVideoInfoList/{id}")
    public ResponseResult getPidVideoInfoList(@PathVariable int id ,@RequestParam Integer pageNum,Integer pageSize,String style,String year,String region){
        List<VideoRegionType> typeRegion = videoTypeRegionService.getTypeRegion(id);
        return videoInfoService.getVideoInfoListByRegionName(typeRegion,pageNum,pageSize,style,year,region);
    }

//    @GetMapping("/getMovieByStyle")
//    public ResponseResult getMovieStyle(){
//
//    }

}
