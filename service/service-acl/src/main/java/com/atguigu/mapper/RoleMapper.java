package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Role;

import java.util.List;

/**
 * 包名:com.atguigu.mapper
 *
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 查询所有角色信息
     * @return
     */
    List<Role> findAll();
}
