/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.materialinto.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.wh.materialinto.service.dto.MaterialIntoDetailDTO;
import com.px.wh.materialinto.domain.MaterialIntoDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  MaterialIntoDetailWrapper
 * @author 品讯科技
 * @version 2022-06-07
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface MaterialIntoDetailWrapper extends EntityWrapper<MaterialIntoDetailDTO, MaterialIntoDetail> {

    MaterialIntoDetailWrapper INSTANCE = Mappers.getMapper(MaterialIntoDetailWrapper.class);
     @Mappings({
            @Mapping(source = "material.id", target = "materialId"),
            @Mapping(source = "inventory.id", target = "inventoryId"),
            @Mapping(source = "materialIntoHouse.id", target = "materialIntoHouseId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    MaterialIntoDetail toEntity(MaterialIntoDetailDTO dto);


    @Mappings({
            @Mapping(source = "materialId", target = "material.id"),
            @Mapping(source = "inventoryId", target = "inventory.id"),
            @Mapping(source = "materialIntoHouseId", target = "materialIntoHouse.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    MaterialIntoDetailDTO toDTO(MaterialIntoDetail entity);
}

