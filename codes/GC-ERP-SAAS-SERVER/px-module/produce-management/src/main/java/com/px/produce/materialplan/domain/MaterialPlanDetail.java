/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.materialplan.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 物料计划单详情Entity
 * @author 品讯科技
 * @version 2022-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_material_plan_detail")
public class MaterialPlanDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 物料计划主表id
     */
	private String materialPlanId;
	/**
     * 存货
     */
	private String inventoryId;
	/**
     * 需求数量
     */
	private String needNum;
	/**
     * 生产状态
     */
	private String type;
	/**
     * 缺少数量
     */
	private String lackNum;

}
