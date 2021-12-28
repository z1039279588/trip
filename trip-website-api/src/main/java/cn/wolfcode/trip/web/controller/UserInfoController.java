package cn.wolfcode.trip.web.controller;

import cn.wolfcode.trip.common.JsonResult;
import cn.wolfcode.trip.domain.LoginUserInfoVo;
import cn.wolfcode.trip.domain.UserInfo;
import cn.wolfcode.trip.domain.UserInfoVo;
import cn.wolfcode.trip.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserInfoController {
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("get")
    public JsonResult get(Long id){
        UserInfo userInfo = userInfoService.getById(id);
        return JsonResult.success(userInfo);
    }
    @GetMapping("checkPhone")
    public JsonResult checkPhone(String phone){
        String pattern = "^1\\d{10}$";
        if (phone.matches(pattern)){  // 校验通过
            // exist为true是代表已经注册
            Boolean exist = userInfoService.checkPhone(phone);
            return JsonResult.success(exist);
        }else {// 手机号格式不正确
            return JsonResult.error("手机号不合法");
        }
    }
    @GetMapping("sendVerifyCode")
    public JsonResult sendVerifyCode(String phone){
        userInfoService.sendVerifyCode(phone);
        return JsonResult.success();
    }
    @PostMapping("regist")
    public JsonResult regist(@Validated UserInfoVo userInfoVo){
        // 参数校验 框架
        // 密码是否相同
        if(!StringUtils.equals(userInfoVo.getPassword(),userInfoVo.getRpassword())){
            throw new RuntimeException("密码不一致");
        }
        // 验证码校验
        String key = "user:code:" +userInfoVo.getPhone();
        String code = redisTemplate.opsForValue().get(key);
        if(!StringUtils.equals(userInfoVo.getVerifyCode(),code)){
            throw new RuntimeException("验证码不正确");
        }
        userInfoService.regist(userInfoVo);
        return JsonResult.success();
    }

    @PostMapping("login")
    public JsonResult login(String username,String password){
        LoginUserInfoVo loginUserInfoVo = userInfoService.login(username,password);
    return JsonResult.success();
    }
}
