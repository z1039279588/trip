package cn.wolfcode.trip.domain;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class UserInfoVo {
    @NotEmpty(message = "手机号码不能为空")
    @Pattern(regexp = "^1[345689]\\d{9}$",message = "手机号码格式不正确")
    private String phone; // 手机号码
    @NotEmpty(message = "用户昵称不能为空")
    @Pattern(regexp = "\\w{4,}")
    private String nickname; // 用户昵称
    @NotEmpty(message = "用户密码不能为空")
    private String password; // 用户密码
    @NotEmpty(message = "确认密码不能为空")
    private String rpassword; // 验证密码
    @Pattern(regexp = "^\\d{4}$",message = "验证码不正确")
    private String verifyCode; // 用户验证码


}
