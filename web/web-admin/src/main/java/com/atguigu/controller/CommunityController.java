package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Community;
import com.atguigu.entity.Dict;
import com.atguigu.service.CommunityService;
import com.atguigu.service.DictService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.atguigu.controller
 *
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {
    private static final String PAGE_INDEX = "community/index";
    private static final String PAGE_CREATE = "community/create";
    private static final String PAGE_EDIT = "community/edit";
    private static final String LIST_ACTION = "redirect:/community";
    @Reference
    private DictService dictService;
    @Reference
    private CommunityService communityService;
    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters, Model model){
        //处理pageNum和pageSize为空的情况
        if (filters.get("pageNum") == null || "".equals(filters.get("pageNum"))) {
            filters.put("pageNum",1);
        }
        if (filters.get("pageSize") == null || "".equals(filters.get("pageSize"))) {
            filters.put("pageSize",10);
        }
        //查询"beijing"的所有区域
        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");
        PageInfo<Community> pageInfo = communityService.findPage(filters);
        //将分页数据存储到请求域
        model.addAttribute("page",pageInfo);
        //将搜索条件存储到请求域

        //处理filters中没有areaId和plateId的情况
        if (!filters.containsKey("areaId")){
            filters.put("areaId",0);
        }
        if (!filters.containsKey("plateId")){
            filters.put("plateId",0);
        }
        model.addAttribute("filters",filters);
        //将areaList存储到请求域
        model.addAttribute("areaList",areaList);
        return PAGE_INDEX;
    }

    @GetMapping("/create")
    public String create(Model model){
        //调用业务层的方法查询"beijing"的所有区域
        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");
        //将areaList存储到请求域中
        model.addAttribute("areaList",areaList);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    public String save(Community community,Model model){
        //调用业务层的方法保存小区数据
        communityService.insert(community);
        return successPage(model,"保存小区信息成功");
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        //调用业务层的方法根据id查询小区信息
        Community community = communityService.getById(id);
        //查询北京的所有区域
        List<Dict> areaList = dictService.findDictListByParentDictCode("beijing");
        //将查询到的小区信息以及areaList存储到请求域
        model.addAttribute("areaList",areaList);
        model.addAttribute("community",community);

        return PAGE_EDIT;
    }

    @PostMapping("/update")
    public String update(Community community,Model model){
        //调用业务层的方法修改小区信息
        communityService.update(community);
        return successPage(model,"修改小区信息成功");
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        //调用业务层的方法根据id删除小区信息
        communityService.delete(id);
        return LIST_ACTION;
    }
}
