/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.one.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.test.one.service.dto.TestFormLeaveDTO;
import com.px.test.one.domain.TestFormLeave;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestFormLeaveWrapper
 * @author 品讯科技
 * @version 2022-04-12
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestFormLeaveWrapper extends EntityWrapper<TestFormLeaveDTO, TestFormLeave> {

    TestFormLeaveWrapper INSTANCE = Mappers.getMapper(TestFormLeaveWrapper.class);
     @Mappings({
            @Mapping(source = "company.id", target = "companyId"),
            @Mapping(source = "office.id", target = "officeId"),
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "area.id", target = "areaId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    TestFormLeave toEntity(TestFormLeaveDTO dto);


    @Mappings({
            @Mapping(source = "companyId", target = "company.id"),
            @Mapping(source = "officeId", target = "office.id"),
            @Mapping(source = "userId", target = "user.id"),
            @Mapping(source = "areaId", target = "area.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    TestFormLeaveDTO toDTO(TestFormLeave entity);
}

