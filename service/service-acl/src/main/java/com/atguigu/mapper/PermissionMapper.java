package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Permission;

import java.util.List;

/**
 * 包名:com.atguigu.mapper
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 查询所有权限
     * @return
     */
    List<Permission> findAll();

    /**
     * 根据用户id查询用户的权限列表
     * @param adminId
     * @return
     */
    List<Permission> findPermissionListByAdminId(Long adminId);

    /**
     * 根据父节点id查询子节点数量
     * @param id
     * @return
     */
    Long findCountByParentId(Long id);
    //查询所有权限的code
    List<String> findAllCodePermission();

    List<String> findCodePermissionListByAdminId(Long adminId);
}
