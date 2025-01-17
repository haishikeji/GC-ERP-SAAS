/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.processplan.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.produce.processplan.service.dto.ProcessPlanDTO;
import com.px.produce.processplan.domain.ProcessPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  ProcessPlanWrapper
 * @author 品讯科技
 * @version 2022-06-14
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface ProcessPlanWrapper extends EntityWrapper<ProcessPlanDTO, ProcessPlan> {

    ProcessPlanWrapper INSTANCE = Mappers.getMapper(ProcessPlanWrapper.class);
     @Mappings({
            @Mapping(source = "processProduce.id", target = "processProduceId"),
            @Mapping(source = "processAdmin.id", target = "processAdminId"),
            @Mapping(source = "processUser.id", target = "processUserId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    ProcessPlan toEntity(ProcessPlanDTO dto);


    @Mappings({
            @Mapping(source = "processProduceId", target = "processProduce.id"),
            @Mapping(source = "processAdminId", target = "processAdmin.id"),
            @Mapping(source = "processUserId", target = "processUser.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    ProcessPlanDTO toDTO(ProcessPlan entity);
}

