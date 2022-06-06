package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.AdminRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 包名:com.atguigu.mapper
 *
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {
    /**
     * 根据用户的id查询当前用户已分配的角色的id集合
     * @param adminId
     * @return
     */
    List<Long> findRoleIdListByAdminId(Long adminId);

    /**
     * 移除用户需要移除的角色
     * @param adminId
     * @param removeRoleIds
     */
    void removeAdminRole(@Param("adminId")Long adminId,@Param("removeRoleIds") List<Long> removeRoleIds);

    /**
     * 根据adminId和roleId查询用户分配的角色
     * @param adminId
     * @param roleId
     * @return
     */
    AdminRole findByAdminIdAndRoleId(@Param("adminId")Long adminId,@Param("roleId")Long roleId);
}
