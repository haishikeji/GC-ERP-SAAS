/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.suppliermanagement.controller;

import javax.validation.Valid;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import com.px.demo.annotation.DemoMode;
import com.px.core.query.QueryWrapperGenerator;
import com.px.logging.annotation.ApiLog;
import com.px.basic.suppliermanagement.domain.SupplierManagement;
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
import com.px.basic.suppliermanagement.service.dto.SupplierManagementDTO;
import com.px.basic.suppliermanagement.service.mapstruct.SupplierManagementWrapper;
import com.px.basic.suppliermanagement.service.SupplierManagementService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 供应商管理Controller
 * @author 品讯科技
 * @version 2022-05-26
 */

@Api(tags ="供应商管理")
@RestController
@RequestMapping(value = "/basic/suppliermanagement/supplierManagement")
public class SupplierManagementController {

	@Autowired
	private SupplierManagementService supplierManagementService;

	@Autowired
	private SupplierManagementWrapper supplierManagementWrapper;


	/**
	 * 供应商管理列表数据
	 */
	@ApiLog("查询供应商管理列表数据")
	@ApiOperation(value = "查询供应商管理列表数据")
	@PreAuthorize("hasAuthority('basic:suppliermanagement:supplierManagement:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<SupplierManagementDTO>> list(SupplierManagementDTO supplierManagementDTO, Page<SupplierManagementDTO> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition(supplierManagementDTO, SupplierManagementDTO.class);
		IPage<SupplierManagementDTO> result = supplierManagementService.findPage(page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 根据Id获取供应商管理数据
	 */
	@ApiLog("根据Id获取供应商管理数据")
	@ApiOperation(value = "根据Id获取供应商管理数据")
	@PreAuthorize("hasAnyAuthority('basic:suppliermanagement:supplierManagement:view','basic:suppliermanagement:supplierManagement:add','basic:suppliermanagement:supplierManagement:edit')")
	@GetMapping("queryById")
	public ResponseEntity<SupplierManagementDTO> queryById(String id) {
		return ResponseEntity.ok(supplierManagementWrapper.toDTO(supplierManagementService.getById(id)));
	}

	/**
	 * 保存供应商管理
	 */
	@ApiLog("保存供应商管理")
	@ApiOperation(value = "保存供应商管理")
	@PreAuthorize("hasAnyAuthority('basic:suppliermanagement:supplierManagement:add','basic:suppliermanagement:supplierManagement:edit')")
	@PostMapping("save")
	public ResponseEntity<String> save(@Valid @RequestBody SupplierManagementDTO supplierManagementDTO) {
		int row = supplierManagementService.lambdaQuery ().eq(SupplierManagement::getSupplierName, supplierManagementDTO.getSupplierName()).ne(SupplierManagement::getId, supplierManagementDTO.getId()).count ();
		if (row >0){
			return new ResponseEntity("供应商-"+supplierManagementDTO.getSupplierName()+"-已存在！", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//新增或编辑表单保存
		supplierManagementService.saveOrUpdate(supplierManagementWrapper.toEntity(supplierManagementDTO));
		return ResponseEntity.ok("保存供应商管理成功");
	}

	/**
	 * 删除供应商管理
	 */
	@DemoMode
	@ApiLog("删除供应商管理")
	@ApiOperation(value = "删除供应商管理")
	@PreAuthorize("hasAuthority('basic:suppliermanagement:supplierManagement:zdel')")
	@DeleteMapping("zdelete")
	public ResponseEntity zdelete(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			supplierManagementService.deleteById ( id );
		}
		return ResponseEntity.ok( "删除供应商管理成功" );
	}


	/**
	 * 删除供应商管理
	 */
	@ApiLog("禁用供应商管理")
	@ApiOperation(value = "禁用供应商管理")
	@PreAuthorize("hasAuthority('basic:suppliermanagement:supplierManagement:del')")
	@DeleteMapping("delete")
	public ResponseEntity<String> delete(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			supplierManagementService.removeById ( id );
		}
		return ResponseEntity.ok( "删除供应商管理成功" );
	}

	/**
	 * 启用供应商管理
	 */
	@ApiLog("启用供应商管理")
	@ApiOperation(value = "启用供应商管理")
	@PreAuthorize("hasAuthority('basic:suppliermanagement:supplierManagement:enable')")
	@PostMapping("enable")
	public ResponseEntity<String> enable(String ids) {
		String idArray[] = ids.split(",");
		for(String id: idArray){
			supplierManagementService.enableById ( id );
		}
		return ResponseEntity.ok( "启用供应商管理成功" );
	}


	/**
	 * 导入供应商数据
	 *
	 * @return
	 */
	@DemoMode
	@PreAuthorize("hasAnyAuthority('basic:suppliermanagement:supplierManagement:import')")
	@PostMapping("import")
	@ApiOperation(value = "导入供应商excel")
	public ResponseEntity importFile(MultipartFile file) throws Exception{
/*
			List <SupplierManagementDTO> list = EasyPoiUtil.importExcel ( file, 1, 1, SupplierManagementDTO.class );
			for (SupplierManagementDTO supplierManagementDTO : list) {
				supplierManagementService.save(supplierManagementWrapper.INSTANCE.toEntity(supplierManagementDTO));
			}
			return ResponseEntity.ok ( "已成功导入 " );*/

		ImportParams params = new ImportParams();
		params.setStartSheetIndex(0);
		List<SupplierManagementDTO> list = new ExcelImportService().importExcelByIs(file.getInputStream(), SupplierManagementDTO.class, params, false).getList();
		if (list.size() > 0) {
			for (SupplierManagementDTO supplierManagementDTO : list) {
				supplierManagementService.save(supplierManagementWrapper.INSTANCE.toEntity(supplierManagementDTO));
			}
		} else {
			return ResponseEntity.badRequest().body ( "导入失败！失败信息：" );
		}
		return ResponseEntity.ok ( "已成功导入 " );



	}

}