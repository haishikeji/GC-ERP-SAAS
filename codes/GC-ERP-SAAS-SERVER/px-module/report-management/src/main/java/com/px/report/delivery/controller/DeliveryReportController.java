/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.delivery.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.logging.annotation.ApiLog;
import com.px.report.delivery.service.DeliveryReportService;
import com.px.report.delivery.service.dto.DeliveryReportDTO;
import com.px.report.delivery.service.dto.DeliveryReportDetailsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品出库明细Controller
 * @author 品讯科技
 * @version 2022-06-10
 */
@Api(tags ="产品出库明细")
@RestController
@RequestMapping(value = "/report/delivery/deliveryReport")
public class DeliveryReportController {

	@Autowired
	private DeliveryReportService deliveryReportService;

	/**
	 * 产品出库明细列表数据
	 */
	@ApiLog("查询产品出库明细列表数据")
	@ApiOperation(value = "查询产品出库明细列表数据")
	@PreAuthorize("hasAuthority('report:delivery:deliveryReport:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<DeliveryReportDTO>> list(DeliveryReportDTO deliveryReportDTO, Page<DeliveryReportDTO> page) throws Exception {
		IPage<DeliveryReportDTO> result = deliveryReportService.findPage (page, deliveryReportDTO);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取产品出库明细数据
	 */
	@ApiLog("根据Id获取产品出库明细数据")
	@ApiOperation(value = "根据Id获取产品出库明细数据")
	@PreAuthorize("hasAnyAuthority('report:delivery:deliveryReport:view')")
	@GetMapping("queryById")
	public ResponseEntity<List<DeliveryReportDetailsDTO>> queryById(DeliveryReportDTO deliveryReportDTO) {
		return ResponseEntity.ok ( deliveryReportService.findById ( deliveryReportDTO ) );
	}

}
