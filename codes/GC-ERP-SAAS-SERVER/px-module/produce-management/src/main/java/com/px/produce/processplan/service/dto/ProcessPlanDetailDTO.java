/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.processplan.service.dto;

import com.px.basic.inventorytype.service.dto.InventoryDTO;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
/**
 * 工序计划详情Entity
 * @author 品讯科技
 * @version 2022-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProcessPlanDetailDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 工序计划id
     */
	private ProcessPlanDTO process;
	/**
     * 存货
     */
	
	private InventoryDTO inventory;
	/**
     * 计划生产数量
     */
	//(message="计划生产数量不能为空")
	private String planProduceNum;
	/**
     * 实际生产数量
     */
	private String realityProduceNum;
	/**
     * 生产备注
     */
	private String produceRemark;

}
