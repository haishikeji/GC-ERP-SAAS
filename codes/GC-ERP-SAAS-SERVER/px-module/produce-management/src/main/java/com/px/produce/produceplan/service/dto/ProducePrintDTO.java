/**
 * Copyright © 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.px.produce.produceplan.service.dto;

import com.px.core.service.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProducePrintDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;
    private String salesorderBatchnumber;
    private String inventoryName;
    private String inventoryCode;
    private String model;
    private String singalUnit;
    private String nventoryAttributeValue;
    private String planProduceNum;
    private Date orderDate;
    private Date deliveryDate;
    private Date planFinishDate;
    private Date trueFinishDate;


}
