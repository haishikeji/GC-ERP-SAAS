/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.px.wh.materialissue.service.dto;

import com.px.produce.materialplan.service.dto.MaterialPlanDTO;
import java.util.List;
import com.google.common.collect.Lists;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 物料出库Entity
 * @author 品讯科技
 * @version 2022-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialIssueDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 物料出库单编号
     */
	private String materialIssueCode;
	/**
     * 物料计划单
     */
	private MaterialPlanDTO MaterialPlan;
	/**
     * 物料出库状态
     */
	private String materialIssueState;
	/**
     * 物料出库类型
     */
	private String materialIssueType;
	/**
     * 流程id
     */
	private String procInsId;
	/**
     * 审核结果
     */
	private String procInsResult;
    /**
     *子表列表
     */
	private List<MaterialIssueDetailDTO> materialIssueDetailDTOList = Lists.newArrayList();

}
