/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.materialissue.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.px.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 物料出库详情表Entity
 * @author 品讯科技
 * @version 2022-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sx_produce_material_issue_detail")
public class MaterialIssueDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
     * 物料出库id
     */
	private String materialIssueId;
	/**
     * 存货
     */
	private String inventoryId;
	/**
     * 出库数量
     */
	private String materialOutNum;
	/**
	 * 数量差额
	 */
	private String slceNum;
	/**
     * 出库仓库
     */
    @TableField("material_out_house")
	private String materialOutHouseId;

}
