/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.basic.stock.service.mapstruct;


import com.px.core.mapstruct.TreeWrapper;
import com.px.basic.stock.service.dto.BasicsStockDTO;
import com.px.basic.stock.domain.BasicsStock;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  BasicsStockWrapper
 * @author 品讯科技
 * @version 2022-05-27
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface BasicsStockWrapper extends TreeWrapper<BasicsStockDTO, BasicsStock> {

    BasicsStockWrapper INSTANCE = Mappers.getMapper(BasicsStockWrapper.class);
}

