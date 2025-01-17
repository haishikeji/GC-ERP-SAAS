/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.purchasing.purchasecontract.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.purchasing.purchasecontract.service.dto.PurchaseContractDTO;
import com.px.purchasing.purchasecontract.domain.PurchaseContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  PurchaseContractWrapper
 * @author 品讯科技
 * @version 2022-05-30
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface PurchaseContractWrapper extends EntityWrapper<PurchaseContractDTO, PurchaseContract> {

    PurchaseContractWrapper INSTANCE = Mappers.getMapper(PurchaseContractWrapper.class);
     @Mappings({
            @Mapping(source = "supplier.id", target = "supplierId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    PurchaseContract toEntity(PurchaseContractDTO dto);


    @Mappings({
            @Mapping(source = "supplierId", target = "supplier.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    PurchaseContractDTO toDTO(PurchaseContract entity);
}

