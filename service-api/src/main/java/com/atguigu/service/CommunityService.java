package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Community;

import java.util.List;

/**
 * 包名:com.atguigu.service
 *
 */
public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();
}
