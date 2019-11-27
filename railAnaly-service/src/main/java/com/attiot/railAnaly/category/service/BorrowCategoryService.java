package com.attiot.railAnaly.category.service;


import com.attiot.railAnaly.category.dao.ABorrowCategoryDao;
import com.attiot.railAnaly.category.entity.ABorrowCategory;
import com.attiot.railAnaly.category.param.ABorrowCategoryQueryParam;
import com.attiot.railAnaly.foundation.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class BorrowCategoryService {
    @Autowired
    private ABorrowCategoryDao aBorrowCategoryDao;

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-09 13:33:30
     */
    public Page<ABorrowCategory> query(ABorrowCategoryQueryParam param){
        Page<ABorrowCategory> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aBorrowCategoryDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aBorrowCategoryDao.query(param));
        }
        return page;
    }
}
