package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.entity.Dict;
import com.atguigu.mapper.DictMapper;
import com.atguigu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 包名:com.atguigu.service.impl
 *
 */
@Transactional
@Service(interfaceClass = DictService.class)
public class DictServiceImpl implements DictService {
    @Autowired
    private DictMapper dictMapper;
    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        //1. 调用持久层方法，根据父节点id查询List<Dict>
        List<Dict> dictList = dictMapper.findListByParentId(id);

        /*List<Map<String, Object>> znodes = new ArrayList<>();
        //2. 将dictList转成List<Map>
        for (Dict dict : dictList) {
            Map<String,Object> znode = new HashMap();
            znode.put("id",dict.getId());
            znode.put("name",dict.getName());
            znode.put("isParent",dictMapper.countIsParent(dict.getId()) > 0);

            //将znode添加到znodes中
            znodes.add(znode);
        }*/

        //使用Stream流
        List<Map<String, Object>> znodes = dictList.stream()
                .map(dict -> {
                    Map<String, Object> znode = new HashMap<>();
                    //往znode中存放id
                    znode.put("id", dict.getId());
                    //往znode中存放name
                    znode.put("name", dict.getName());
                    //往znode中存放isParent
                    znode.put("isParent", dictMapper.countIsParent(dict.getId()) > 0);
                    return znode;
                })
                .collect(Collectors.toList());
        return znodes;
    }

    @Override
    public List<Dict> findDictListByParentDictCode(String parentDictCode) {
        return dictMapper.findDictListByParentDictCode(parentDictCode);
    }

    @Override
    public List<Dict> findDictListByParentId(Long parentId) {
        return dictMapper.findListByParentId(parentId);
    }
}
