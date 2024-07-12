package cn.xiaoguo.controller;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.service.VideoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName VideoTypeController
 * @Description TODO
 * @Author 小果
 * @Date 2024/6/24 11:00
 * @Version 1.0
 */

@RestController
@RequestMapping("/videoType")
public class VideoTypeController {

    @Autowired
    private VideoTypeService videoTypeService;

    @GetMapping("/getParentType")
    public ResponseResult getParentType() {
      return   videoTypeService.getParentType();
    }

    @GetMapping("/getClassify/{id}")
    public ResponseResult getClassify(@PathVariable int id) {
        return videoTypeService.getClassify(id);
    }
}
