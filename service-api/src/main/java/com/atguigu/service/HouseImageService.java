package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.HouseImage;

import java.util.List;

/**
 * 包名:com.atguigu.service
 *
 */
public interface HouseImageService extends BaseService<HouseImage> {
    /**
     * 根据房源的id和type查询房源或者房产列表
     * @param houseId
     * @param type
     * @return
     */
    List<HouseImage> findHouseImageList(Long houseId,Integer type);
}
