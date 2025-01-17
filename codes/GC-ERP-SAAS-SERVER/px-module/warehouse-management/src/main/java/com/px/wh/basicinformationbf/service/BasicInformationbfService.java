/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.basicinformationbf.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.px.wh.basicinformationbf.service.dto.BasicInformationbfDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.wh.basicinformationbf.domain.BasicInformationbf;
import com.px.wh.basicinformationbf.mapper.BasicInformationbfMapper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 信息Service
 * @author 品讯科技
 * @version 2023-05-09
 */
@Service
@Transactional
@DS("#session.sss")//从session获取
public class BasicInformationbfService extends ServiceImpl<BasicInformationbfMapper, BasicInformationbf> {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public BasicInformationbfDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 自定义分页检索
	 * @param page
	 * @param queryWrapper
	 * @return
	 */
	public IPage <BasicInformationbfDTO> findPage(Page <BasicInformationbfDTO> page, QueryWrapper queryWrapper) {
		queryWrapper.eq ("a.del_flag", 0 ); // 排除已经删除
		return  baseMapper.findList (page, queryWrapper);
	}

	public String getdbnameBylogin(String loginName) {

		ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		HttpServletRequest request = servletRequestAttributes.getRequest();
		Object sss = request.getSession().getAttribute("sss");
		return  baseMapper.getdbnameBylogin(loginName);
	}

}
