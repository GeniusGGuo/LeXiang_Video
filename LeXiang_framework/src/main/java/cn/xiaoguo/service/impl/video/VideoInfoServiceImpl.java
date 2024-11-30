package cn.xiaoguo.service.impl.video;

import cn.xiaoguo.constant.SystemConstants;
import cn.xiaoguo.domain.entity.ResponseResult;
import cn.xiaoguo.domain.entity.movieAndType.MovieAndRegion;
import cn.xiaoguo.domain.entity.movieAndType.MovieAndStyle;
import cn.xiaoguo.domain.entity.movieAndType.MovieAndYear;
import cn.xiaoguo.domain.entity.type.VideoRegionType;
import cn.xiaoguo.domain.entity.type.VideoTypeStyle;
import cn.xiaoguo.domain.entity.type.VideoTypeYear;
import cn.xiaoguo.domain.entity.video.VideoInfo;
import cn.xiaoguo.domain.vo.MovieInfoVo;
import cn.xiaoguo.domain.vo.PageVo;
import cn.xiaoguo.enums.AppHttpCodeEnum;
import cn.xiaoguo.mapper.VideoInfoMapper;
import cn.xiaoguo.service.movieAndType.MovieAndRegionService;
import cn.xiaoguo.service.movieAndType.MovieAndStyleService;
import cn.xiaoguo.service.movieAndType.MovieAndYearService;
import cn.xiaoguo.service.type.VideoTypeRegionService;
import cn.xiaoguo.service.type.VideoTypeStyleService;
import cn.xiaoguo.service.type.VideoTypeYearService;
import cn.xiaoguo.service.video.VideoInfoService;
import com.baomidou.mybatisplus.core.conditions.AbstractLambdaWrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * (VideoInfo)表服务实现类
 *
 * @author makejava
 * @since 2024-07-31 21:58:39
 */
@Service("videoInfoService")
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoMapper, VideoInfo> implements VideoInfoService {

    @Autowired
    private VideoTypeStyleService videoTypeStyleService;
    @Autowired
    private MovieAndStyleService movieAndStyleService;
    @Autowired
    private VideoTypeYearService videoTypeYearService;
    @Autowired
    private MovieAndYearService movieAndYearService;
    @Autowired
    private VideoTypeRegionService videoTypeRegionService;
    @Autowired
    private MovieAndRegionService movieAndRegionService;

    @Override
    public Integer addMovies(MovieInfoVo movieInfoVo) {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setHyId(Integer.parseInt(movieInfoVo.getHyId()));
        videoInfo.setMovieName(movieInfoVo.getMovieName());
        videoInfo.setMovieAlias(movieInfoVo.getMovieAlias());
        videoInfo.setMovieImg(movieInfoVo.getMovieImg());
        videoInfo.setDirectorName(movieInfoVo.getDirectorName());
        videoInfo.setPerformerName(movieInfoVo.getPerformerName());
        videoInfo.setMovieEpisodes(movieInfoVo.getMovieEpisodes());
        videoInfo.setMovieStyle(movieInfoVo.getMovieStyle());
        videoInfo.setMovieRegion(movieInfoVo.getMovieRegion());
        videoInfo.setMovieYear(movieInfoVo.getMovieYear());
        videoInfo.setMovieSynopsis(movieInfoVo.getMovieSynopsis());
        save(videoInfo);
        LambdaQueryWrapper<VideoInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoInfo::getHyId,Integer.parseInt(movieInfoVo.getHyId()));
        VideoInfo info = getOne(wrapper);
        return info.getId();

    }

    @Override
    public ResponseResult getVideoInfoListByRegionName(List<VideoRegionType> typeRegion, Integer pageNum, Integer pageSize,String style,String year,String region) {
        // 初始化查询条件包装器
        LambdaQueryWrapper<VideoInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideoInfo::getMovieStatus, SystemConstants.VIDEO_TYPE_STATUS);
        int pId = 0;
        for (VideoRegionType type : typeRegion) {
            pId=type.getTypePid();
        }
        // 处理地区查询
        if (StringUtils.hasText(region)||StringUtils.hasText(style)||StringUtils.hasText(year)) {

            Integer styleId = getForeignKeyIdByCondition("style",pId,style, videoTypeStyleService);
            Integer yearId = getForeignKeyIdByCondition("year",pId,year, videoTypeYearService);
            Integer regionId = getForeignKeyIdByCondition("region",pId,region, videoTypeRegionService);
            // 处理风格、年份、地区的关联查询
            List<Integer> videoIdsByStyle = getVideoIdsByCondition(styleId,movieAndStyleService, "style", MovieAndStyle::getVideoId);
            List<Integer> videoIdsByYear = getVideoIdsByCondition(yearId,movieAndYearService, "year", MovieAndYear::getVideoId);
            List<Integer> videoIdsByRegion = getVideoIdsByCondition(regionId,movieAndRegionService, "region", MovieAndRegion::getVideoId);

            // 使用HashSet来求交集，因为HashSet的contains方法时间复杂度较低
            Set<Integer> intersection = new HashSet<>();
            // 对各个分类的idList进行交集运算
            addIdListToIntersection(intersection, videoIdsByStyle);
            boolean yearBoolean = addIdListToIntersection(intersection, videoIdsByYear);
            if (!yearBoolean){
                return ResponseResult.errorResult(AppHttpCodeEnum.VIDEO_BY_TYPE_NULL);
            }
            boolean regionBoolean = addIdListToIntersection(intersection, videoIdsByRegion);
            if (!regionBoolean){
                return ResponseResult.errorResult(AppHttpCodeEnum.VIDEO_BY_TYPE_NULL);
            }
            wrapper.in(VideoInfo::getId, intersection);
        }else {
            List<String> regionNames = typeRegion.stream()
                    .map(VideoRegionType::getTypeRegionName)
                    .collect(Collectors.toList());
            wrapper.in(VideoInfo::getMovieRegion, regionNames);
        }
        // 分页查询
        Page<VideoInfo> page = new Page<>(pageNum, pageSize);
        IPage<VideoInfo> resultPage = page(page, wrapper);
        // 封装返回结果
        return ResponseResult.okResult(new PageVo(resultPage.getRecords(), resultPage.getTotal()));

    }

    // 通用方法，根据条件获取视频ID列表
    private <T> List<Integer> getVideoIdsByCondition(Integer id,IService<T> service,
                                                     String type, Function<T, Integer> videoIdExtractor) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_"+type+"_id",id);
        List<T> entities = service.list(queryWrapper);
        return entities.stream().map(videoIdExtractor).collect(Collectors.toList());
    }

    // 通用方法，根据条件获取外键ID
    private <T> Integer getForeignKeyIdByCondition(String type,Integer pid,String condition, IService<T> service) {
        if (!StringUtils.hasText(condition)) {
            return null;
        }
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        // 假设外键字段名为typeXXXName，例如typeStyleName, typeYearName, typeRegionName
        // 这里需要根据实际情况调整，或者使用反射等机制动态指定字段名
        queryWrapper.eq("type_pid",pid);
        queryWrapper.eq("type_" + type + "_name", condition);
        T entity = service.getOne(queryWrapper);
        return entity != null ? (Integer) getFieldValueByName(entity, "id") : null;
    }


    // 通用方法，向查询条件中添加视频ID列表
    private boolean addIdListToIntersection(Set<Integer> intersection , List<Integer> videoIds) {
        // 判断videoIds1是否为空，并添加到交集中
        if (videoIds != null && !videoIds.isEmpty()) {
            if (intersection.isEmpty()) {
                intersection.addAll(videoIds);
            } else {
                intersection.retainAll(videoIds);
                if (intersection.isEmpty()){
                    return false;
                }
            }
        }
        return true;

    }
    // 辅助方法：通过反射获取实体类指定属性名的Lambda函数
    private <T> SFunction<T, ?> getEntityLambdaFunctionByName(String fieldName) {
        return entity -> {
            try {
                Field field = ReflectionUtils.findField(entity.getClass(), fieldName);
                ReflectionUtils.makeAccessible(field);
                return ReflectionUtils.getField(field, entity);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        };
    }

    // 辅助方法：通过反射获取实体类指定字段名的值
    private <T, R> R getFieldValueByName(T obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (R) field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
//        if (!StringUtils.hasText(style)&&!StringUtils.hasText(year)&&!StringUtils.hasText(region)) {
//            List<String> regionNameList = typeRegion.stream()
//                    .map(VideoRegionType::getTypeRegionName)
//                    .toList();
//            LambdaQueryWrapper<VideoInfo> wrapper = new LambdaQueryWrapper<>();
//            wrapper.eq(VideoInfo::getMovieStatus, SystemConstants.VIDEO_TYPE_STATUS);
//            wrapper.in(VideoInfo::getMovieRegion, regionNameList);
//            //分页查询
//            Page<VideoInfo> page = new Page<>(pageNum, pageSize);
//            page(page, wrapper);
//            return ResponseResult.okResult(new PageVo(page.getRecords(), page.getTotal()));
//        }
//        List<MovieAndStyle> movieAndStyleList = null; // 在if外部声明
//        List<MovieAndYear> movieAndYearList = null; // 在if外部声明
//        List<MovieAndRegion> movieAndRegionList = null; // 在if外部声明
//        if (StringUtils.hasText(style)){
//            LambdaQueryWrapper<VideoTypeStyle> styleWrapper = new LambdaQueryWrapper<>();
//            styleWrapper.eq(VideoTypeStyle::getTypeStyleName,style);
//            VideoTypeStyle videoTypeStyle = videoTypeStyleService.getOne(styleWrapper);
//            LambdaQueryWrapper<MovieAndStyle> movieAndStyleLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            movieAndStyleLambdaQueryWrapper.eq(MovieAndStyle::getVideoStyleId,videoTypeStyle.getId());
//            movieAndStyleList = movieAndStyleService.list(movieAndStyleLambdaQueryWrapper);
//        }
//        if (StringUtils.hasText(year)) {
//            LambdaQueryWrapper<VideoTypeYear> yearWrapper = new LambdaQueryWrapper<>();
//            yearWrapper.eq(VideoTypeYear::getTypeYearName,year);
//            VideoTypeYear videoTypeYear = videoTypeYearService.getOne(yearWrapper);
//            LambdaQueryWrapper<MovieAndYear> movieAndYearLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            movieAndYearLambdaQueryWrapper.eq(MovieAndYear::getVideoYearId,videoTypeYear.getId());
//            movieAndYearList = movieAndYearService.list(movieAndYearLambdaQueryWrapper);
//        }
//        if(StringUtils.hasText(region)){
//            LambdaQueryWrapper<VideoRegionType> regionWrapper = new LambdaQueryWrapper<>();
//            regionWrapper.eq(VideoRegionType::getTypeRegionName,region);
//            VideoRegionType videoRegionType = videoTypeRegionService.getOne(regionWrapper);
//            LambdaQueryWrapper<MovieAndRegion> movieAndRegionLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            movieAndRegionLambdaQueryWrapper.eq(MovieAndRegion::getVideoRegionId,videoRegionType.getTypeId());
//            movieAndRegionList = movieAndRegionService.list(movieAndRegionLambdaQueryWrapper);
//        }
//        List<Integer> videoIdList1 = null;
//        List<Integer> videoIdList2 = null;
//        List<Integer> videoIdList3 = null;
//        if (movieAndRegionList != null) {
//            videoIdList1 = movieAndRegionList.stream().map(MovieAndRegion::getVideoId).toList();
//        }
//        if (movieAndStyleList!=null){
//            videoIdList2 = movieAndStyleList.stream().map(MovieAndStyle::getVideoId).toList();
//        }
//        if (movieAndYearList!=null){
//            videoIdList3 = movieAndYearList.stream().map(MovieAndYear::getVideoId).toList();
//        }
//
//        return null;
//    }

