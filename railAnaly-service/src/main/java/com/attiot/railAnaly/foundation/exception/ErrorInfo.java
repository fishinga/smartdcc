package com.attiot.railAnaly.foundation.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能/模块：<br/>
 * 类描述：<br/>
 * 修订历史：：<br/>
 * 日期  作者  参考  描述：<br/>
 *
 * @author 
 * @version 
 * @see
 */
public enum ErrorInfo {
	// 定单
	DEMAND_EXIST("6100", "当前车辆已有需求单，请勿重复提交"),
	INNER_EMPTY("6200", "查无定单"),
	INNER_PILE_EMPTY("6201", "充电桩未登记。"),
	INNER_PILE_EXIST("6202", "当前充电桩已启动，请勿重复提交。"),
	ORDER_EMPTY("6300", "查无定单"),
    CUSTOMER_CANNOT_PLACE("6301","没有开通小额免密功能，账户余额不足60元。"),
    GROUP_CUSTOMER_CANNOT_PLACE("6302","没有开通后付款，账户余额不足60元。"),
    VIP_CUSTOMER_CANNOT_PLACE("6303","账户余额不足40元。"),
    IS_NOT_VIP("6304","该账户不是VIP账户"),
    CUSTOMER_HAS_ORDER("6305","您还有正在充电中的订单，请先结算订单！"),
	// 充电
	STATION_EMPTY("7100", "充电站未登记。"),
	STATION_EXIST("7101", "充电车已存在，请勿重复提交。"),
	STATION_NOT_BIND("7102", "充电桩不能绑定在小充电站。"),
	PILE_EMPTY("7200", "充电桩未登记。"),
	PILE_EXIST("7201", "充电桩已存在，请勿重复提交。"),
	PILE_CHARGE_EMPTY("7200", "无充电桩自充单。"),
	PILE_CHARGE_IN("7200", "充电桩自充中，请勿重复提交。"),
	CAR_EMPTY("7300", "充电车未登记。"),
	CAR_EXIST("7301", "充电车已存在，请勿重复提交。"),
	CAR_CHARGE_EMPTY("7302", "无充电车自充单。"),
	CAR_CHARGE_IN("7303", "充电车自充中，请勿重复提交。"),
	CAR_MANAGER_UP("7304", "充电车绑定送电员数量已达上限。"),
	CAR_PILE_UP("7305", "充电车绑定充电桩数量已达上限。"),
    CAR_WORKING("7306", "充电车有定单。"),
    MANAGER_BINDED("7307", "已有绑定车辆。"),
    CAR_MANAGER_UNBINDED("7308", "您尚未绑定该车辆"),
    PILE_UNBINDED("7309", "您尚未绑定该充电桩，无法扫描启动。"),
	// 评价
	EVALUATE_EXIST("8000", "定单已评价，请勿重复提交。"),

	TSUSER_NAME_NOT_EXIST("10011", "充电员未登记"),
	USER_SESSION_OUTTIME("10012", "登录用户会话超时"),
    USER_NAME_NOT_EXIST("1001", "用户或密码错误"),
    USER_PASSWORD_ERROR("1002", "用户密码错误"),
    PHONE_HAVE_REGISTERED("1003", "该手机号已经注册过"),
    USER_LOCKED("1004", "该用户已被锁定"),
    LOGIN_USER_NAME_EMPTY("1005", "用户名不能为空。"),
    LOGIN_PASSWORD_EMPTY("1006", "密码不能为空。"),
    OLD_PASSWORD_ERROR("1007", "原密码不正确"),
    USER_UNUSUAL("1008", "用户信息异常"),    
    REGISTER_PHONE_SMS_OVER("1022", "发送短信过于频繁，请稍后再试"),

    COUPON_CITY_ERROR("1009", "参数cityCode错误"),
    COUPON_BUSTYPE_ERROR("1010", "参数busType错误"),
    COUPON_CODE_ERROR("1011", "优惠券编码不能为空"),
    
    EMPTY("", ""),
    AUTHORITY_LACK("8000", "无操作权限"),
    AUTHORITY_LACK_CARUSER("8100", "没有管理该借款人信息的权限"),
    AUTHORITY_LACK_CARINFO("8101", "没有管理该车辆信息的权限"),
    PARAM_NOT_ALLOW("8001", "不允许的参数，请确保发送的参数是否能进行相应的操作"),
    PARAM_NOT_ALLOW_WIRED("8002", "有线设备，不允许该操作"),
    PARAM_NOT_ALLOW_DONE("8003", "该设备已经执行过该操作，不允许重复操作"),
    PARAM_NOT_ALLOW_DONT_NEED("8004", "该设备还未开启追踪，无需关闭"),
    ORG_ID_EMPTY("8005","机构ID不能为空"),
    CITY_CODE_EMPTY("8006","城市编码不能为空"),
    //充电卡
    BASE_CARD_EMPTY("8007","无效的充电卡"),
    BASE_CARD_CITY_ERROR("8008","充电卡所对应的城市不符！"),
    BASE_CARD_PWD_ERROR("8009","充电卡密码不正确！"),
    BASE_CARD_IS_USED("8010","充电卡已使用过！"),
    BASE_CARD_IS_OVERDUE("8011","充电卡已过期！"),

    NEEL_LOGIN("9990", "需要登录"),
    VERITY_CODE_ERROR("9991", "验证码错误"),
    VERITY_CITY_ERROR("9994", "城市错误"),
    CLIENT_MISS("9995", "非法客户端"),
    SECRET_ERROR("9996", "验签错误"),
    PARAM_MISS("9997", "参数缺失"),
    PARAM_ERROR("9998", "参数错误"),
    //注册错误提示
    REGISTER_PASSWORD_ERROR("9089","两次输入的密码不一致"),
    SERVER_ERROR("9999", "系统繁忙"),
    WX_BINDUSER_ERROR("3001","该用户已经绑定,请解除绑定"),
    //支付订单消息
    PAY_ORDER_EXIST("2000", "订单已经支付,无需重复支付"),
    PAY_ORDER_NOEXIST("2000", "订单不存在"),
    PAY_ORDER_NOPAY("2000", "订单已失效"),
    ERROR_RTNTOSCH("2000", "临时任务已被删除无法交接！"),
    ;



    private String code;
    private String message;

    ErrorInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorInfo getEnumByValue(String value) {
        ErrorInfo [] enumArr = ErrorInfo.class.getEnumConstants() ;
        for(ErrorInfo item:enumArr) {
            if  (item.getCode().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }
    
    public Map<String, Object> getMap() {
    	Map<String, Object> map = new HashMap<>();
        map.put("code",code);
        map.put("message",message);
        return map;
    }
}
