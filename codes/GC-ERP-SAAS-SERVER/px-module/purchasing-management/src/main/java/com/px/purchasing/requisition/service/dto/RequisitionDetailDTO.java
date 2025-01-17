/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.purchasing.requisition.service.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.px.basic.inventorytype.service.dto.InventoryDTO;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
/**
 * 请购单存货详情Entity
 * @author 品讯科技
 * @version 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RequisitionDetailDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 请购id
     */
	private RequisitionDTO requisition;
	/**
     * 存货
     */
	
	private InventoryDTO inventory;
	/**
     * 存货数量
     */
	//(message="存货数量不能为空")
	private String inventoryNum;
	/**
     * 存货备注
     */
	private String inventoryRemark;

	/**
	 * 未税单价
	 */
	@TableField(exist = false)
	private String notaxPrice;
	/**
	 * 含税单价
	 */
	@TableField(exist = false)
	private String taxincludedPrice;
}
