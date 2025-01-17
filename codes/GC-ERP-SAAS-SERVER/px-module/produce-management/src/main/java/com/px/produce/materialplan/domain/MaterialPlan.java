/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.materialplan.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 物料计划单Entity
 * @author 品讯科技
 * @version 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_material_plan")
public class MaterialPlan extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 计划单号
     */
	private String materialplanCode;
	/**
     * 销售订单
     */
	private String salesorderId;
	/**
     * 生产计划单
     */
	private String produceplanId;
	/**
     * 流程id
     */
	private String procInsId;
	/**
	 * 物料计划单状态
	 */
	private String materialPlanState;
	/**
	 * 审核结果
	 */
	private String result;

}
