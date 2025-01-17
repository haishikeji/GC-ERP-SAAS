/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.materialOutReport.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.px.report.materialOutReport.domain.MaterialOutReportDetail;
import com.px.report.materialOutReport.service.dto.MaterialOutReportDetailDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 物料出库详情表MAPPER接口
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
public interface MaterialOutReportDetailMapper extends BaseMapper<MaterialOutReportDetail> {

    /**
     * 获取物料出库详情表列表
     *
     * @param queryWrapper
     * @return
     */
    List<MaterialOutReportDetailDTO> findList(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
