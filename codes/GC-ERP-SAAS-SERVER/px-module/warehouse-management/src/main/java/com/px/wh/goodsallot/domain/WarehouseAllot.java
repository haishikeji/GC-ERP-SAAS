/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.goodsallot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 仓库调拨Entity
 * @author 品讯科技
 * @version 2022-06-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_ck_allot")
public class WarehouseAllot extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 调出仓库
     */
	private String outWarehouseId;
	/**
     * 入库仓库
     */
	private String inWarehouseId;
	/**
     * 备注
     */
	private String remark;
	/**
     * 审核状态 0待审核 1审核通过 2审核不通过
     */
	private String aduitStatus;
	/**
     * 审核人
     */
    @TableField("aduit_user")
	private String aduitUserId;
	/**
     * 审核时间
     */
	private String aduitDate;
	/**
     * 审核备注
     */
	private String aduitRemark;
	/**
	 * 流程实例id
	 */
	private String procInsId;
	/**
	 * 审核结果
	 */
	private String procInsResult;
	/*类型：1物料 2产品*/
	private Integer type;

}
