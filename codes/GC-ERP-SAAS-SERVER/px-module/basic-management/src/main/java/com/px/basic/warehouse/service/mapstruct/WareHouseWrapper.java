/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.warehouse.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.basic.warehouse.service.dto.WareHouseDTO;
import com.px.basic.warehouse.domain.WareHouse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  WareHouseWrapper
 * @author 品讯科技
 * @version 2022-05-26
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface WareHouseWrapper extends EntityWrapper<WareHouseDTO, WareHouse> {

    WareHouseWrapper INSTANCE = Mappers.getMapper(WareHouseWrapper.class);
}

