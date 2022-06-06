package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Permission;
import com.atguigu.entity.RolePermission;
import com.atguigu.helper.PermissionHelper;
import com.atguigu.mapper.PermissionMapper;
import com.atguigu.mapper.RolePermissionMapper;
import com.atguigu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 包名:com.atguigu.service.impl
 *
 */
@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public BaseMapper<Permission> getEntityMapper() {
        return permissionMapper;
    }

    @Override
    public List<Map<String, Object>> findPermissionByRoleId(Long roleId) {
        //1. 查询出所有权限列表
        List<Permission> allPermissionList = permissionMapper.findAll();
        //2. 查询出当前角色已分配的权限id集合
        List<Long> assignPermissionIdList = rolePermissionMapper.findPermissionIdListByRoleId(roleId);
        //3. 创建一个集合用来封装返回数据
        List<Map<String,Object>> resultList = new ArrayList<>();
        //4. 遍历出每一个权限
        for (Permission permission : allPermissionList) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            map.put("open",true);
            //判断当前权限是否已分配
            if (assignPermissionIdList.contains(permission.getId())) {
                map.put("checked",true);
            }else {
                map.put("checked",false);
            }
            //将map添加到resultList
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public void saveRolePermission(Long roleId, List<Long> permissionIds) {
        //1. 查询角色已分配的权限id集合
        List<Long> assignPermissionIdList = rolePermissionMapper.findPermissionIdListByRoleId(roleId);
        //2. 找出要删除的权限id的集合
        List<Long> removePermissionIdList = assignPermissionIdList.stream()
                .filter(id -> !permissionIds.contains(id))
                .collect(Collectors.toList());
        //3. 删除角色与权限的绑定
        if (removePermissionIdList != null && removePermissionIdList.size() > 0) {
            rolePermissionMapper.removeRolePermission(roleId,removePermissionIdList);
        }

        //遍历出传过来的每一个permissionId
        for (Long permissionId : permissionIds) {
            //判断是否已分配过
            RolePermission rolePermission = rolePermissionMapper.findByRoleIdAndPermissionId(roleId, permissionId);
            if (rolePermission == null) {
                //之前从未绑定过,则新增
                rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermissionMapper.insert(rolePermission);
            }else {
                if (rolePermission.getIsDeleted() == 1) {
                    //之前绑定过，但是已取消，则将isDeleted修改成0
                    rolePermission.setIsDeleted(0);
                    rolePermissionMapper.update(rolePermission);
                }
            }
        }
    }

    @Override
    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        //判断当前是否是超级管理员
        List<Permission> permissionList = null;
        if (adminId == 1) {
            //超级管理员,查询所有权限
            permissionList = permissionMapper.findAll();
        }else {
            //不是超级管理员,根据adminId查询权限列表
            permissionList = permissionMapper.findPermissionListByAdminId(adminId);
        }
        //现在的permissionList中的每一个permission有啥: acl_permission表中的各个字段的数据
        //将权限列表构建成父子级结构:父子级菜单中需要啥呢?
        //创建一个新集合存储构建后的数据
        List<Permission> treeNodes = PermissionHelper.build(permissionList);
        return treeNodes;
    }

    @Override
    public List<Permission> findAllMenu() {
        //1. 查询所有权限
        List<Permission> permissionList = permissionMapper.findAll();
        //2. 构建成父子级菜单
        return PermissionHelper.build(permissionList);
    }

    @Override
    public List<String> findCodePermissionListByAdminId(Long adminId) {
        List<String> codePermissionList = null;
        //判断是否是超级管理员
        if (adminId == 1) {
            //超级管理员:查询出所有的code
            codePermissionList = permissionMapper.findAllCodePermission();
        }else {
            //普通用户:根据adminId查询code集合
            codePermissionList = permissionMapper.findCodePermissionListByAdminId(adminId);
        }
        return codePermissionList;
    }

    @Override
    public void delete(Long id) {
        //1. 判断当前菜单是否有子菜单，如果有就不能删除
        Long ChildCount = permissionMapper.findCountByParentId(id);

        if (ChildCount > 0) {
            //说明有子菜单，不能删除
            throw new RuntimeException("当前菜单有子菜单，不能删除");
        }
        //没有子菜单，则可以删除
        super.delete(id);
    }
}
