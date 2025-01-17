/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.materialplan.service;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.produce.materialplan.service.dto.MaterialPlanDetailDTO;
import com.px.produce.materialplan.domain.MaterialPlanDetail;
import com.px.produce.materialplan.mapper.MaterialPlanDetailMapper;

/**
 * 物料计划单详情Service
 * @author 品讯科技
 * @version 2022-06-13
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class MaterialPlanDetailService extends ServiceImpl<MaterialPlanDetailMapper, MaterialPlanDetail> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public MaterialPlanDetailDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询列表
	 * @param materialPlanId
	 * @return
	 */
	public List <MaterialPlanDetailDTO> findList(String materialPlanId) {
		return  baseMapper.findList (materialPlanId);
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage<MaterialPlanDetailDTO> findPage(Page<MaterialPlanDetailDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findPage (page, queryWrapper);
	}
}
