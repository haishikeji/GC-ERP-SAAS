/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.inventoryStock.service.dto;

import com.px.basic.inventorytype.service.dto.InventoryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 存货库存详情表Entity
 * @author 品讯科技
 * @version 2022-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WarehouseStockDetailDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * 存货
	 */
	private InventoryDTO inventory;
	/**
	 * 存货类型
	 */
	private String inventoryType;
	/**
	 * 在库库存
	 */
	private String inventoryInStock;
	/**
	 * 可用库存
	 */
	private String availableStock;
	/**
	 * 锁定库存
	 */
	private String lockStock;
	/**
	 * 库存总价
	 */
	private String totalValue;
}
