/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.purchasing.requisition.mapper;

;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
;
import com.px.purchasing.requisition.service.dto.RequisitionDetailDTO;
import com.px.purchasing.requisition.domain.RequisitionDetail;

/**
 * 请购单存货详情MAPPER接口
 * @author 品讯科技
 * @version 2022-05-30
 */
public interface RequisitionDetailMapper extends BaseMapper<RequisitionDetail> {

    /**
     * 根据id获取请购单存货详情
     * @param id
     * @return
     */
    RequisitionDetailDTO findById(String id);

    /**
     * 获取请购单存货详情列表
     *
     * @param RequisitionId
     * @return
     */
    List <RequisitionDetailDTO> findList(String RequisitionId);

}
