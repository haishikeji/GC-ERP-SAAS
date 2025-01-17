/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.inventoryStock.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.wh.inventoryStock.domain.InventoryStock;
import com.px.wh.inventoryStock.mapper.InventoryStockMapper;
import com.px.wh.inventoryStock.service.dto.InventoryStockDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 存货库存管理Service
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class InventoryStockService extends ServiceImpl<InventoryStockMapper, InventoryStock> {

    /**
     * 自定义分页检索
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage<InventoryStockDTO> findPage(Page<InventoryStockDTO> page, QueryWrapper queryWrapper) {
        return baseMapper.findList(page, queryWrapper);
    }
}
