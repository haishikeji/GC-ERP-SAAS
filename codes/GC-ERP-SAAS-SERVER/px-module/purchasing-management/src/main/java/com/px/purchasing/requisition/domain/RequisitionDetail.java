/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.purchasing.requisition.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 请购单存货详情Entity
 * @author 品讯科技
 * @version 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_pur_requisition_detail")
public class RequisitionDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 请购id
     */
	private String requisitionId;
	/**
     * 存货
     */
	private String inventoryId;
	/**
     * 存货数量
     */
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
