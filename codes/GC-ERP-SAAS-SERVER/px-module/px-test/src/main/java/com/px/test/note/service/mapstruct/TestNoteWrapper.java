/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.note.service.mapstruct;


import com.px.core.mapstruct.EntityWrapper;
import com.px.test.note.service.dto.TestNoteDTO;
import com.px.test.note.domain.TestNote;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *  TestNoteWrapper
 * @author 品讯科技
 * @version 2022-03-18
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {} )
public interface TestNoteWrapper extends EntityWrapper<TestNoteDTO, TestNote> {

    TestNoteWrapper INSTANCE = Mappers.getMapper(TestNoteWrapper.class);
}

