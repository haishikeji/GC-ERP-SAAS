/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.treetable.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 车辆Entity
 * @author 品讯科技
 * @version 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_car")
public class Car extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 品牌
     */
	private String name;
	/**
     * 车系
     */
	private String kindId;
	/**
     * 简介
     */
	private String remarks;

}
