package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * 包名:com.atguigu.mapper
 *
 */
public interface HouseUserMapper extends BaseMapper<HouseUser> {
    /**
     * 根据房源id查询房源房东列表
     * @param houseId
     * @return
     */
    List<HouseUser> findHouseUserListByHouseId(Long houseId);
}
