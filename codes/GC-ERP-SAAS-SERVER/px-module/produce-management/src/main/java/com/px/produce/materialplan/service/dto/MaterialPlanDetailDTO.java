/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.materialplan.service.dto;

import javax.validation.constraints.NotNull;
import com.px.basic.inventorytype.service.dto.InventoryDTO;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 物料计划单详情Entity
 * @author 品讯科技
 * @version 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialPlanDetailDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 物料计划主表id
     */
	//(message="物料计划主表id不能为空")
	private MaterialPlanDTO materialPlan;
	/**
     * 存货
     */
	
    @Query(tableColumn = "a.inventory_id", javaField = "inventory.id", type = QueryType.LIKE)
	private InventoryDTO inventory;
	/**
     * 需求数量
     */
	private String needNum;
	/**
     * 生产状态
     */
	private String type;
	/**
     * 缺少数量
     */
	private String lackNum;

}
