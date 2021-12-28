package cn.wolfcode.trip.service;

import cn.wolfcode.trip.domain.LoginUserInfoVo;
import cn.wolfcode.trip.domain.UserInfo;
import cn.wolfcode.trip.domain.UserInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserInfoService extends IService<UserInfo> {
    /**
     * 验证手机号
     * @param phone
     * @return
     */
    Boolean checkPhone(String phone);

    /**
     * 发送短信
     * @param phone
     */
    void sendVerifyCode(String phone);

    /**
     * 用户注册
     * @param userInfoVo
     */
    void regist(UserInfoVo userInfoVo);

    LoginUserInfoVo login(String username, String password);
}
