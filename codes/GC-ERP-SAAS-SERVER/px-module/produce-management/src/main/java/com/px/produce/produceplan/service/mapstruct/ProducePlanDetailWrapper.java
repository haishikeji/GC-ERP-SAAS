/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.produceplan.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.produce.produceplan.service.dto.ProducePlanDetailDTO;
import com.px.produce.produceplan.domain.ProducePlanDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * ProducePlanDetailWrapper
 *
 * @author 品讯科技
 * @version 2022-06-07
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface ProducePlanDetailWrapper extends EntityWrapper<ProducePlanDetailDTO, ProducePlanDetail> {

    ProducePlanDetailWrapper INSTANCE = Mappers.getMapper(ProducePlanDetailWrapper.class);

    @Mappings({
            @Mapping(source = "producePlan.id", target = "producePlanId"),
            @Mapping(source = "inventory.id", target = "inventoryId"),
            @Mapping(source = "defaultWareHouse.id", target = "defaultWarehouseId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping(source = "updateBy.id", target = "updateBy")})
    ProducePlanDetail toEntity(ProducePlanDetailDTO dto);


    @Mappings({
            @Mapping(source = "producePlanId", target = "producePlan.id"),
            @Mapping(source = "inventoryId", target = "inventory.id"),
            @Mapping(source = "defaultWarehouseId", target = "defaultWareHouse.id"),
            @Mapping(source = "createBy", target = "createBy.id"),
            @Mapping(source = "updateBy", target = "updateBy.id")})
    ProducePlanDetailDTO toDTO(ProducePlanDetail entity);
}

