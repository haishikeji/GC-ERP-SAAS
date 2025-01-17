/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.grid.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.test.grid.domain.TestContinent;
import com.px.test.grid.mapper.TestContinentMapper;

/**
 * 洲Service
 * @author 品讯科技
 * @version 2022-03-18
 */
@Service
@Transactional
public class TestContinentService extends ServiceImpl<TestContinentMapper, TestContinent> {

}
