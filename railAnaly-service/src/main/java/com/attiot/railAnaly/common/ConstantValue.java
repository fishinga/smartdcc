/**   
* @Title: ComstantValue.java 
* @Package com.attiot.dms.common 
* @Description: TODO
* @author zhangbf
* @company attiot  
* @date 2017年9月12日 下午4:07:34 
* @version V1.0   
*/
package com.attiot.railAnaly.common;

/**
 * @author zhangbf
 *
 */
public class ConstantValue {

	//任务来源1系统修
	public static final Integer JOB_SOURCE_SYSTEM=1;
	//任务来源2普查整改
	public static final Integer JOB_SOURCE_REFORM=2;
	//任务来源3故障提报
	public static final Integer JOB_SOURCE_FAULT=3;
	//临时
	public static final Integer JOB_SOURCE_TEMP=4;
	//日检、四日检
	public static final Integer JOB_SOURCE_DAILY=5;
	//任务交接
	public static final Integer JOB_SOURCE_ROLLBACK=6;

	//作业牌
	public static final String BORROW_CATEGORY_BRAND="1001";
	//钥匙类
	public static final String BORROW_CATEGORY_KEY="1002";
	//电子设备类
	public static final String BORROW_CATEGORY_ELECTICDEV="1003";
	//断送电类
	public static final String BORROW_CATEGORY_STARTORSTOPELECTIC="1004";

	public static final String DEFAULT_UPLOAD_FOLDER="D:/upload/";
	//断送电
	public static final String PUSH_MSG_DUANDIAN = "duandian";
	//归还
	public static final String PUSH_MSG_GUIHUANG="guihuang";
	//挂牌
	public static final String PUSH_MSG_GUAPAI="guapai";
	//待办
	public static final String PUSH_MSG_DAIBANG="daibang";
	//工单
	public static final String PUSH_MSG_GONGDAN="gongdan";

	/****任务来源*****/
	public static final Integer TASK_SOURCE_1=1;//系统修
	public static final Integer TASK_SOURCE_2=2;//普查整改
	public static final Integer TASK_SOURCE_3=3;//故障提报
	public static final Integer TASK_SOURCE_4=4;//临时
	public static final Integer TASK_SOURCE_5=5;//日检/四日检
	public static final Integer TASK_SOURCE_6=6;//任务交接
	public static final Integer TASK_SOURCE_7=7;//定额
	public static final Integer TASK_SOURCE_8=8;//常规任务
	public static final Integer TASK_SOURCE_9=9;//部分完成
	
	
	
}
