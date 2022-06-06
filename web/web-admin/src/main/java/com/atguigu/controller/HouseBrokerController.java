package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Admin;
import com.atguigu.entity.HouseBroker;
import com.atguigu.service.AdminService;
import com.atguigu.service.HouseBrokerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 包名:com.atguigu.controller
 *
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController extends BaseController {
    private static final String PAGE_CREATE = "houseBroker/create";
    private static final String PAGE_EDIT = "houseBroker/edit";
    private static final String SHOW_ACTION = "redirect:/house/";
    @Reference
    private AdminService adminService;
    @Reference
    private HouseBrokerService houseBrokerService;
    @GetMapping("/create")
    public String create(HouseBroker houseBroker, Model model){
        //使用houseBroker对象封装请求参数中houseId
        //将houseBroker存储到请求域
        model.addAttribute("houseBroker",houseBroker);

        //查询出所有管理员列表存储到请求域
        saveAdminListToModel(model);
        return PAGE_CREATE;
    }

    private void saveAdminListToModel(Model model) {
        //查询出admin列表
        List<Admin> adminList = adminService.findAll();
        //将admin列表存储到请求域
        model.addAttribute("adminList",adminList);
    }

    @PostMapping("/save")
    public String save(HouseBroker houseBroker,Model model){
        //调用adminService的方法根据id查询管理员详情
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        //将管理员的相关信息存储到houseBroker中
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);

        return successPage(model,"新增经纪人信息成功");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        //id代表的是经纪人表中的id字段的值
        //调用houseBrokerService的方法根据id查询经纪人信息
        HouseBroker houseBroker = houseBrokerService.getById(id);
        //将经纪人信息存储到请求域
        model.addAttribute("houseBroker",houseBroker);
        //查询出管理员列表，将管理员列表存储到请求域
        saveAdminListToModel(model);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(HouseBroker houseBroker,Model model){
        //调用adminService的方法根据id查询管理员的信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        //将管理员的相关信息设置到houseBroker中
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        //调用houseBrokerService修改经纪人信息
        houseBrokerService.update(houseBroker);
        return successPage(model,"修改经纪人信息成功");
    }

    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("id") Long id){
        //1. 调用houseBrokerService的方法根据id删除经纪人
        houseBrokerService.delete(id);

        return SHOW_ACTION + houseId;
    }
}
