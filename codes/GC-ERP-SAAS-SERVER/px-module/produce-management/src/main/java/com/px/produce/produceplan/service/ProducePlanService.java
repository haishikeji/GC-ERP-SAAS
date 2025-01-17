/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.produceplan.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.px.sys.constant.CommonConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.produce.produceplan.service.dto.ProducePlanDTO;
import com.px.produce.produceplan.service.dto.ProducePlanDetailDTO;
import com.px.produce.produceplan.service.mapstruct.ProducePlanWrapper;
import com.px.produce.produceplan.service.mapstruct.ProducePlanDetailWrapper;
import com.px.produce.produceplan.domain.ProducePlan;
import com.px.produce.produceplan.domain.ProducePlanDetail;
import com.px.produce.produceplan.mapper.ProducePlanMapper;

/**
 * 生产计划单Service
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class ProducePlanService extends ServiceImpl<ProducePlanMapper, ProducePlan> {
    /**
     * 子表service
     */
    @Autowired
    private ProducePlanDetailService producePlanDetailService;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public ProducePlanDTO findById(String id) {
        ProducePlanDTO producePlanDTO = baseMapper.findById(id);
        producePlanDTO.setProducePlanDetailDTOList(producePlanDetailService.findList(id));
        return producePlanDTO;
    }

    public ProducePlanDTO findById1(String id) {
        ProducePlanDTO producePlanDTO = baseMapper.findById1(id);
        producePlanDTO.setProducePlanDetailDTOList(producePlanDetailService.findList(id));
        return producePlanDTO;

    }

    /**
     * 自定义分页检索
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage<ProducePlanDTO> findPage(Page<ProducePlanDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.eq("a.del_flag", 0); // 排除已经删除
        return baseMapper.findList(page, queryWrapper);
    }

    /**
     * 自定义分页检索
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage<ProducePlanDTO> findPage1(Page<ProducePlanDTO> page, QueryWrapper queryWrapper) {
        queryWrapper.eq("a.del_flag", 0); // 排除已经删除
        return baseMapper.findList1(page, queryWrapper);
    }

    /**
     * 保存或者更新
     *
     * @param producePlanDTO
     * @return
     */
    public void saveOrUpdate(ProducePlanDTO producePlanDTO) {
        ProducePlan producePlan = ProducePlanWrapper.INSTANCE.toEntity(producePlanDTO);
        super.saveOrUpdate(producePlan);
        for (ProducePlanDetailDTO producePlanDetailDTO : producePlanDTO.getProducePlanDetailDTOList()) {
            if (CommonConstants.DELETED.equals(producePlanDetailDTO.getDelFlag())) {
                producePlanDetailService.removeById(producePlanDetailDTO.getId());
            } else {
                ProducePlanDetail producePlanDetail = ProducePlanDetailWrapper.INSTANCE.toEntity(producePlanDetailDTO);
                producePlanDetail.setProducePlanId(producePlan.getId());
                producePlanDetail.setSalesorderBatchnumber(producePlanDetailDTO.getSalesorderBatchnumber());
                producePlanDetailService.saveOrUpdate(producePlanDetail);
            }
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public void removeById(String id) {
        super.removeById(id);
        producePlanDetailService.lambdaUpdate().eq(ProducePlanDetail::getProducePlanId, id).remove();
    }


}
