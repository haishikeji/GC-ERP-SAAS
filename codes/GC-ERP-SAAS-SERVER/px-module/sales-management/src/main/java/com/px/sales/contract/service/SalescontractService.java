/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.sales.contract.service;

import com.px.sales.contract.domain.Salescontract;
import com.px.sales.contract.mapper.SalescontractMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.sales.contract.service.dto.SalescontractDTO;

/**
 * 销售合同Service
 * @author 品讯科技
 * @version 2022-05-30
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class SalescontractService extends ServiceImpl<SalescontractMapper, Salescontract> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public SalescontractDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <SalescontractDTO> findPage(Page <SalescontractDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

	public void deleteBynot() {
		baseMapper.deleteBynot();
	}

    public void updateHetong() {
		baseMapper.updateHetong();
    }
}
