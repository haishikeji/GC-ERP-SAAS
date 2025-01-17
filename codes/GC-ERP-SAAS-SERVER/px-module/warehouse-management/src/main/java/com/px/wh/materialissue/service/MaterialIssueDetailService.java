/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.materialissue.service;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.px.wh.materialissue.domain.MaterialIssueDetail;
import com.px.wh.materialissue.service.dto.MaterialIssueDetailDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.wh.materialissue.mapper.MaterialIssueDetailMapper;

/**
 * 物料出库详情表Service
 * @author 品讯科技
 * @version 2022-06-17
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class MaterialIssueDetailService extends ServiceImpl<MaterialIssueDetailMapper, MaterialIssueDetail> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public MaterialIssueDetailDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询列表
	 * @param materialIssueId
	 * @return
	 */
	public List <MaterialIssueDetailDTO> findList(String materialIssueId) {
		return  baseMapper.findList (materialIssueId);
	}

}
