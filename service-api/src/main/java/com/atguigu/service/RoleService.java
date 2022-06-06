package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.atguigu.service
 *
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();

    /**
     * 根据用户id查询用户已分配和未分配的角色列表
     * @param adminId
     * @return
     */
    Map<String,List<Role>> findRolesByAdminId(Long adminId);

    /**
     * 保存用户分配权限
     * @param adminId
     * @param rolIds
     */
    void saveAdminRole(Long adminId,List<Long> rolIds);
}
