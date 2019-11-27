/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.point.dao;

import com.attiot.railAnaly.point.entity.ATaskListForgein;
import com.attiot.railAnaly.task.entity.ATaskList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 任务工单(外来人员)
 *
 * @author attiot
 * 2018-06-20 15:58:15
 */
@Repository
public interface ATaskListForgeinDao {
    /**
     * 新增
     *
     * @param aTaskListForgein 参数
     *
     * @author attiot
     * 2018-06-20 15:58:15
     */
    void insert(ATaskListForgein aTaskListForgein);

    /**
     * 修改
     *
     * @param aTaskListForgein 参数
     *
     * @author attiot
     * 2018-06-20 15:58:15
     */
    void update(ATaskListForgein aTaskListForgein);

    /**
     * 删除
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-06-20 15:58:15
     */
    void delete(String id);


    public List<ATaskListForgein> getUnFinishATaskListForgeinList();

    public List<ATaskListForgein> getByIds(String ids);



    /**
     * 根据主键id获取实体对象
     *
     * @param id 主键
     *
     * @author attiot
     * 2018-06-20 15:58:15
     */
    ATaskListForgein getById(String id);

    public void batchInsert(List<ATaskListForgein> list);

    public List<ATaskListForgein> getATaskListForgein(Map params);

    public void updateWorkStateAndPointByIds(Map params);

    public void updateParentTaskId(List<ATaskListForgein> list);
}
