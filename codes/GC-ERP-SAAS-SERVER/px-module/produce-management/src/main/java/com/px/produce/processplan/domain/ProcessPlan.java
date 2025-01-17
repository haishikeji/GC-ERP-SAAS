/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.processplan.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * 工序计划管理Entity
 * @author 品讯科技
 * @version 2022-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_pro_process_plan")
public class ProcessPlan extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 工序计划编号
     */
	private String processCode;
	/**
     * 生产计划单
     */
	private String processProduceId;
	/**
     * 生产工序
     */
    @TableField("process_admin")
	private String processAdminId;
	/**
     * 生产员
     */
    @TableField("process_user")
	private String processUserId;
	/**
     * 生产日期
     */
	private Date processData;
	/**
     * 状态
     */
	private String processState;

}
