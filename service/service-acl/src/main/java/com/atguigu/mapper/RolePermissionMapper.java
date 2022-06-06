package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 包名:com.atguigu.mapper
 *
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    /**
     * 查询角色已分配的权限id集合
     * @param roleId
     * @return
     */
    List<Long> findPermissionIdListByRoleId(Long roleId);

    /**
     * 根据roleId和permissionId查询
     * @param roleId
     * @param permissionId
     * @return
     */
    RolePermission findByRoleIdAndPermissionId(@Param("roleId") Long roleId,@Param("permissionId") Long permissionId);

    /**
     * 删除角色与权限的绑定
     * @param roleId
     * @param removePermissionIdList
     */
    void removeRolePermission(@Param("roleId") Long roleId,@Param("removePermissionIdList") List<Long> removePermissionIdList);

}
