/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.activiti.service;

import com.px.test.activiti.service.dto.TestActivitiAuditDTO;
import com.px.test.activiti.service.mapstruct.TestActivitiAuditWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.test.activiti.domain.TestActivitiAudit;
import com.px.test.activiti.mapper.TestActivitiAuditMapper;

/**
 * 薪酬调整申请Service
 * @author 品讯科技
 * @version 2022-03-20
 */
@Service
@Transactional
public class TestActivitiAuditService extends ServiceImpl<TestActivitiAuditMapper, TestActivitiAudit> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public TestActivitiAuditDTO findById(String id) {
		TestActivitiAuditDTO testActivitiAuditDTO = baseMapper.findById ( id );
		return testActivitiAuditDTO;
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <TestActivitiAuditDTO> findPage(Page <TestActivitiAuditDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

	/**
	* 保存或者更新
	* @param  testActivitiAuditDTO
	* @return
	*/
	public void saveOrUpdate(TestActivitiAuditDTO testActivitiAuditDTO) {
		TestActivitiAudit testActivitiAudit =  TestActivitiAuditWrapper.INSTANCE.toEntity ( testActivitiAuditDTO );
		super.saveOrUpdate (testActivitiAudit);
		testActivitiAuditDTO.setId ( testActivitiAudit.getId () );
	}

	/**
	 * 删除
	 * @param  id
	 * @return
	 */
	public void removeById(String id) {
		super.removeById ( id );
	}

}
