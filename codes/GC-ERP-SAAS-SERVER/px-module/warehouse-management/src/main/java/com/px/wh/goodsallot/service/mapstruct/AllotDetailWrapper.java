/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.goodsallot.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.wh.goodsallot.service.dto.AllotDetailDTO;
import com.px.wh.goodsallot.domain.AllotDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  AllotDetailWrapper
 * @author 品讯科技
 * @version 2022-06-07
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface AllotDetailWrapper extends EntityWrapper<AllotDetailDTO, AllotDetail> {

    AllotDetailWrapper INSTANCE = Mappers.getMapper(AllotDetailWrapper.class);
     @Mappings({
            @Mapping(source = "allot.id", target = "allotId"),
            @Mapping(source = "inventory.id", target = "inventoryId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    AllotDetail toEntity(AllotDetailDTO dto);


    @Mappings({
            @Mapping(source = "allotId", target = "allot.id"),
            @Mapping(source = "inventoryId", target = "inventory.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    AllotDetailDTO toDTO(AllotDetail entity);
}

