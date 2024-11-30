package cn.xiaoguo.controller;

import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadController
 * @Description TODO
 * @Author 小果
 * @Date 2024/7/18 17:35
 * @Version 1.0
 */

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/updateUserAvatar/{id}")
    public ResponseResult updateUserAvatar(@PathVariable("id") Long id, @RequestParam("avatar") MultipartFile img){
        return uploadService.updateUserAvatar(id,img);
    }

}
