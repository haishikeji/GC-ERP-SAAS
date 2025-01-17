/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.materialIntoReport.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.report.materialIntoReport.domain.MaterialIntoReport;
import com.px.report.materialIntoReport.service.dto.MaterialIntoReportDTO;
import org.apache.ibatis.annotations.Param;

/**
 * 物料入库详情MAPPER接口
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
public interface MaterialIntoReportMapper extends BaseMapper<MaterialIntoReport> {

    /**
     * 获取物料入库详情列表
     *
     * @param queryWrapper
     * @return
     */
    IPage<MaterialIntoReportDTO> findList(Page<MaterialIntoReportDTO> page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
