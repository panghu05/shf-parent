package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Admin;

import java.util.List;

/**
 * 包名:com.atguigu.mapper
 *
 */
public interface AdminMapper extends BaseMapper<Admin> {
    List<Admin> findAll();

    Admin getByUsername(String username);
}
