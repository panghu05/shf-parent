package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.bo.LoginBo;
import com.atguigu.entity.bo.RegisterBo;
import com.atguigu.result.Result;
import com.atguigu.result.ResultCodeEnum;
import com.atguigu.service.UserInfoService;
import com.atguigu.util.MD5;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 包名:com.atguigu.controller
 *
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Reference
    private UserInfoService userInfoService;
    @GetMapping("/sendCode/{phone}")
    public Result sendCode(@PathVariable("phone") String phone, HttpSession session){
        //本应该调用阿里云短信(短信SDK)给phone发送短信
        //现在模拟一个短信
        String code = "1111";
        //实际开发中验证码应该是存储到Redis，并且设置时效性; 我们将其存储到session
        session.setAttribute("CODE",code);
        //验证码发送成功
        return Result.ok();
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterBo registerBo,HttpSession session){
        //1. 校验验证码是否正确
        //1.1 获取session中保存的验证码
        String sessionCode = (String) session.getAttribute("CODE");
        //1.2 校验
        if (!registerBo.getCode().equalsIgnoreCase(sessionCode)) {
            //验证码校验失败
            return Result.build(null, ResultCodeEnum.CODE_ERROR);
        }
        //2. 校验手机号是否已存在
        //2.1 调用业务层的方法根据手机号查询用户信息
        UserInfo userInfo = userInfoService.getByPhone(registerBo.getPhone());
        //2.2 判断查询到的用户信息是否为null，如果不为null表示手机号已存在则不能注册
        if (userInfo != null) {
            //手机号已存在
            return Result.build(null,ResultCodeEnum.PHONE_REGISTER_ERROR);
        }
        //3. 对密码进行加密
        String encryptPassword = MD5.encrypt(registerBo.getPassword());
        //4. 调用业务层的方法将数据保存起来
        userInfo = new UserInfo();
        //拷贝属性
        BeanUtils.copyProperties(registerBo,userInfo);
        //重新设置密码
        userInfo.setPassword(encryptPassword);
        //设置status为1
        userInfo.setStatus(1);
        userInfoService.insert(userInfo);
        return Result.ok();
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginBo loginBo,HttpSession session){
        //根据手机号查找用户，判断手机号是否正确
        UserInfo userInfo = userInfoService.getByPhone(loginBo.getPhone());
        if (userInfo == null) {
            //说明你的手机号错了!!!
            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }
        //判断账号是否被锁定了
        if (userInfo.getStatus() == 0) {
            return Result.build(null,ResultCodeEnum.ACCOUNT_LOCK_ERROR);
        }
        //判断密码是否正确
        if (!userInfo.getPassword().equals(MD5.encrypt(loginBo.getPassword()))) {
            //密码错误
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }
        //如果能走到这，说明登录成功，则将用户的信息存储到session中
        session.setAttribute("USER",userInfo);

        //将当前登录的用户信息响应给前端，让前端页面回显
        Map responseMapping = new HashMap();
        responseMapping.put("nickName",userInfo.getNickName());
        responseMapping.put("phone",userInfo.getPhone());
        return Result.ok(responseMapping);
    }

    @GetMapping("/logout")
    public Result logout(HttpSession session){
        //从session中移除当前用户信息，或者直接销毁session
        session.invalidate();
        return Result.ok();
    }
}
