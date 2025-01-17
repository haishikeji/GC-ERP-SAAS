/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.logistics.customall.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 物流公司管理Entity
 * @author 品讯科技
 * @version 2023-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_logistics_custom")
public class SxLogisticsCustom extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 物流公司名称
     */
	private String customName;
	/**
     * 电话
     */
	private String customCall;
	/**
     * 邮编
     */
	private String zip;
	/**
     * 地址
     */
	private String address;
	/**
     * 联系人
     */
	private String contactsName;
	/**
     * 联系人手机
     */
	private String contactsPhone;
	/**
     * 车牌
     */
	private String license;
	/**
     * 车型
     */
	private String contactsVehicle;
	/**
     * 车厢长度
     */
	private String contactsLong;
	/**
     * 车厢宽度
     */
	private String contactsWide;
	/**
     * 载物高度
     */
	private String contactsHeight;
	/**
     * 驾驶证
     */
	private String contactsDriver;
	/**
     * 行驶证
     */
	private String contactsDriving;
	/**
     * 营运证
     */
	private String contactsTrading;
	/**
     * 保险证
     */
	private String contactsCertificate;
	/**
     * 车架号
     */
	private String contactsVin;
	/**
     * 发动机号
     */
	private String contactsEngine;

}
