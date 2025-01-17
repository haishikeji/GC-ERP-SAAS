/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.customer.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.basic.customer.service.dto.CustomerDTO;
import com.px.basic.customer.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  CustomerWrapper
 * @author 品讯科技
 * @version 2022-05-27
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface CustomerWrapper extends EntityWrapper<CustomerDTO, Customer> {

    CustomerWrapper INSTANCE = Mappers.getMapper(CustomerWrapper.class);
     @Mappings({
            @Mapping(source = "orderAttr.id", target = "orderAttrId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    Customer toEntity(CustomerDTO dto);


    @Mappings({
            @Mapping(source = "orderAttrId", target = "orderAttr.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    CustomerDTO toDTO(Customer entity);
}

