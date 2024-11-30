package cn.xiaoguo.controller;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.service.type.VideoTypeYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName VideoTypeYearController
 * @Description TODO
 * @Author 小果
 * @Date 2024/7/15 18:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/videoType")
public class VideoTypeYearController {

    @Autowired
    private VideoTypeYearService videoTypeYearService;

    @GetMapping("/getTypeYear/{id}")
    public ResponseResult getTypeYear(@PathVariable int id) {
        return videoTypeYearService.getTypeYear(id);
    }

}
