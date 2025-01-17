/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.onetomany.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.test.onetomany.service.dto.TestDataMainFormDTO;
import com.px.test.onetomany.domain.TestDataMainForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestDataMainFormWrapper
 * @author 品讯科技
 * @version 2022-03-20
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestDataMainFormWrapper extends EntityWrapper<TestDataMainFormDTO, TestDataMainForm> {

    TestDataMainFormWrapper INSTANCE = Mappers.getMapper(TestDataMainFormWrapper.class);
     @Mappings({
            @Mapping(source = "tuser.id", target = "tuserId"),
            @Mapping(source = "office.id", target = "officeId"),
            @Mapping(source = "area.id", target = "areaId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    TestDataMainForm toEntity(TestDataMainFormDTO dto);


    @Mappings({
            @Mapping(source = "tuserId", target = "tuser.id"),
            @Mapping(source = "officeId", target = "office.id"),
            @Mapping(source = "areaId", target = "area.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    TestDataMainFormDTO toDTO(TestDataMainForm entity);
}

