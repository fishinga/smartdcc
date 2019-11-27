package com.attiot.railAnaly.util;

/**
 * Created by SSS on 2018/4/19.
 */
public class Constant {

    /******** 指派方式  begin ******/
    /***
     * :1按个人，2按角色；3按组织机构；4 按申请人 5 按工班长  6-上节点指派
     */
    public static final int ASSIGN_TYPE_1 = 1;
    public static final int ASSIGN_TYPE_2 = 2;
    public static final int ASSIGN_TYPE_3 = 3;
    public static final int ASSIGN_TYPE_4 = 4;
    public static final int ASSIGN_TYPE_5 = 5;
    public static final int ASSIGN_TYPE_6 = 6;
    /******** 指派方式  end  ******/


    /****** 流程类型  begin  ***/
    /**
     * 请销点
     */
    public static final String PROCESS_TYPE_1= "1";
    /**
     * 借用归还
     */
    public static final String PROCESS_TYPE_2= "2";

    /****** 流程类型  end   ***/


    /************ 流程  begin ***********/
    /***
     *  （1010 登车许可请点 ，1011, 登车许可销点）
        （1020 Stinger请点 ，1021, Stinger销点）
        （1030 人工推车请点 ，1031,人工推车销点）
        （2010 借用 ，2011,归还）
     */
    public static final String PROCESS_CODE_1010 = "1010";
    public static final String PROCESS_CODE_1011 = "1011";
    public static final String PROCESS_CODE_1020 = "1020";
    public static final String PROCESS_CODE_1021 = "1021";
    public static final String PROCESS_CODE_1030 = "1030";
    public static final String PROCESS_CODE_1031 = "1031";
    public static final String PROCESS_CODE_2010 = "2010";
    public static final String PROCESS_CODE_2011 = "2011";
    /************ 流程  end  ***********/


    /***  审批 begin ****/
    /***
     * 审批通过与否：1审批通过；-1审批不通过;0审批中
     */
    public static final String AUDIT_STATE = "-1";
    public static final String AUDIT_STATE_0 = "0";
    public static final String AUDIT_STATE_1 = "1";
    /***  审批 end ****/
}
