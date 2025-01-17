/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.goodsreturn.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.wh.goodsreturn.domain.CpReturn;
import com.px.wh.goodsreturn.service.dto.CpReturnDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  CpReturnWrapper
 * @author 品讯科技
 * @version 2022-06-08
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface CpReturnWrapper extends EntityWrapper<CpReturnDTO, CpReturn> {

    CpReturnWrapper INSTANCE = Mappers.getMapper(CpReturnWrapper.class);
     @Mappings({
            @Mapping(source = "inventory.id", target = "inventoryId"),
            @Mapping(source = "salesorder.id", target = "salesorderId"),
            @Mapping(source = "salesorderDetail.id", target = "salesorderDetailId"),
            @Mapping(source = "customer.id", target = "customerId"),
            @Mapping(source = "returnUser.id", target = "returnUserId"),
            @Mapping(source = "returnWarehouse.id", target = "returnWarehouseId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    CpReturn toEntity(CpReturnDTO dto);


    @Mappings({
            @Mapping(source = "inventoryId", target = "inventory.id"),
            @Mapping(source = "salesorderId", target = "salesorder.id"),
            @Mapping(source = "salesorderDetailId", target = "salesorderDetail.id"),
            @Mapping(source = "customerId", target = "customer.id"),
            @Mapping(source = "returnUserId", target = "returnUser.id"),
            @Mapping(source = "returnWarehouseId", target = "returnWarehouse.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    CpReturnDTO toDTO(CpReturn entity);
}

