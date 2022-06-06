package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.UserFollow;
import com.atguigu.entity.UserInfo;
import com.atguigu.entity.vo.UserFollowVo;
import com.atguigu.result.Result;
import com.atguigu.service.UserFollowService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 包名:com.atguigu.controller
 *
 */
@RestController
@RequestMapping("/userFollow")
public class UserFollowController {
    @Reference
    private UserFollowService userFollowService;

    @GetMapping("/auth/follow/{houseId}")
    public Result addFollow(@PathVariable("houseId") Long houseId, HttpSession session){
        //1. 判断用户之前是否已经添加过这条数据的关注:根据用户id和房源id查询出UserFollow
        //1.1 获取当前登录的用户
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        UserFollow userFollow = userFollowService.findByUserIdAndHouseId(userInfo.getId(),houseId);
        //2.如果用户之前已经添加过关注，那么我们只需要更新这条数据的is_deleted为0

        if (userFollow != null) {
            //说明用户之前关注过
            userFollow.setIsDeleted(0);
            //更新关注
            userFollowService.update(userFollow);
        }else {
            //3. 如果用户之前没有添加过关注，我们需要新增一条数据
            userFollow = new UserFollow();
            userFollow.setUserId(userInfo.getId());
            userFollow.setHouseId(houseId);
            userFollowService.insert(userFollow);
        }

        return Result.ok();
    }

    @GetMapping("/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable("pageNum") Integer pageNum,
                               @PathVariable("pageSize") Integer pageSize,
                               HttpSession session){
        //获取当前用户
        UserInfo userInfo = (UserInfo) session.getAttribute("USER");
        //调用业务层的方法查询用户关注列表
        PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum, pageSize, userInfo.getId());

        return Result.ok(pageInfo);
    }

    @GetMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id){
        //创建UserFollow对象
        UserFollow userFollow = new UserFollow();
        userFollow.setId(id);
        userFollow.setIsDeleted(1);
        //调用业务层的方法修改
        userFollowService.update(userFollow);

        return Result.ok();
    }
}
