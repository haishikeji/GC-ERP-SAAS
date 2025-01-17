/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.sales.contract.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.sales.contract.service.dto.SalescontractDTO;
import com.px.sales.contract.domain.Salescontract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  SalescontractWrapper
 * @author 品讯科技
 * @version 2022-05-30
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface SalescontractWrapper extends EntityWrapper<SalescontractDTO, Salescontract> {

    SalescontractWrapper INSTANCE = Mappers.getMapper(SalescontractWrapper.class);
     @Mappings({
            @Mapping(source = "customer.id", target = "customerId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    Salescontract toEntity(SalescontractDTO dto);


    @Mappings({
            @Mapping(source = "customerId", target = "customer.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    SalescontractDTO toDTO(Salescontract entity);
}

