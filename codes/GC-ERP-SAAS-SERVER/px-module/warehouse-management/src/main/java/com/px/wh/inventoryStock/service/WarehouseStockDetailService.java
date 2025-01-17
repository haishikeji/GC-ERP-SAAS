/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.inventoryStock.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.wh.inventoryStock.domain.WarehouseStockDetail;
import com.px.wh.inventoryStock.mapper.WarehouseStockDetailMapper;
import com.px.wh.inventoryStock.service.dto.WarehouseStockDetailDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 仓库库存详情表Service
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class WarehouseStockDetailService extends ServiceImpl<WarehouseStockDetailMapper, WarehouseStockDetail> {

    /**
     * 查询列表
     *
     * @param id
     * @return
     */
    public List<WarehouseStockDetailDTO> findList(String id) {
        return baseMapper.findList(id);
    }

}
