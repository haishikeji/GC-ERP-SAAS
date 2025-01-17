/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.produceplan.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.produce.produceplan.service.dto.ProducePlanDTO;
import com.px.produce.produceplan.domain.ProducePlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  ProducePlanWrapper
 * @author 品讯科技
 * @version 2022-06-07
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface ProducePlanWrapper extends EntityWrapper<ProducePlanDTO, ProducePlan> {

    ProducePlanWrapper INSTANCE = Mappers.getMapper(ProducePlanWrapper.class);
     @Mappings({
            @Mapping(source = "salesorder.id", target = "salesorderId"),
            @Mapping(source = "workshop.id", target = "workshopId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    ProducePlan toEntity(ProducePlanDTO dto);


    @Mappings({
            @Mapping(source = "salesorderId", target = "salesorder.id"),
            @Mapping(source = "workshopId", target = "workshop.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    ProducePlanDTO toDTO(ProducePlan entity);
}

