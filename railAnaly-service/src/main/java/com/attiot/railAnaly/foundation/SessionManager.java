package com.attiot.railAnaly.foundation;

/**
 * 功能/模块：<br/>
 * 类描述：<br/>
 * 修订历史：：<br/>
 * 日期  作者  参考  描述：<br/>
 *
 * @author dengsc
 * @version 1.0
 * @see
 */

public class SessionManager {

    private static ThreadLocal<Session> threadLocal = new ThreadLocal<>();

    /**
     * 获得当前登录的用户id 或 授权的第三方客户id
     *
     * @return 如果未登录，返回null值
     */
    public static String getLoginId() {
        Session session = threadLocal.get();
        if (session != null) {
            return session.getUserId();
        }
        return null;
    }

    /**
     * 获取用户在线会话信息
     *
     * @return
     */
    public static Session getUserOnlineSession() {
        return  threadLocal.get();
    }

    /**
     * 将当前登录的用户名绑定到线程中
     *
     * @param session 会话信息
     */
    public static void boundUserOnline(Session session) {
        if (session != null) {
            threadLocal.set(session);
        }
    }

    public static void clearThreadLocal() {
        threadLocal.remove();
    }

}
