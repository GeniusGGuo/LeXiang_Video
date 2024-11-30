package cn.xiaoguo.service.impl.upload;

import cn.xiaoguo.utils.FileUtils;
import io.minio.MinioClient;
import cn.xiaoguo.config.MinioConfig;
import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.user.User;
import cn.xiaoguo.enums.AppHttpCodeEnum;
import cn.xiaoguo.mapper.UserMapper;
import cn.xiaoguo.service.upload.UploadService;
import cn.xiaoguo.utils.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * @ClassName UploadServiceImpl
 * @Description TODO
 * @Author 小果
 * @Date 2024/7/18 17:37
 * @Version 1.0
 */



@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;


    @Override
    public ResponseResult updateUserAvatar(Long id, MultipartFile file) {
        try {
            //文件名
            String fileName = file.getOriginalFilename();
            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            String path = "User" + "/" + id + "/" + newFileName;
            //类型
            String contentType = file.getContentType();
            //获取当前头像的存储路径
            User user = userMapper.selectById(id);
            if (!(user.getAvatar() == null || user.getAvatar().isEmpty())) {
                String ossPath = user.getAvatar().split("/lexiang-video/")[1];
                //删除原来的头像
                minioUtils.removeFile(minioConfig.getBucketName(), ossPath);
            }
            //生气新的头像的访问链接
            String url = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + path;
            //上传新的头像
            minioUtils.uploadFile(minioConfig.getBucketName(), file, path, contentType);
            user.setAvatar(url);
            userMapper.updateById(user);
            return ResponseResult.okResult(url);
        } catch (Exception e) {
            log.error("上传失败", e);
            return ResponseResult.errorResult(AppHttpCodeEnum.UPLOAD_FAIL);
        }
    }

    @Override
    public String uploadMovieImg(String movieImg, String id,String type) {
        try (InputStream inputStream = new URL(movieImg).openStream()){
            String fileName = System.currentTimeMillis() + ".jpg";
            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            String path = "Video" + "/" + type + "/" + id + "/" + newFileName;
            //生气新的头像的访问链接
            String url = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + path;
            minioUtils.uploadFile(minioConfig.getBucketName(), path, inputStream);
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
