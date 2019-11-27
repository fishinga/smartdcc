/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.borrow.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 借用物品明细
 *
 * @author attiot
 * 2018-04-10 19:46:33
 */
@Getter
@Setter
@ToString
public class ABorrowListDetail{
    
    /***/
    private String id;
    /**借用清单Id*/
    private String borrowListId;
    /**编号*/
    private String code;
    /**单位*/
    private String unit;
    /**借用物品ID*/
    private String borrowGoodsId;
    /**借用物品名称*/
    private String borrowGoodsName;
    /**借用物品类型*/
    private String borrowGoodsCategory;
    /**类型名称*/
    private String categoryName;

    /***归还状态:1-待还，2-归还中,3-已还,4-转借*/
    private String returnType;

    /***归还者*/
    private String restituer;

    private String restituername;

    /***归还日期*/
    private String returnTime;

    /***归还单号*/
    private String returnNum;

    /***转借之前的原ID*/
    private String attachId;

}
