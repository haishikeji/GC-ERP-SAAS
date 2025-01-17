/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.supplier.inventoryset.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 供应商存货设置Entity
 * @author 品讯科技
 * @version 2022-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_supplier_management")
public class Supplier extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 供应商名称
     */
	private String supplierName;
	/**
     * 联系人
     */
	private String concat;
	/**
     * 电话
     */
	private String phone;
	/**
     * 地址
     */
	private String address;
	/**
	 * 供应商等级
	 */
	private String supplierLevel;

}
