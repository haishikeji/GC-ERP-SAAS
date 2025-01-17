/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.workshop.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 生产车间Entity
 * @author 品讯科技
 * @version 2022-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_bas_workshop")
public class WorkShop extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 车间编码
     */
	private String code;
	/**
     * 车间名称
     */
	private String name;
	/**
     * 车间负责人
     */
	private String chargeId;
	/**
     * 车间地址
     */
	private String address;

}
