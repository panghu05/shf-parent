package com.atguigu.base;

import com.atguigu.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 包名:com.atguigu.base
 *
 * propagation:事务的传播性，它所研究的是当方法嵌套执行的事务外层方法的事务是否会传播到内层方法
 * 1. REQUIRED:如果外层方法有事务，那么内层方法就加入外层方法的事务一起执行(跟外层方法共用事务);如果外层方法没有事务，内层方法则自己新建事务执行
 * 2. SUPPORTS:如果外层方法有事务，那么内层方法就加入外层方法的事务一起执行(跟外层方法共用事务);如果外层方法没有事务，内层方法则以非事务方式执行
 */
@Transactional(propagation = Propagation.REQUIRED)
public abstract class BaseServiceImpl<T> {
    public abstract BaseMapper<T> getEntityMapper();

    public void insert(T t) {
        getEntityMapper().insert(t);
    }


    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public T getById(Long id) {
        return getEntityMapper().getById(id);
    }

    public void update(T t) {
        getEntityMapper().update(t);
    }

    public void delete(Long id) {
        getEntityMapper().delete(id);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public PageInfo<T> findPage(Map<String, Object> filters) {
        //将pageSize和pageNum强转成int类型
        int pageNum = CastUtil.castInt(filters.get("pageNum"),1);
        int pageSize = CastUtil.castInt(filters.get("pageSize"),10);
        PageHelper.startPage(pageNum,pageSize);
        //调用持久层的方法查询数据集
        //封装返回结果
        return new PageInfo<>(getEntityMapper().findPage(filters),10);
    }
}
