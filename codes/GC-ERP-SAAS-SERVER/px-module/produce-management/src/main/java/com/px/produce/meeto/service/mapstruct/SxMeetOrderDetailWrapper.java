/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.meeto.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.produce.meeto.service.dto.SxMeetOrderDetailDTO;
import com.px.produce.meeto.domain.SxMeetOrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  SxMeetOrderDetailWrapper
 * @author 品讯科技
 * @version 2023-11-10
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface SxMeetOrderDetailWrapper extends EntityWrapper<SxMeetOrderDetailDTO, SxMeetOrderDetail> {

    SxMeetOrderDetailWrapper INSTANCE = Mappers.getMapper(SxMeetOrderDetailWrapper.class);
     @Mappings({
            @Mapping(source = "purOrder.id", target = "purOrderId"),
            @Mapping(source = "inventory.id", target = "inventoryId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    SxMeetOrderDetail toEntity(SxMeetOrderDetailDTO dto);


    @Mappings({
            @Mapping(source = "purOrderId", target = "purOrder.id"),
            @Mapping(source = "inventoryId", target = "inventory.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    SxMeetOrderDetailDTO toDTO(SxMeetOrderDetail entity);
}

