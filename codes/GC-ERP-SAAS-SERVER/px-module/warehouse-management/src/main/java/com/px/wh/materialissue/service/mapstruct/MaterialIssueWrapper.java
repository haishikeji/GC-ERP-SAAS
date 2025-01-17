/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.wh.materialissue.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.wh.materialissue.service.dto.MaterialIssueDTO;
import com.px.wh.materialissue.domain.MaterialIssue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  MaterialIssueWrapper
 * @author 品讯科技
 * @version 2022-06-17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface MaterialIssueWrapper extends EntityWrapper<MaterialIssueDTO, MaterialIssue> {

    MaterialIssueWrapper INSTANCE = Mappers.getMapper(MaterialIssueWrapper.class);
     @Mappings({
            @Mapping(source = "materialPlan.id", target = "materialPlanId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    MaterialIssue toEntity(MaterialIssueDTO dto);


    @Mappings({
            @Mapping(source = "materialPlanId", target = "materialPlan.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    MaterialIssueDTO toDTO(MaterialIssue entity);
}

