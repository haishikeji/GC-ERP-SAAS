/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.inventorybom.mapper;

;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
;
import com.px.basic.inventorybom.service.dto.InventorybomDetailDTO;
import com.px.basic.inventorybom.domain.InventorybomDetail;

/**
 * 物料清单详情MAPPER接口
 * @author 品讯科技
 * @version 2022-05-29
 */
public interface InventorybomDetailMapper extends BaseMapper<InventorybomDetail> {

    /**
     * 根据id获取物料清单详情
     * @param id
     * @return
     */
    InventorybomDetailDTO findById(String id);

    /**
     * 获取物料清单详情列表
     *
     * @param InventorybomId
     * @return
     */
    List <InventorybomDetailDTO> findList(String InventorybomId);

}
