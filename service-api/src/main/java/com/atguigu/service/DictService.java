package com.atguigu.service;

import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.atguigu.service
 *
 */
public interface DictService {
    /**
     * 根据父节点id查询数字字典
     * @param id
     * @return
     */
    List<Map<String,Object>> findZnodes(Long id);

    /**
     * 根据父节点的dictCode查询子节点列表
     * @param parentDictCode
     * @return
     */
    List<Dict> findDictListByParentDictCode(String parentDictCode);

    /**
     * 根据父节点id查询子节点列表
     * @param parentId
     * @return
     */
    List<Dict> findDictListByParentId(Long parentId);
}
