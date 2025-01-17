/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.materialOutReport.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.report.materialOutReport.domain.MaterialOutReportDetail;
import com.px.report.materialOutReport.mapper.MaterialOutReportDetailMapper;
import com.px.report.materialOutReport.service.dto.MaterialOutReportDetailDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 物料入库详情表Service
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class MaterialOutReportDetailService extends ServiceImpl<MaterialOutReportDetailMapper, MaterialOutReportDetail> {

    /**
     * 查询列表
     *
     * @param queryWrapper
     * @return
     */
    public List<MaterialOutReportDetailDTO> findList(QueryWrapper queryWrapper) {
        return baseMapper.findList(queryWrapper);
    }

}
