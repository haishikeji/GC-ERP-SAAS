/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.materialplan.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.px.sys.constant.CommonConstants;
import com.px.produce.materialplan.service.mapstruct.MaterialPlanDetailWrapper;
import com.px.produce.materialplan.service.mapstruct.MaterialPlanWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.produce.materialplan.service.dto.MaterialPlanDTO;
import com.px.produce.materialplan.service.dto.MaterialPlanDetailDTO;
import com.px.produce.materialplan.domain.MaterialPlan;
import com.px.produce.materialplan.domain.MaterialPlanDetail;
import com.px.produce.materialplan.mapper.MaterialPlanMapper;

/**
 * 物料计划单Service
 * @author 品讯科技
 * @version 2022-06-13
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class MaterialPlanService extends ServiceImpl<MaterialPlanMapper, MaterialPlan> {
	/**
	* 子表service
	*/
	@Autowired
	private MaterialPlanDetailService materialPlanDetailService;

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public MaterialPlanDTO findById(String id) {
		MaterialPlanDTO materialPlanDTO = baseMapper.findById ( id );
		materialPlanDTO.setMaterialPlanDetailDTOList(materialPlanDetailService.findList(id));
		return materialPlanDTO;
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <MaterialPlanDTO> findPage(Page <MaterialPlanDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

	/**
	* 保存或者更新
	* @param  materialPlanDTO
	* @return
	*/
	public void saveOrUpdate(MaterialPlanDTO materialPlanDTO) {
		MaterialPlan materialPlan =  MaterialPlanWrapper.INSTANCE.toEntity ( materialPlanDTO );
		super.saveOrUpdate (materialPlan);
		for (MaterialPlanDetailDTO materialPlanDetailDTO : materialPlanDTO.getMaterialPlanDetailDTOList ()){
			if ( CommonConstants.DELETED.equals ( materialPlanDetailDTO.getDelFlag()) ){
				materialPlanDetailService.removeById ( materialPlanDetailDTO.getId () );
			}else{
				MaterialPlanDetail materialPlanDetail = MaterialPlanDetailWrapper.INSTANCE.toEntity ( materialPlanDetailDTO );
				materialPlanDetail.setMaterialPlanId ( materialPlan.getId () );
				materialPlanDetailService.saveOrUpdate ( materialPlanDetail );
			}
		}
	}

	/**
	 * 删除
	 * @param  id
	 * @return
	 */
	public void removeById(String id) {
		super.removeById ( id );
		materialPlanDetailService.lambdaUpdate ().eq ( MaterialPlanDetail::getMaterialPlanId, id ).remove ();
	}
}
