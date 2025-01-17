/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.px.purchasing.requisition.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import com.px.sys.service.dto.OfficeDTO;
import com.px.sys.service.dto.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
/**
 * 请购单Entity
 * @author 品讯科技
 * @version 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RequisitionDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 请购单号
     */
	//(message="请购单号不能为空")
    @Query(tableColumn = "a.requisition_number", javaField = "requisitionNumber", type = QueryType.LIKE)
	private String requisitionNumber;
	/**
     * 请购人
     */
	//(message="请购人不能为空")
    @Query(tableColumn = "a.requisition_user", javaField = "requisitionUser.id", type = QueryType.EQ)
	private UserDTO requisitionUser;
	/**
     * 请购部门
     */
	//(message="请购部门不能为空")
    @Query(tableColumn = "a.requisition_dep", javaField = "requisitionDep.id", type = QueryType.EQ)
	private OfficeDTO requisitionDep;
	/**
     * 请购时间
     */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//(message="请购时间不能为空")
	private Date requisitionData;
	/**
	 * 开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Query(tableColumn = "a.requisition_data", javaField = "requisitionBegin", type = QueryType.GE)
	private Date requisitionBegin;
	/**
	 * 结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Query(tableColumn = "a.requisition_data", javaField = "requisitionEnd", type = QueryType.LE)
	private Date requisitionEnd;
	/**
     * 请购单状态
     */
    @Query(tableColumn = "a.requisition_state", javaField = "requisitionState", type = QueryType.EQ)
	private String requisitionState;
	/**
     * 流程实例ID
     */
	private String procInsId;
	/**
     * 流程结果
     */
	private String procInsResult;
    /**
     *子表列表
     */
	private List<RequisitionDetailDTO> requisitionDetailDTOList = Lists.newArrayList();

}
