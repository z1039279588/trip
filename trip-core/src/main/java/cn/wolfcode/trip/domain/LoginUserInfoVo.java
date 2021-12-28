package cn.wolfcode.trip.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginUserInfoVo {
    private String token;   // 登入生成的token
    private UserInfo userInfo; // 用户对象
}
