/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.px.produce.meeto.service.dto;

import com.px.purchasing.purchasecontract.service.dto.PurchaseContractDTO;
import com.px.purchasing.requisition.service.dto.RequisitionDTO;
import com.px.supplier.inventoryset.service.dto.SupplierDTO;
import java.util.List;
import com.google.common.collect.Lists;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;

/**
 * yingfuEntity
 * @author 品讯科技
 * @version 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SxMeetOrderDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 采购订单编号
     */
    @Query(tableColumn = "a.pur_code", javaField = "purCode", type = QueryType.EQ)
	private String purCode;
	/**
     * 采购合同
     */
	@Nullable
    @Query(tableColumn = "a.pur_contract_id", javaField = "purContract.id", type = QueryType.EQ)
	private PurchaseContractDTO purContract;
	/**
     * 请购单
     */
	private RequisitionDTO purRequisition;
	/**
     * 供应商
     */
	private SupplierDTO purSupplier;
	/**
     * 流程实例id
     */
	private String procInsId;
	/**
     * 审核结果
     */
	private String procInsResult;
	/**
     * 采购订单状态
     */
	private String purState;
	/**
     * 合计含税金额
     */
	private String totalpriceIncludingtax;
	/**
     * 总数量
     */
	private String totalNumber;
    /**
     *子表列表
     */
	private List<SxMeetOrderDetailDTO> sxMeetOrderDetailDTOList = Lists.newArrayList();

}
