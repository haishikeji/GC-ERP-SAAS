/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.sales.customer.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.sales.customer.service.dto.CustomerServerDTO;
import com.px.sales.customer.domain.CustomerServer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  CustomerServerWrapper
 * @author 品讯科技
 * @version 2023-04-25
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface CustomerServerWrapper extends EntityWrapper<CustomerServerDTO, CustomerServer> {

    CustomerServerWrapper INSTANCE = Mappers.getMapper(CustomerServerWrapper.class);
}

