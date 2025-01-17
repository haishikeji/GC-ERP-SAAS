/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.materialissue.service.dto;

import com.px.basic.inventorytype.service.dto.InventoryDTO;
import com.px.basic.warehouse.service.dto.WareHouseDTO;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 物料出库详情表Entity
 * @author 品讯科技
 * @version 2022-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialIssueDetailDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 物料出库id
     */
	private MaterialIssueDTO materialIssue;
	/**
     * 存货
     */
	private InventoryDTO inventory;
	/**
     * 出库数量
     */
	private String materialOutNum;
	/**
	 * 数量差额
	 */
	private String slceNum;
	/**
     * 出库仓库
     */
	private WareHouseDTO materialOutHouse;

}
