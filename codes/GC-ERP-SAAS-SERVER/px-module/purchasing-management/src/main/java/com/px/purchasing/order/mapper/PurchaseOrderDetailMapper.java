/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.purchasing.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.px.purchasing.order.domain.PurchaseOrderDetail;
import com.px.purchasing.order.service.dto.PurchaseOrderDetailDTO;
import com.px.purchasing.order.service.dto.PurchaseOrderDetailDTO1;

import java.util.HashMap;
import java.util.List;

;
;

/**
 * 采购订单详情MAPPER接口
 * @author 品讯科技
 * @version 2022-06-01
 */
public interface PurchaseOrderDetailMapper extends BaseMapper<PurchaseOrderDetail> {

    /**
     * 根据id获取采购订单详情
     * @param id
     * @return
     */
    PurchaseOrderDetailDTO findById(String id);

    /**
     * 获取采购订单详情列表
     *
     * @param PurchaseOrderId
     * @return
     */
    List <PurchaseOrderDetailDTO> findList(String PurchaseOrderId);

    List <PurchaseOrderDetailDTO1> findList1(HashMap hashMap);

    /**
     * 查询列表
     * @param
     * @return
     */
    public List <PurchaseOrderDetailDTO> findLists(String supplierId,String inventoryId);

    void updatepurslze(String id);

    void updatepurslze1(String id);

    void updatepurslze2(String id);

    void updatepurslze3(String id);
}
