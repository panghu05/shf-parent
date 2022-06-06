package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseUser;

import java.util.List;

/**
 * 包名:com.atguigu.service
 *
 */
public interface HouseUserService extends BaseService<HouseUser> {
    /**
     * 根据房源id查询房源房东列表
     * @param houseId
     * @return
     */
    List<HouseUser> findHouseUserListByHouseId(Long houseId);
}
