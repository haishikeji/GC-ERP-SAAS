/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.shop.service.mapstruct;


import com.px.core.mapstruct.TreeWrapper;
import com.px.test.shop.service.dto.CategoryDTO;
import com.px.test.shop.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  CategoryWrapper
 * @author 品讯科技
 * @version 2022-03-18
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface CategoryWrapper extends TreeWrapper<CategoryDTO, Category> {

    CategoryWrapper INSTANCE = Mappers.getMapper(CategoryWrapper.class);
     @Mappings({
            @Mapping(source = "parent.id", target = "parentId"),
            @Mapping(source = "createBy.id", target = "createBy"),
            @Mapping (source = "updateBy.id", target = "updateBy")})
    Category toEntity(CategoryDTO dto);


    @Mappings({
            @Mapping(source = "parentId", target = "parent.id"),
            @Mapping (source = "createBy", target = "createBy.id"),
            @Mapping (source = "updateBy", target = "updateBy.id")})
    CategoryDTO toDTO(Category entity);
}

