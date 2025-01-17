/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.dailyproduction.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 生产日报详情Entity
 * @author 品讯科技
 * @version 2023-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_produce_daily_production_details")
public class DailyproductionDetails extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 日报ID
     */
	private String dailyId;
	/**
     * 产品ID
     */
	private String inventoryId;
	/**
	 * 产品ID
	 */
	private String inventoryName;
	/**
     * 完成数量
     */
	private String completedQuantity;
	/**
     * 良品数量
     */
	private String goodProducts;
	/**
     * 不良数量
     */
	private String noProducts;
	/**
     * 良品率
     */
	private String yield;

}
