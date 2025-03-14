package cn.xiaoguo.enums;

/**
 * @ClassName AppHttpCodeEnum
 * @Description  http状态码枚举类
 * @Author 小果
 * @Date 2024/6/7 17:39
 * @Version 1.0
 */
public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
    PHONENUMBER_EXIST(502,"手机号已存在"), EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),

    COMMENT_NOT_NULL(506,"内容不能为空"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    FILE_TYPE_ERROR(507,"文件上传类型错误"),
    USERNAME_NOT_NULL(508,"用户名不能为空"),
    EMAIL_NOT_NULL(509,"邮箱不能为空"),
    PASSWORD_NOT_NULL(510,"密码不能为空"),
    NICKNAME_NOT_NULL(511,"昵称不能为空"),
    NICKNAME_EXIST(512,"昵称已存在"),
    MENU_PARENT_ID(514,"修改菜单'写博文'失败，上级菜单不能选择自己"),
    DELETE_MENU_ID(515,"存在子菜单不允许删除"),

    ;
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}