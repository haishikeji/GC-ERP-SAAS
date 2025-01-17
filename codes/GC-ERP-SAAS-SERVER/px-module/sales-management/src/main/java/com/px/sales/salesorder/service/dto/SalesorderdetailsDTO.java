/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.sales.salesorder.service.dto;

import com.px.basic.inventorytype.service.dto.InventoryDTO;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.px.basic.warehouse.service.dto.WareHouseDTO;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 销售订单详情Entity
 * @author 品讯科技
 * @version 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SalesorderdetailsDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 销售订单Id
     */
	private SalesorderDTO salesorder;
	/**
     * 存货
     */
	
	private InventoryDTO inventory;
	/**
	 * 默认仓库
	 */
	private WareHouseDTO defaultWareHouse;
	/**
     * 订单数量
     */
	//(message="订单数量不能为空")
	private String salesorderNum;
	/**
     * 计划生产数量
     */
	private String planProduceNum;
	/**
	 * 已出库数量
	 */
	private String issuedNum;
	/**
     * 锁定库存
     */
	private String lockInventory;
	/**
     * 单价
     */
	private String unitPrice;
	/**
     * 总价
     */
	private String totalPrice;
	/**
	 * 未税单价
	 */
	private String notaxiUnitPrice;
	/**
	 * 未税总价
	 */
	private String notaxiTotalPrice;
	/**
     * 交期
     */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;
	/**
     * 订单属性
     */
	private String orderAttribute;

	/**
	 * 货物批号
	 */
		private String salesorderBatchnumber;

	/**
	 * 标识删除 1删除
	 */
	private Integer del;

}
