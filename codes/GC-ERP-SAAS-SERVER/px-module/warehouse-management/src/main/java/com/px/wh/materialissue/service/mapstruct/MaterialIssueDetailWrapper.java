/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.materialissue.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.wh.materialissue.service.dto.MaterialIssueDetailDTO;
import com.px.wh.materialissue.domain.MaterialIssueDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  MaterialIssueDetailWrapper
 * @author 品讯科技
 * @version 2022-06-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface MaterialIssueDetailWrapper extends EntityWrapper<MaterialIssueDetailDTO, MaterialIssueDetail> {

    MaterialIssueDetailWrapper INSTANCE = Mappers.getMapper(MaterialIssueDetailWrapper.class);
     @Mappings({
            @Mapping(source = "materialIssue.id", target = "materialIssueId"),
            @Mapping(source = "inventory.id", target = "inventoryId"),
            @Mapping(source = "materialOutHouse.id", target = "materialOutHouseId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    MaterialIssueDetail toEntity(MaterialIssueDetailDTO dto);


    @Mappings({
            @Mapping(source = "materialIssueId", target = "materialIssue.id"),
            @Mapping(source = "inventoryId", target = "inventory.id"),
            @Mapping(source = "materialOutHouseId", target = "materialOutHouse.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    MaterialIssueDetailDTO toDTO(MaterialIssueDetail entity);
}

