/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.report.warehousing.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.logging.annotation.ApiLog;
import com.px.report.warehousing.service.WarehousingReportService;
import com.px.report.warehousing.service.dto.WarehousingReportDTO;
import com.px.report.warehousing.service.dto.WarehousingReportDetailsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品入库明细Controller
 * @author 品讯科技
 * @version 2022-06-10
 */
@Api(tags ="产品入库明细")
@RestController
@RequestMapping(value = "/report/warehousing/warehousingReport")
public class WarehousingReportController {

	@Autowired
	private WarehousingReportService warehousingReportService;

	/**
	 * 产品入库明细列表数据
	 */
	@ApiLog("查询产品入库明细列表数据")
	@ApiOperation(value = "查询产品入库明细列表数据")
	@PreAuthorize("hasAuthority('report:warehousing:warehousingReport:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<WarehousingReportDTO>> list(WarehousingReportDTO warehousingReportDTO, Page<WarehousingReportDTO> page) throws Exception {
		IPage<WarehousingReportDTO> result = warehousingReportService.findPage (page, warehousingReportDTO);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取产品入库明细数据
	 */
	@ApiLog("根据Id获取产品入库明细数据")
	@ApiOperation(value = "根据Id获取产品入库明细数据")
	@PreAuthorize("hasAnyAuthority('report:warehousing:warehousingReport:view')")
	@GetMapping("queryById")
	public ResponseEntity<List<WarehousingReportDetailsDTO>> queryById(WarehousingReportDTO warehousingReportDTO) {
		return ResponseEntity.ok ( warehousingReportService.findById ( warehousingReportDTO ) );
	}

}
