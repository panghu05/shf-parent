package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.atguigu.service
 *
 */
public interface PermissionService extends BaseService<Permission> {
    /**
     * 查询角色以分配和未分配的权限
     * @param roleId
     * @return
     */
    List<Map<String,Object>> findPermissionByRoleId(Long roleId);

    /**
     * 保存给角色分配的权限
     * @param roleId
     * @param permissionIds
     */
    void saveRolePermission(Long roleId, List<Long> permissionIds);

    /**
     * 根据用户的id查询出用户的动态菜单
     * @param adminId
     * @return
     */
    List<Permission> findMenuPermissionByAdminId(Long adminId);

    /**
     * 查询所有菜单
     * @return
     */
    List<Permission> findAllMenu();

    List<String> findCodePermissionListByAdminId(Long adminId);
}
