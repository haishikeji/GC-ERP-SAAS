/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.stock.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.px.logging.annotation.ApiLog;
import com.px.basic.stock.domain.BasicsStock1;
import com.px.basic.stock.service.BasicsStockService;
import com.px.basic.stock.service.BasicsStockService1;
import com.px.basic.stock.service.dto.BasicsStockDTO;
import com.px.basic.stock.service.dto.BasicsStockDTO1;
import com.px.basic.stock.service.mapstruct.BasicsStockWrapper;
import com.px.core.query.QueryWrapperGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 存货属性Controller
 * @author 品讯科技
 * @version 2022-05-27
 */
@Api(tags ="存货属性") 
@RestController
@RequestMapping(value = "/basic/stock/basicsStock")
public class BasicsStockController {

	@Autowired
	private BasicsStockService basicsStockService;

	@Autowired
	private BasicsStockService1 basicsStockService1;

	@Autowired
	private BasicsStockWrapper basicsStockWrapper;


	/**
	 * 存货属性列表数据
	 */
	@ApiLog("查询存货属性列表数据")
	@ApiOperation(value = "查询存货属性列表数据")
	@PreAuthorize("hasAuthority('basic:stock:basicsStock:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<BasicsStock1>> list(BasicsStockDTO1 basicsStockDTO1, Page<BasicsStock1> page) throws Exception {
		QueryWrapper queryWrapper = QueryWrapperGenerator.buildQueryCondition (basicsStockDTO1, BasicsStockDTO1.class);
		IPage<BasicsStock1> result = basicsStockService1.page (page, queryWrapper);
		result.getRecords().forEach(basicsStock1 -> {
			basicsStock1.setChildren(basicsStockService1.list(new LambdaQueryWrapper<BasicsStock1>()
					.eq(BasicsStock1::getDelFlag,0)
					.eq(BasicsStock1::getParentId,basicsStock1.getId())));
		});
		return ResponseEntity.ok (result);
	}

	/**
	 * 根据Id获取存货属性数据
	 */
	@ApiLog("根据Id获取存货属性数据")
	@ApiOperation(value = "根据Id获取存货属性数据")
	@PreAuthorize("hasAnyAuthority('basic:stock:basicsStock:view','basic:stock:basicsStock:add','basic:stock:basicsStock:edit')")
	@GetMapping("queryById")
	public ResponseEntity<BasicsStockDTO> queryById(String id) {
		return ResponseEntity.ok ( basicsStockWrapper.toDTO ( basicsStockService.getById ( id ) ) );
	}

	/**
	 * 保存存货属性
	 */
	@ApiLog("保存存货属性")
	@ApiOperation(value = "保存存货属性")
	@PreAuthorize("hasAnyAuthority('basic:stock:basicsStock:add','basic:stock:basicsStock:edit')")
	@PostMapping("save")
	public  ResponseEntity <String> save(@Valid @RequestBody BasicsStockDTO basicsStockDTO) {
		//新增或编辑表单保存
		basicsStockService.saveOrUpdate (basicsStockWrapper.toEntity (basicsStockDTO));
        return ResponseEntity.ok ( "保存存货属性成功" );
	}

	/**
	 * 删除存货属性
	 */
	@ApiLog("删除存货属性")
	@ApiOperation(value = "删除存货属性")
	@PreAuthorize("hasAuthority('basic:stock:basicsStock:del')")
	@DeleteMapping("delete")
	public ResponseEntity <String> delete(String ids) {
		String idArray[] = ids.split(",");
        basicsStockService.removeWithChildrenByIds ( Lists.newArrayList ( idArray ) );
		return ResponseEntity.ok( "删除存货属性成功" );
	}

	/**
     * 获取JSON树形数据。
     * @param extId 排除的ID
     * @return
	*/
	@ApiLog("查询存货属性树表数据")
	@ApiOperation(value = "查询存货属性树表数据")
	@GetMapping("treeData")
	public ResponseEntity <List <BasicsStockDTO> > treeData(String extId) {
		List <BasicsStockDTO> rootTree = basicsStockService.treeData ( extId ).stream ().map ( basicsStockWrapper::toDTO ).collect( Collectors.toList());
		return ResponseEntity.ok ( rootTree );
	}

}
