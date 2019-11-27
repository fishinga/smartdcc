package com.attiot.railAnaly.metro.model;

import lombok.Data;

import java.util.List;

/**工器具--模型
 * @author Administrator
 * 2018-04-19 9:59
 **/
@Data
public class ToolsTypeModel {
    //类型Code
    private  String typeCode;
    //类型名称
    private  String typeText;
    //子记录 位置、编码、状态
    private List<ToolsTypeLocationModel> child;

}
