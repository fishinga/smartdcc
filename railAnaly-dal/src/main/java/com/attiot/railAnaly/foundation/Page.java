package com.attiot.railAnaly.foundation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
@Getter
@Setter
@ToString
public class Page<T> implements Iterable<T> {

    private List<T> results = new ArrayList<T>();
    private long totalNum = 0;
    private int pageSize = 10;
    private int pageNo = 1;

    public long getTotalPage() {
        return this.totalNum / this.pageSize + ((this.totalNum % this.pageSize > 0) ? 1 : 0);
    }


    /**
     * 数据是否有溢出（总数据量小于(pageNo-1)*pageSize)
     *
     * @return
     */
    public boolean isOverCount() {
        if (totalNum == 0) {
            return false;
        }
        if (pageNo == 1) {
            return false;
        }
        int i = (pageNo - 1) * pageSize;
        return i >= totalNum;
    }

    /**
     * 数据是否还为查完
     *
     * @return
     */
    public boolean hasNext() {
        return totalNum > pageNo * pageSize;
    }


    public int getMaxPageNo() {
        if (isOverCount()) {
            return BigDecimal.valueOf(totalNum)
                .divide(BigDecimal.valueOf(pageSize), 0, RoundingMode.UP).intValue();
        }
        return pageNo;
    }

    public Iterator<T> iterator() {
        return results.iterator();
    }
}
