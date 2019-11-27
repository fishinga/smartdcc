/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.service;

import com.attiot.railAnaly.common.util.StringUtil;
import com.attiot.railAnaly.metro.dao.APerformanceContentsDao;
import com.attiot.railAnaly.metro.dao.APerformanceDataDao;
import com.attiot.railAnaly.metro.entity.APerformanceContents;
import com.attiot.railAnaly.metro.entity.APerformanceData;
import com.attiot.railAnaly.metro.param.APerformanceDataQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.attiot.railAnaly.foundation.Page;

import java.util.Collections;
import java.util.List;

/**
 * 考评明细数据
 *
 * @author attiot
 * 2019-02-14 14:58:38
 */
@Service
public class APerformanceDataService{

    @Autowired
    private APerformanceDataDao aPerformanceDataDao;
    @Autowired
    private APerformanceContentsDao aPerformanceContentsDao;

    /**
     * 新增
     *
     * @param aPerformanceData 参数
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    public void create(APerformanceData aPerformanceData){
        //添加考评内容
        if(StringUtil.isNotEmpty(aPerformanceData.getPerfContents())){
            APerformanceContents aPerformanceContents = new APerformanceContents();
            aPerformanceContents.setContents(aPerformanceData.getPerfContents());
            aPerformanceContentsDao.insert(aPerformanceContents);
            aPerformanceData.setPerfContentsId(aPerformanceContents.getId());
        }
        aPerformanceDataDao.insert(aPerformanceData);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    public void delete(String id){
        APerformanceData aPerformanceData = getById(id);
        if(null != aPerformanceData){
            aPerformanceContentsDao.delete(aPerformanceData.getPerfContentsId());
        }
        aPerformanceDataDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
     public APerformanceData getById(String id){
        return  aPerformanceDataDao.getById(id);
     }


    /**
     * 修改
     *
     * @param aPerformanceData 参数
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    public void update(APerformanceData aPerformanceData){
        APerformanceContents aPerformanceContents = aPerformanceContentsDao.getById(aPerformanceData.getPerfContentsId());
        if(null != aPerformanceContents){
            aPerformanceContents.setContents(aPerformanceData.getPerfContents());
            aPerformanceContentsDao.update(aPerformanceContents);
        }
        aPerformanceDataDao.update(aPerformanceData);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2019-02-14 14:58:38
     */
    public Page<APerformanceData> query(APerformanceDataQueryParam param){
        Page<APerformanceData> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceDataDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceDataDao.query(param));
        }
        return page;
    }

    public List<APerformanceData> getListByQuery(APerformanceDataQueryParam queryParam) {
        return aPerformanceDataDao.query(queryParam);
    }

    public APerformanceData getByQuery(APerformanceDataQueryParam aPerformanceDataQueryParam) {
        return aPerformanceDataDao.getByParam(aPerformanceDataQueryParam);
    }

    public Page<APerformanceData> queryTen(APerformanceDataQueryParam param) {
        Page<APerformanceData> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPerformanceDataDao.queryCountTen(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPerformanceDataDao.queryTen(param));
        }
        return page;
    }

}
