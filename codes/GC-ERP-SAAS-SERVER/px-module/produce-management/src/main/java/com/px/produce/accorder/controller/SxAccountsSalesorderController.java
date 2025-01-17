/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.accorder.controller;

import javax.validation.Valid;
import com.px.core.query.QueryWrapperGenerator;
import com.px.logging.annotation.ApiLog;
import com.px.produce.accorder.service.dto.SxAccountsSalesorderdetailsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.produce.accorder.service.dto.SxAccountsSalesorderDTO;
import com.px.produce.accorder.service.SxAccountsSalesorderService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 应收Controller
 * @author 品讯科技
 * @version 2023-11-13
 */
@Api(tags ="应收")
@RestController
@RequestMapping(value = "/produce/accorder/sxAccountsSalesorder")
public class SxAccountsSalesorderController {

	@Autowired
	private SxAccountsSalesorderService sxAccountsSalesorderService;

	/**
	 * 应收列表数据
	 */
	@ApiLog("查询应收列表数据")
	@ApiOperation(value = "查询应收列表数据")
	@PreAuthorize("hasAuthority('produce:accorder:sxAccountsSalesorder:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<SxAccountsSalesorderDTO>> list(SxAccountsSalesorderDTO sxAccountsSalesorderDTO, Page<SxAccountsSalesorderDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (sxAccountsSalesorderDTO, SxAccountsSalesorderDTO.class);
		IPage<SxAccountsSalesorderDTO> result = sxAccountsSalesorderService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取应收数据
	 */
	@ApiLog("根据Id获取应收数据")
	@ApiOperation(value = "根据Id获取应收数据")
	@PreAuthorize("hasAnyAuthority('produce:accorder:sxAccountsSalesorder:view','produce:accorder:sxAccountsSalesorder:add','produce:accorder:sxAccountsSalesorder:edit')")
	@GetMapping("queryById")
	public ResponseEntity<SxAccountsSalesorderDTO> queryById(String id) {
		return ResponseEntity.ok ( sxAccountsSalesorderService.findById ( id ) );
	}

	/**
	 * 保存应收
	 */
	@ApiLog("保存应收")
	@ApiOperation(value = "保存应收")
	@PreAuthorize("hasAnyAuthority('produce:accorder:sxAccountsSalesorder:add','produce:accorder:sxAccountsSalesorder:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody SxAccountsSalesorderDTO sxAccountsSalesorderDTO) {
		//新增或编辑表单保存
		BigDecimal totalityIncluding = new BigDecimal(0);
		if(StringUtils.isBlank(sxAccountsSalesorderDTO.getId())){
			sxAccountsSalesorderService.saveOrUpdate (sxAccountsSalesorderDTO);
		}else {
			List<SxAccountsSalesorderdetailsDTO> sxAccountsSalesorderdetailsDTOList = sxAccountsSalesorderDTO.getSxAccountsSalesorderdetailsDTOList();
			for (SxAccountsSalesorderdetailsDTO sxAccountsSalesorderdetailsDTO : sxAccountsSalesorderdetailsDTOList) {
				totalityIncluding = totalityIncluding.add(new BigDecimal(sxAccountsSalesorderdetailsDTO.getAccountsReceivableAmount()));
			}
			sxAccountsSalesorderDTO.setProcInsId(totalityIncluding.toString());
			sxAccountsSalesorderService.saveOrUpdate (sxAccountsSalesorderDTO);
		}

        return ResponseEntity.ok ( "保存应收成功" );
	}


	/**
	 * 删除应收
	 */
	@ApiLog("删除应收")
	@ApiOperation(value = "删除应收")
	@PreAuthorize("hasAuthority('produce:accorder:sxAccountsSalesorder:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			sxAccountsSalesorderService.removeById ( id );
		}
		return ResponseEntity.ok( "删除应收成功" );
	}

}
