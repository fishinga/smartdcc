package com.attiot.railAnaly.point.service;

import com.attiot.railAnaly.foundation.Page;
import com.attiot.railAnaly.point.dao.APointPleaseStingerDao;
import com.attiot.railAnaly.point.entity.APointPleaseStinger;
import com.attiot.railAnaly.point.param.APointPleaseStingerQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class PointPleaseStingerService {
    @Autowired
    private APointPleaseStingerDao aPointPleaseStingerDao;

    /**
     * 新增
     *
     * @param aPointPleaseStinger 参数
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    public void create(APointPleaseStinger aPointPleaseStinger){
        aPointPleaseStingerDao.insert(aPointPleaseStinger);
    }

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    public void delete(String id){
        aPointPleaseStingerDao.delete(id);
    }


    /***
     * 根据主键查询
     * @param id
     * @return
     */
    public APointPleaseStinger getById(String id){
        return  aPointPleaseStingerDao.getById(id);
    }

    /***
     * 根据请点ID 查询
     * @param pointId
     * @return
     */
    public APointPleaseStinger getByPointId(String pointId){
        return  aPointPleaseStingerDao.getByPointId(pointId);
    }

    /**
     * 修改
     *
     * @param aPointPleaseStinger 参数
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    public void update(APointPleaseStinger aPointPleaseStinger){
        aPointPleaseStingerDao.update(aPointPleaseStinger);
    }

    /**
     * 查询
     *
     * @param param 参数
     *
     * @author attiot
     * 2018-04-17 20:27:15
     */
    public Page<APointPleaseStinger> query(APointPleaseStingerQueryParam param){
        Page<APointPleaseStinger> page = new Page<>();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalNum(aPointPleaseStingerDao.queryCount(param));
        if(page.isOverCount()){
            page.setResults(Collections.EMPTY_LIST);
        }else{
            page.setResults(aPointPleaseStingerDao.query(param));
        }
        return page;
    }
}
