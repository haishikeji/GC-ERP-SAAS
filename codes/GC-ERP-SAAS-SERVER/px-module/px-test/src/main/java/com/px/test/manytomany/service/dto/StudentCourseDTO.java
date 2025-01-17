/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.test.manytomany.service.dto;

import javax.validation.constraints.NotNull;

import com.px.core.query.Query;
import com.px.core.query.QueryType;
import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 学生课程记录DTO
 * @author 品讯科技
 * @version 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StudentCourseDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	/**
     * 学生
     */
	//(message="学生不能为空")
    @Query(tableColumn = "a.student_id", javaField = "student.id", type = QueryType.LIKE)
	private StudentDTO student;
	/**
     * 课程
     */
	//(message="课程不能为空")
    @Query(tableColumn = "a.course_id", javaField = "course.id", type = QueryType.LIKE)
	private CourseDTO course;
	/**
     * 分数
     */
	//(message="分数不能为空")
	private Double score;
	/**
     * 备注信息
     */
	private String remarks;

}
