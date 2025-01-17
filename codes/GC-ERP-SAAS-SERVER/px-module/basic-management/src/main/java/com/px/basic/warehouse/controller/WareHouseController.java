/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.warehouse.controller;

import javax.validation.Valid;

import com.px.core.query.QueryWrapperGenerator;
import com.px.logging.annotation.ApiLog;
import com.px.basic.warehouse.domain.WareHouse;
import com.px.basic.warehouse.service.dto.WareHouseDTO;
import com.px.basic.warehouse.service.mapstruct.WareHouseWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.basic.warehouse.service.WareHouseService;

/**
 * 仓库基础信息Controller
 * @author 品讯科技
 * @version 2022-05-26
 */

@Api(tags ="仓库基础信息")
@RestController
@RequestMapping(value = "/basic/warehouse/wareHouse")
public class WareHouseController {

	@Autowired
	private WareHouseService wareHouseService;

	@Autowired
	private WareHouseWrapper wareHouseWrapper;

	/**
	 * 仓库基础信息列表数据
	 */
	@ApiLog("查询仓库基础信息列表数据")
	@ApiOperation(value = "查询仓库基础信息列表数据")
	@PreAuthorize("hasAuthority('basic:warehouse:wareHouse:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<WareHouseDTO>> list(WareHouseDTO wareHouseDTO, Page<WareHouseDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (wareHouseDTO, WareHouseDTO.class);
		IPage<WareHouseDTO> result = wareHouseService.findPage(page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取仓库基础信息数据
	 */
	@ApiLog("根据Id获取仓库基础信息数据")
	@ApiOperation(value = "根据Id获取仓库基础信息数据")
	@PreAuthorize("hasAnyAuthority('basic:warehouse:wareHouse:view','basic:warehouse:wareHouse:add','basic:warehouse:wareHouse:edit')")
	@GetMapping("queryById")
	public ResponseEntity<WareHouseDTO> queryById(String id) {
		return ResponseEntity.ok ( wareHouseWrapper.toDTO ( wareHouseService.getById ( id ) ) );
	}

	/**
	 * 保存仓库基础信息
	 */
	@ApiLog("保存仓库基础信息")
	@ApiOperation(value = "保存仓库基础信息")
	@PreAuthorize("hasAnyAuthority('basic:warehouse:wareHouse:add','basic:warehouse:wareHouse:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody WareHouseDTO wareHouseDTO) {

        int row = wareHouseService.lambdaQuery ().eq(WareHouse::getName, wareHouseDTO.getName()).ne(WareHouse::getId, wareHouseDTO.getId()).count ();
        if (row >0){
            return new ResponseEntity("仓库-"+wareHouseDTO.getName()+"-已存在！", HttpStatus.INTERNAL_SERVER_ERROR);
        }

		//新增或编辑表单保存
		wareHouseService.saveOrUpdate (wareHouseWrapper.toEntity (wareHouseDTO));
        return ResponseEntity.ok ( "保存仓库基础信息成功" );
	}


	/**
	 * 禁用仓库基础信息
	 */
	@ApiLog("禁用仓库基础信息")
	@ApiOperation(value = "删除仓库基础信息")
	@PreAuthorize("hasAuthority('basic:warehouse:wareHouse:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
//		String idArray[] = ids.split(",");
//        wareHouseService.removeByIds ( Lists.newArrayList ( idArray ) );
//		return ResponseEntity.ok( "删除仓库基础信息成功" );
		String idArray[] = ids.split(",");
		for(String id: idArray){
			wareHouseService.removeById ( id );
		}
		return ResponseEntity.ok( "禁用仓库基础信息" );
	}

	/**
	 * 启用仓库基础信息
	 */
	@ApiLog("启用仓库基础信息")
	@ApiOperation(value = "启用仓库基础信息")
	@PreAuthorize("hasAuthority('basic:warehouse:wareHouse:enable')")
	@PostMapping("enable")
	public ResponseEntity<String> enable(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			wareHouseService.enableById ( id );
		}
		return ResponseEntity.ok( "启用仓库基础信息" );
	}

}
