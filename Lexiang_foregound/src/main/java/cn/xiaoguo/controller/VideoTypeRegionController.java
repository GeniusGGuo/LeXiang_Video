package cn.xiaoguo.controller;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.type.VideoRegionType;
import cn.xiaoguo.service.type.VideoTypeRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName VideoTypeController
 * @Description 视频类型控制层
 * @Author 小果
 * @Date 2024/6/24 11:00
 * @Version 1.0
 */

@RestController
@RequestMapping("/videoType")
public class VideoTypeRegionController {

    @Autowired
    private VideoTypeRegionService videoTypeRegionService;

    @GetMapping("/getParentType")
    public ResponseResult getParentType() {
        List<VideoRegionType> parentTypeList = videoTypeRegionService.getParentType();
        return ResponseResult.okResult(parentTypeList);
    }

    @GetMapping("/getTypeRegion/{id}")
    public ResponseResult getTypeRegion(@PathVariable int id) {
        List<VideoRegionType> typeRegion = videoTypeRegionService.getTypeRegion(id);
        return ResponseResult.okResult(typeRegion);
    }

    @PostMapping("/addClassifyList")
    public ResponseResult addClassifyList(@RequestBody List<VideoRegionType> videoRegionTypeList) {
        return videoTypeRegionService.addClassifyList(videoRegionTypeList);
    }
}
