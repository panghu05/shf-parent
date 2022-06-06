package com.atguigu.mapper;

import com.atguigu.entity.UserFollow;
import com.atguigu.entity.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 包名:com.atguigu.mapper
 *
 */
public interface UserFollowMapper {
    /**
     * 根据用户id和房源id查询关注信息
     * @param userId
     * @param houseId
     * @return
     */
    UserFollow findByUserIdAndHouseId(@Param("userId") Long userId,@Param("houseId") Long houseId);

    /**
     * 更新房源关注信息
     * @param userFollow
     */
    void update(UserFollow userFollow);

    /**
     * 新增关注信息
     * @param userFollow
     */
    void insert(UserFollow userFollow);

    /**
     * 根据用户的id查询用户的关注列表
     * @param userId
     * @return
     */
    Page<UserFollowVo> findListPage(Long userId);
}
