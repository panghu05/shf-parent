package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.PermissionService;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 包名:com.atguigu.controller
 */
@Controller
@RequestMapping("/role")
@SuppressWarnings("unchecked")
public class RoleController extends BaseController {
    private static final String PAGE_ASSIGN_SHOW = "role/assignShow";
    private static final String PAGE_CREATE = "role/create";
    @Reference
    private RoleService roleService;
    @Reference
    private PermissionService permissionService;
    private static final String PAGE_INDEX = "role/index";
    private static final String LIST_ACTION = "redirect:/role";
    private static final String PAGE_EDIT = "role/edit";

    @PreAuthorize("hasAnyAuthority('role.show')")
    @RequestMapping
    public String index(@RequestParam Map filters, Model model) {
        //判断pageSize和pageNum是否有值,如果没值则赋默认值
        if (!filters.containsKey("pageSize")) {
            filters.put("pageSize", 10);
        }
        if (!filters.containsKey("pageNum")) {
            filters.put("pageNum", 1);
        }

        //调用业务层的方法进行分页查询
        PageInfo pageInfo = roleService.findPage(filters);
        //查询到的分页数据存储到请求域中
        model.addAttribute("page", pageInfo);
        //将搜索条件存储到请求域中
        model.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    @PreAuthorize("hasAnyAuthority('role.create')")
    @PostMapping("/save")
    public String save(Role role, Model model) {
        //调用业务层的方法保存角色信息
        roleService.insert(role);
        //显示成功页面，提示"保存角色成功"
        return successPage(model, "保存角色成功");
    }

    @PreAuthorize("hasAnyAuthority('role.edit')")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        //调用业务层的方法根据id查询角色信息
        Role role = roleService.getById(id);
        //将查询到的角色信息存储到请求域
        model.addAttribute("role", role);

        return PAGE_EDIT;
    }

    @PreAuthorize("hasAnyAuthority('role.edit')")
    @PostMapping("/update")
    public String update(Role role, Model model) {
        //调用业务层的方法修改角色信息
        roleService.update(role);
        //显示成功页面，并且提示"修改角色信息成功"
        return successPage(model, "修改角色信息成功");
    }

    @PreAuthorize("hasAnyAuthority('role.delete')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        //调用业务层的方法根据id删除
        roleService.delete(id);
        //重定向查询所有
        return LIST_ACTION;
    }

    @PreAuthorize("hasAnyAuthority('role.assgin')")
    @GetMapping("/assignShow/{roleId}")
    public String assignShow(@PathVariable("roleId") Long roleId, Model model) {
        //1. 将roleId存储到请求域
        model.addAttribute("roleId", roleId);
        //2. 调用业务层的方法查询出角色已分配和未分配的权限
        List<Map<String, Object>> permissionList = permissionService.findPermissionByRoleId(roleId);
        //3. 将permissionList转成JSON字符串存储到请求域
        model.addAttribute("zNodes", JSON.toJSONString(permissionList));
        return PAGE_ASSIGN_SHOW;
    }

    @PreAuthorize("hasAnyAuthority('role.assgin')")
    @PostMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId,
                                   @RequestParam("permissionIds") List<Long> permissionIds,
                                   Model model) {
        //调用业务层的方法保存分配权限
        permissionService.saveRolePermission(roleId, permissionIds);
        return successPage(model, "给角色分配权限成功");
    }

    @PreAuthorize("hasAnyAuthority('role.create')")
    @GetMapping("/create")
    public String create() {
        return PAGE_CREATE;
    }
}
