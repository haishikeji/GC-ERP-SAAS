/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.sales.salesorder.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.sales.salesorder.service.dto.SalesorderdetailsDTO;
import com.px.sales.salesorder.domain.Salesorderdetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  SalesorderdetailsWrapper
 * @author 品讯科技
 * @version 2022-05-30
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface SalesorderdetailsWrapper extends EntityWrapper<SalesorderdetailsDTO, Salesorderdetails> {

    SalesorderdetailsWrapper INSTANCE = Mappers.getMapper(SalesorderdetailsWrapper.class);
     @Mappings({
            @Mapping(source = "salesorder.id", target = "salesorderId"),
            @Mapping(source = "inventory.id", target = "inventoryId"),
            @Mapping(source = "defaultWareHouse.id", target = "defaultWarehouseId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    Salesorderdetails toEntity(SalesorderdetailsDTO dto);


    @Mappings({
            @Mapping(source = "salesorderId", target = "salesorder.id"),
            @Mapping(source = "inventoryId", target = "inventory.id"),
            @Mapping(source = "defaultWarehouseId", target = "defaultWareHouse.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    SalesorderdetailsDTO toDTO(Salesorderdetails entity);
}

