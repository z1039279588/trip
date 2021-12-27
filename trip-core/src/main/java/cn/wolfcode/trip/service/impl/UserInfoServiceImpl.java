package cn.wolfcode.trip.service.impl;

import cn.wolfcode.trip.domain.UserInfo;
import cn.wolfcode.trip.domain.UserInfoVo;
import cn.wolfcode.trip.mapper.UserInfoMapper;
import cn.wolfcode.trip.service.IUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
