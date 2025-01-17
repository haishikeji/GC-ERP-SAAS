/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.dailytem.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.basic.dailytem.service.dto.DailyTypeDTO;
import com.px.basic.dailytem.domain.DailyType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  DailyTypeWrapper
 * @author 品讯科技
 * @version 2022-06-10
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface DailyTypeWrapper extends EntityWrapper<DailyTypeDTO, DailyType> {

    DailyTypeWrapper INSTANCE = Mappers.getMapper(DailyTypeWrapper.class);
     @Mappings({
            @Mapping(source = "dailyTen.id", target = "dailyTenId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    DailyType toEntity(DailyTypeDTO dto);


    @Mappings({
            @Mapping(source = "dailyTenId", target = "dailyTen.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    DailyTypeDTO toDTO(DailyType entity);
}

