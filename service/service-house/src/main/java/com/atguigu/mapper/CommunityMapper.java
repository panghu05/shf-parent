package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * 包名:com.atguigu.mapper
 *
 */
public interface CommunityMapper extends BaseMapper<Community> {
    List<Community> findAll();
}
