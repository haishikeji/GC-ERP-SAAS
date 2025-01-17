/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.delivery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.px.report.delivery.domain.DeliveryReport;
import com.px.report.delivery.service.dto.DeliveryReportDTO;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 产品出库明细MAPPER接口
 * @author 品讯科技
 * @version 2022-06-10
 */
public interface DeliveryReportMapper extends BaseMapper<DeliveryReport> {

    /**
     * 根据id获取产品出库明细
     * @param id
     * @return
     */
    DeliveryReportDTO findById(String id);

    /**
     * 获取产品出库明细列表
     *
     * @return
     */
    IPage <DeliveryReportDTO> findList(Page <DeliveryReportDTO> page, @Param("wr") DeliveryReportDTO deliveryReportDTO);


}
