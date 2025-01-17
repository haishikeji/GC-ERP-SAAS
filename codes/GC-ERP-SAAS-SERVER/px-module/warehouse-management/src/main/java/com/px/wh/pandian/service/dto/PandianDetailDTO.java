/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.pandian.service.dto;

import javax.validation.constraints.NotNull;
import com.px.basic.inventorytype.service.dto.InventoryDTO;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 盘点单详情Entity
 * @author 品讯科技
 * @version 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PandianDetailDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 盘点单id
     */
	//(message="盘点单id不能为空")
	private PandainDTO pandian;
	/**
     * 存货id
     */
	//(message="存货id不能为空")
	private InventoryDTO inventory;
	/**
     * 在库库存
     */
	//(message="在库库存不能为空")
	private String inventoryInStock;
	/**
     * 盘点库存
     */
	//(message="盘点库存不能为空")
	private String pandianStock;

	private String chayiStock;

	/**
     * 备注、差异原因
     */
	private String remark;

}
