/**
        * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
        */
        package com.px.supplier.inventoryset.service.dto;

import java.util.List;
import com.google.common.collect.Lists;
import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 供应商存货设置Entity
 * @author 品讯科技
 * @version 2022-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SupplierDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 供应商名称
     */
	//(message="供应商名称不能为空")
    @Query(type = QueryType.EQ)
	private String supplierName;
	/**
     * 联系人
     */
    @Query(type = QueryType.EQ)
	private String concat;
	/**
     * 电话
     */
    @Query(type = QueryType.EQ)
	private String phone;
	/**
     * 地址
     */
	private String address;
	/**
	 * 供应商等级
	 */
	private String supplierLevel;
    /**
     *子表列表
     */
	private List<SupplierDetailDTO> supplierDetailDTOList = Lists.newArrayList();

}
