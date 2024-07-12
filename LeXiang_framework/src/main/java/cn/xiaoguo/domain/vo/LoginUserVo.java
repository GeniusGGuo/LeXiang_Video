package cn.xiaoguo.domain.vo;

import cn.xiaoguo.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName LoginUserVo
 * @Description TODO
 * @Author 小果
 * @Date 2024/6/11 21:36
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserVo {

    private String token;
    private User userInfo;
}
