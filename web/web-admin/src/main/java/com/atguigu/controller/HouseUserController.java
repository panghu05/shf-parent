package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.HouseUser;
import com.atguigu.service.HouseUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 包名:com.atguigu.controller
 *
 */
@Controller
@RequestMapping("/houseUser")
public class HouseUserController extends BaseController {
    private static final String PAGE_CREATE = "houseUser/create";
    private static final String PAGE_EDIT = "houseUser/edit";
    private static final String SHOW_ACTION = "redirect:/house/";
    @Reference
    private HouseUserService houseUserService;
    @GetMapping("/create")
    public String create(HouseUser houseUser, Model model){
        //将houseUser存储到请求域
        model.addAttribute("houseUser",houseUser);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(HouseUser houseUser,Model model){
        //调用业务层的方法保存房东信息
        houseUserService.insert(houseUser);

        return successPage(model,"保存房东信息成功");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model){
        //调用业务层的方法根据id查询房东信息
        HouseUser houseUser = houseUserService.getById(id);
        //将房东信息存储到请求域
        model.addAttribute("houseUser",houseUser);
        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(HouseUser houseUser,Model model){
        //调用业务层的方法修改房东信息
        houseUserService.update(houseUser);
        return successPage(model,"修改房东信息成功");
    }

    @GetMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,
                         @PathVariable("id") Long id){
        //调用业务层的方法根据id删除房东
        houseUserService.delete(id);

        return SHOW_ACTION + houseId;
    }
}
