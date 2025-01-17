/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.sales.cusplan.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 计划开发（潜在客户）Entity
 * @author 品讯科技
 * @version 2023-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_customer_plan")
public class CustomerPlan extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     *  机会来源
     */
	private String planSource;
	/**
     * 联系人
     */
	private String planContacts;
	/**
     *  联系电话
     */
	private String contactsCall;
	/**
     * 概要
     */
	private String planSummary;
	/**
     * 成功几率
     */
	private String planSuccess;
	/**
     * 机会描述
     */
	private String planDescribe;
	/**
     * 开发程度（进行中 1  失败 2 成功3）
     */
	private String planState;

}
