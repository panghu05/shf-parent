package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseBroker;

import java.util.List;

/**
 * 包名:com.atguigu.service
 *
 */
public interface HouseBrokerService extends BaseService<HouseBroker> {
    /**
     * 根据房源id查询房源经纪人列表
     * @param houseId
     * @return
     */
    List<HouseBroker> findHouseBrokerListByHouseId(Long houseId);
}
