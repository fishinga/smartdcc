/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.goods.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 物品损耗信息
 *
 * @author attiot
 * 2018-05-02 20:21:58
 */
@Getter
@Setter
@ToString
public class ABorrowGoodLoss{
    
    /**主键ID*/
    private String id;
    /**物品信息ID*/
    private String goodsId;
    /**物品名称*/
    private String goodsName;
    /**磨损等级*/
    private String wearGrade;
    /**备注*/
    private String remarks;
    /**使用人ID*/
    private String userId;
    /**使用人名字*/
    private String userName;
    /**创建者*/
    private String creater;
    /**创建时间*/
    private Date createTime;
    /**审批流单号*/
    private String workFlowWbNo;

}
