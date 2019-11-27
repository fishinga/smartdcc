package com.attiot.railAnaly.foundation;

import lombok.Getter;
import lombok.Setter;

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
@Setter
@Getter
public class SqlQueryParam {

    public static final int LOCK_FOR_UPDATE = 0;
    public static final int LOCK_IN_SHARE_MODE = 1;

    public static final String SORT_DESC = "DESC";
    public static final String SORT_ASC = "ASC";

    public static final String SQL_CREATE_TIME = "CREATE_TIME";
    private static final int MAX_LIMIT = 1000;

    protected int pageNo = 1;
    protected int pageSize = 1000;
    protected String column;
    protected String sort = SORT_DESC;
    protected int limit = -1;
    protected boolean lock;
    protected int lockMode; // 0:排它锁 FOR UPDATE 1：共享锁 LOCK IN SHARE MODE


    public int getStartIndex() {
//        System.out.println();
        int i = getPageNo() - 1;
        if (i < 0) {
            return 0;
        }
        return i * getPageSize();
    }

    public void setLimit(int limit) {
        this.limit = limit;
        this.pageNo = -1;
    }


    public String getColumn() {
        if (column != null) {
            if (column.contains("'")) {
                return null;
            }
            return column;
        }
        return null;
    }

    public int getLimit() {
        return limit > MAX_LIMIT ? MAX_LIMIT : limit;
    }

    public String getSort() {
        if (SORT_DESC.equalsIgnoreCase(sort) || SORT_ASC.equalsIgnoreCase(sort)) {
            return sort;
        }
        return null;
    }

    public void setMaxLimit() {
        this.pageNo = -1;
        this.limit = MAX_LIMIT;
    }
}
