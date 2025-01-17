/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.meeto.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 应付订单详情Entity
 * @author 品讯科技
 * @version 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_meet_order_detail")
public class SxMeetOrderDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 采购订单id
     */
	private String purOrderId;
	/**
     * 供应商id
     */
    @TableField("pur_supplier_id")
	private String purSupplier;
	/**
     * 存货
     */
	private String inventoryId;
	/**
     * 采购数量
     */
	private String purOrderNum;
	/**
     * 已入库数量
     */
	private String purUsedNum;
	/**
     * 未入库数量
     */
	private String purUnusedNum;
	/**
     * 供货时间
     */
	private Date supplyDate;
	/**
     * 单价
     */
	private String unitPrice;
	/**
     * 总价
     */
	private String totalPrice;
	/**
     * 订单属性
     */
	private String orderAttribute;
	/**
     * 已入庫
     */
	private String received;
	/**
     * 应付款金额
     */
	private String payableAmount;
	/**
     * 剩余付款金额
     */
	private String remainingAmount;

}
