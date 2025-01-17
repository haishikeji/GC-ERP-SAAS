/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.sales.salesorder.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.sales.salesorder.service.dto.SalesorderDTO;
import com.px.sales.salesorder.domain.Salesorder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  SalesorderWrapper
 * @author 品讯科技
 * @version 2022-05-30
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface SalesorderWrapper extends EntityWrapper<SalesorderDTO, Salesorder> {

    SalesorderWrapper INSTANCE = Mappers.getMapper(SalesorderWrapper.class);
     @Mappings({
            @Mapping(source = "salescontract.id", target = "salescontractId"),
            @Mapping(source = "customer.id", target = "customerId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    Salesorder toEntity(SalesorderDTO dto);


    @Mappings({
            @Mapping(source = "salescontractId", target = "salescontract.id"),
            @Mapping(source = "customerId", target = "customer.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    SalesorderDTO toDTO(Salesorder entity);
}

