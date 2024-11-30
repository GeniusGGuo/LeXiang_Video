package cn.xiaoguo.service.upload;

import cn.xiaoguo.domain.entity.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadService
 * @Description TODO
 * @Author 小果
 * @Date 2024/7/18 17:36
 * @Version 1.0
 */

public interface UploadService {


    ResponseResult updateUserAvatar(Long id, MultipartFile img);

    String uploadMovieImg(String movieImg,String id,String type);
}
