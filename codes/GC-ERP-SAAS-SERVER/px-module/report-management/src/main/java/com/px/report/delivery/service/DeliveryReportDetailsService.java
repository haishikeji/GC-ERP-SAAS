/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.delivery.service;

import java.util.List;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.px.report.delivery.service.dto.DeliveryReportDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.report.delivery.service.dto.DeliveryReportDetailsDTO;
import com.px.report.delivery.domain.DeliveryReportDetails;
import com.px.report.delivery.mapper.DeliveryReportDetailsMapper;

/**
 * 产品出库明细详情Service
 * @author 品讯科技
 * @version 2022-06-10
 */
@DS("#session.sss")//从session获取
@Service
@Transactional
public class DeliveryReportDetailsService extends ServiceImpl<DeliveryReportDetailsMapper, DeliveryReportDetails> {

	/**
	 * 根据id查询
	 * @return
	 */
	public DeliveryReportDetailsDTO findById(String id) {
		return baseMapper.findById ( id );
	}

	/**
	 * 查询列表
	 * @return
	 */
	public List <DeliveryReportDetailsDTO> findList(DeliveryReportDTO deliveryReportDTO) {
		return  baseMapper.findList (deliveryReportDTO);
	}

}
