/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.manytomany.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.test.manytomany.domain.Course;
import com.px.test.manytomany.mapper.CourseMapper;

/**
 * 课程Service
 * @author 品讯科技
 * @version 2022-03-18
 */
@Service
@Transactional
public class CourseService extends ServiceImpl<CourseMapper, Course> {

}
