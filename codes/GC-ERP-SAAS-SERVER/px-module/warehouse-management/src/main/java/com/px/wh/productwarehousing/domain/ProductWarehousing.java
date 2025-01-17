/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.productwarehousing.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 产品入库单Entity
 * @author 品讯科技
 * @version 2022-06-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_wh_product_warehousing")
public class ProductWarehousing extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 入库单编号
     */
	private String warehousingOrderNo;
	/**
     * 销售订单
     */
	private String salesorderId;
	/**
	 * 生产计划单
	 */
	private String produceplanId;
	/**
     * 入库单状态
     */
	private String warehousingStatus;
	/**
	 * 入库单类型
	 */
	private String warehousingType;
	/**
     * 流程实例ID
     */
	private String procInsId;
	/**
     * 审核结果
     */
	private String result;

}
