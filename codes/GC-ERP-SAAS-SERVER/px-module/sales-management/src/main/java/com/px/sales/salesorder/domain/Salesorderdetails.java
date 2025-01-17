/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.sales.salesorder.domain;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 销售订单详情Entity
 * @author 品讯科技
 * @version 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_sale_salesorderdetails")
public class Salesorderdetails extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 销售订单Id
     */
	private String salesorderId;
	/**
     * 存货
     */
	private String inventoryId;
	/**
	 * 仓库id
	 */
	private String defaultWarehouseId;
	/**
     * 订单数量
     */
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
     * 含税单价
     */
	private String unitPrice;
	/**
     * 含税总价
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
	private Date deliveryDate;
	/**
     * 订单属性
     */
	private String orderAttribute;
	/**
	 * 货物批号
	 */
	private String salesorderBatchnumber;

}
