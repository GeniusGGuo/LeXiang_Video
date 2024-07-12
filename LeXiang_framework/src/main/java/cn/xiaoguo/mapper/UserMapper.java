package cn.xiaoguo.mapper;

import cn.xiaoguo.domain.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2024-06-07 17:20:25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
