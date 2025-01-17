/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.orderattribute.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.basic.orderattribute.domain.OrderAttribute;
import com.px.basic.orderattribute.mapper.OrderAttributeMapper;
import com.px.basic.orderattribute.service.dto.OrderAttributeDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单属性管理Service
 * @author 品讯科技
 * @version 2022-05-26
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class OrderAttributeService extends ServiceImpl<OrderAttributeMapper, OrderAttribute> {

    public IPage<OrderAttributeDTO> findPage(Page<OrderAttributeDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.eq ("a.del_flag", 0 ); // 排除已经禁用删除*/
        return  baseMapper.findList (page, queryWrapper);
    }

    public IPage<OrderAttributeDTO> findPage1(Page<OrderAttributeDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.ne ("a.del_flag", 1 ); // 排除已经删除*/
        return  baseMapper.findList (page, queryWrapper);
    }

    public void disableById(String id) {
        OrderAttribute orderAttribute = new OrderAttribute();
        orderAttribute.setId(id);
        orderAttribute.setDelFlag(2);
        baseMapper.updateStateById(orderAttribute);
    }

    public void enableById(String id) {
        OrderAttribute orderAttribute = new OrderAttribute();
        orderAttribute.setId(id);
        orderAttribute.setDelFlag(0);
        baseMapper.updateStateById(orderAttribute);
    }
}
