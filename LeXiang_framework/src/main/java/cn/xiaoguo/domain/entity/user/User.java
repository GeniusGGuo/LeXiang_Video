package cn.xiaoguo.domain.entity.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2024-06-07 17:20:27
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User  {
//id@TableId
    private String id;

//账号
    private String userName;
//昵称
    private String nickName;
//密码
    private String password;
//用户类型（0为普通用户/1为管理员用户）
    private String userType;
//账号状态（0正常1停用）
    private String status;
//邮箱
    private String email;
//性别（0为男1为女）
    private String sex;
//头像
    private String avatar;
//删除标识（0为正常1为删除）
    private String delFlag;



}
