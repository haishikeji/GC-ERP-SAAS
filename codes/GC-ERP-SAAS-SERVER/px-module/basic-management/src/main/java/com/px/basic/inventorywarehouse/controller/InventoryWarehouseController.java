/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.inventorywarehouse.controller;

import javax.validation.Valid;
import com.google.common.collect.Lists;
import com.px.core.query.QueryWrapperGenerator;
import com.px.logging.annotation.ApiLog;
import com.px.basic.inventorywarehouse.service.dto.InventoryWarehouseDTO;
import com.px.basic.inventorywarehouse.service.mapstruct.InventoryWarehouseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.basic.inventorywarehouse.service.InventoryWarehouseService;

/**
 * 存货仓库中间表Controller
 * @author 品讯科技
 * @version 2022-06-09
 */

@Api(tags ="存货仓库中间表")
@RestController
@RequestMapping(value = "/basic/inventorywarehouse/inventoryWarehouse")
public class InventoryWarehouseController {

	@Autowired
	private InventoryWarehouseService inventoryWarehouseService;

	@Autowired
	private InventoryWarehouseWrapper inventoryWarehouseWrapper;

	/**
	 * 存货仓库列表数据
	 */
	@ApiLog("查询存货仓库列表数据")
	@ApiOperation(value = "查询存货仓库列表数据")
	@PreAuthorize("hasAuthority('basic:inventorywarehouse:inventoryWarehouse:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<InventoryWarehouseDTO>> list(InventoryWarehouseDTO inventoryWarehouseDTO, Page<InventoryWarehouseDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (inventoryWarehouseDTO, InventoryWarehouseDTO.class);
		IPage<InventoryWarehouseDTO> result = inventoryWarehouseService.findPage (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取存货仓库数据
	 */
	@ApiLog("根据Id获取存货仓库数据")
	@ApiOperation(value = "根据Id获取存货仓库数据")
	@PreAuthorize("hasAnyAuthority('basic:inventorywarehouse:inventoryWarehouse:view','basic:inventorywarehouse:inventoryWarehouse:add','basic:inventorywarehouse:inventoryWarehouse:edit')")
	@GetMapping("queryById")
	public ResponseEntity<InventoryWarehouseDTO> queryById(String id) {
		return ResponseEntity.ok ( inventoryWarehouseService.findById ( id ) );
	}

	/**
	 * 保存存货仓库
	 */
	@ApiLog("保存存货仓库")
	@ApiOperation(value = "保存存货仓库")
	@PreAuthorize("hasAnyAuthority('basic:inventorywarehouse:inventoryWarehouse:add','basic:inventorywarehouse:inventoryWarehouse:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody InventoryWarehouseDTO inventoryWarehouseDTO) {
		//新增或编辑表单保存
		inventoryWarehouseService.saveOrUpdate (inventoryWarehouseWrapper.toEntity (inventoryWarehouseDTO));
        return ResponseEntity.ok ( "保存存货仓库成功" );
	}


	/**
	 * 删除存货仓库
	 */
	@ApiLog("删除存货仓库")
	@ApiOperation(value = "删除存货仓库")
	@PreAuthorize("hasAuthority('basic:inventorywarehouse:inventoryWarehouse:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        inventoryWarehouseService.removeByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除存货仓库成功" );
	}

}
