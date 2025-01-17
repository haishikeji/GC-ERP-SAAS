/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.inventoryStock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.px.wh.inventoryStock.domain.WarehouseStockDetail;
import com.px.wh.inventoryStock.service.dto.WarehouseStockDetailDTO;

import java.util.List;

/**
 * 仓库库存详情表MAPPER接口
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
public interface WarehouseStockDetailMapper extends BaseMapper<WarehouseStockDetail> {

    /**
     * 获取仓库库存详情表列表
     *
     * @param id
     * @return
     */
    List<WarehouseStockDetailDTO> findList(String id);

}
