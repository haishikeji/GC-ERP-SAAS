/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.productdelivery.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 产品出库单Entity
 * @author 品讯科技
 * @version 2022-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_wh_productdelivery")
public class Productdelivery extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 出库单编号
     */
	private String deliveryOrderNo;
	/**
     * 销售订单
     */
	private String salesorderId;

	@TableField(exist = false)
	private String customerId;

	@TableField(exist = false)
	private String receivingId;
	/**
     * 出库类型
     */
	private String issueType;
	/**
	 * 出库单状态
	 */
	private String productdeliveryState;
	/**
     * 流程实例ID
     */
	private String procInsId;
	/**
     * 审核结果
     */
	private String result;

}
