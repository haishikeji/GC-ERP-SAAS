/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.sales.commissioner.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 服务专员Entity
 * @author 品讯科技
 * @version 2023-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_customer_commissioner")
public class Commissioner extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 名称
     */
	private String comName;
	/**
     * 年纪
     */
	private String comAge;
	/**
     * 邮箱
     */
	private String comMailbox;
	/**
     * 手机号
     */
	private String comPhone;

}
