/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.dailyproduction.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.produce.dailyproduction.service.dto.DailyproductionDetailsDTO;
import com.px.produce.dailyproduction.domain.DailyproductionDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  DailyproductionDetailsWrapper
 * @author 品讯科技
 * @version 2023-11-07
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface DailyproductionDetailsWrapper extends EntityWrapper<DailyproductionDetailsDTO, DailyproductionDetails> {

    DailyproductionDetailsWrapper INSTANCE = Mappers.getMapper(DailyproductionDetailsWrapper.class);
     @Mappings({
            @Mapping(source = "daily.id", target = "dailyId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    DailyproductionDetails toEntity(DailyproductionDetailsDTO dto);


    @Mappings({
            @Mapping(source = "dailyId", target = "daily.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    DailyproductionDetailsDTO toDTO(DailyproductionDetails entity);
}

