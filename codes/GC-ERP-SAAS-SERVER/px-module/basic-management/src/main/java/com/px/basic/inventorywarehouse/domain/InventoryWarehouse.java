/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.inventorywarehouse.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 存货仓库中间表Entity
 * @author 品讯科技
 * @version 2022-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_bas_inventory_warehouse")
public class InventoryWarehouse extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 存货id
     */
	private String inventoryId;
	/**
     * 仓库id
     */
	private String warehouseId;
	/**
     * 最小库存
     */
	private String minimumInventory;
	/**
     * 最大库存
     */
	private String maximumInventory;
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

}
