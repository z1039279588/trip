package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.common.LogicException;
import cn.wolfcode.trip.domain.LoginUserInfoVo;
import cn.wolfcode.trip.domain.UserInfo;
import cn.wolfcode.trip.domain.UserInfoVo;
import cn.wolfcode.trip.mapper.UserInfoMapper;
import cn.wolfcode.trip.service.IUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper,UserInfo> implements IUserInfoService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Boolean checkPhone(String phone) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        UserInfo userInfo = super.getOne(queryWrapper);
        return userInfo != null;
    }

    @Override
    public void sendVerifyCode(String phone) {
        // 生成验证码
        String code = RandomStringUtils.random(4, "0123456789");
        // 发送短信
        System.err.println(code);
        String key = "user:code:" + phone;
        redisTemplate.opsForValue().set(key,code,5, TimeUnit.MINUTES);
    }

    @Override
    public void regist(UserInfoVo userInfoVo) {
        // 创建一个用户对象
        UserInfo userInfo = new UserInfo();
        // 拷贝
        BeanUtils.copyProperties(userInfoVo,userInfo);
        // 保存
        super.save(userInfo);
    }

    @Override
    public LoginUserInfoVo login(String username, String password) {
        // 根据用户名,密码查询数据库
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", username);
        queryWrapper.eq("password", password);
        UserInfo userInfo = super.getOne(queryWrapper);
        if(userInfo == null){
            throw new LogicException(401,"账户或密码错误");
        }
        // 判断用户状态
        if(UserInfo.STATE_DISABLE == userInfo.getState()){
            throw new LogicException(401,"当前账号被禁用");
        }
        // 创建token,保存数据到redis中
        String token = UUID.randomUUID().toString().replaceAll("-","");
        // 封装vo对象保存
        LoginUserInfoVo loginUserInfoVo = new LoginUserInfoVo();
        loginUserInfoVo.setToken(token);
        loginUserInfoVo.setUserInfo(userInfo);
        return loginUserInfoVo;
    }
}
