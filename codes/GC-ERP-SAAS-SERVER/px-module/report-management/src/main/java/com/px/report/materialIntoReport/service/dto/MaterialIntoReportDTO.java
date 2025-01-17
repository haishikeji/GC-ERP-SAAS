/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.px.report.materialIntoReport.service.dto;

import com.px.basic.inventorytype.service.dto.InventoryDTO;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 物料入库详情Entity
 * @author 品讯科技
 * @version 2022-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialIntoReportDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * 存货
	 */
	private InventoryDTO inventory;
	/**
	 * 存货类型ID
	 */
	@Query(tableColumn = "b.inventory_type_id", javaField = "inventoryTypeId", type = QueryType.EQ)
	private String inventoryTypeId;
	/**
	 * 存货类型
	 */
	private String inventoryType;
	/**
	 * 入库仓库ID
	 */
	@Query(tableColumn = "a.material_into_house", javaField = "materialIntoHouseId", type = QueryType.EQ)
	private String materialIntoHouseId;
	/**
	 * 存货合计入库数量
	 */
	private String inventoryIntoNum;
	/**
	 * 入库开始时间
	 */
	@Query(tableColumn = "a.create_date", javaField = "materialIntoBegin", type = QueryType.GE)
	private Date materialIntoBegin;
	/**
	 * 入库结束时间
	 */
	@Query(tableColumn = "a.create_date", javaField = "materialIntoEnd", type = QueryType.LE)
	private Date materialIntoEnd;
}
