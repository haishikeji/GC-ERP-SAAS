/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.grid.service;

import com.px.test.grid.service.dto.TestCountryDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.test.grid.domain.TestCountry;
import com.px.test.grid.mapper.TestCountryMapper;

/**
 * 国家Service
 * @author 品讯科技
 * @version 2022-03-18
 */
@Service
@Transactional
public class TestCountryService extends ServiceImpl<TestCountryMapper, TestCountry> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TestCountryDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <TestCountryDTO> findPage(Page <TestCountryDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

}
