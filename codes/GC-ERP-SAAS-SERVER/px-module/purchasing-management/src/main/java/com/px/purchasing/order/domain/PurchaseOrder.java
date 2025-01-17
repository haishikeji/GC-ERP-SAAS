/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.purchasing.order.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 采购订单管理Entity
 * @author 品讯科技
 * @version 2022-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_pur_order")
public class PurchaseOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 采购订单编号
     */
	private String purCode;
	/**
     * 采购合同
     */
	private String purContractId;
	/**
     * 请购单
     */
	private String purRequisitionId;
	/**
	 * 供应商
	 */
	private String purSupplierId;
	/**
	 * 供货商名称
	 */
	@TableField(exist = false)
	private String purSupplierName;
	/**
     * 流程实例id
     */
	private String procInsId;
	/**
     * 审核结果
     */
	private String procInsResult;
	/**
     * 采购订单状态
     */
	private String purState;

	/**
	 * 合计含税价格
	 */
	private String totalpriceIncludingtax;
	/**
	 * 总数量
	 */
	private String totalNumber;
}
