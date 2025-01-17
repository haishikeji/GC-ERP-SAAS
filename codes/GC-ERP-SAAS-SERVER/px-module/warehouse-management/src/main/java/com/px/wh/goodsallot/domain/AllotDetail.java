/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.goodsallot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 仓库调拨详情Entity
 * @author 品讯科技
 * @version 2022-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_ck_allot_detail")
public class AllotDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 调拨单id
     */
	private String allotId;
	/**
     * 存货id
     */
	private String inventoryId;
	/**
     * 调拨数量
     */
	private String num;

}
