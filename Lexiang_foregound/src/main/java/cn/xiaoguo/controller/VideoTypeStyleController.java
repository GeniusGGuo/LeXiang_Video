package cn.xiaoguo.controller;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.service.type.VideoTypeStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName VideoTypeStyleController
 * @Description TODO
 * @Author 小果
 * @Date 2024/7/15 18:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/videoType")
public class VideoTypeStyleController {

    @Autowired
    private VideoTypeStyleService videoTypeStyleService;

    @GetMapping("/getTypeStyle/{id}")
    public ResponseResult getTypeStyle(@PathVariable int id) {
        return videoTypeStyleService.getTypeStyle(id);
    }


}
