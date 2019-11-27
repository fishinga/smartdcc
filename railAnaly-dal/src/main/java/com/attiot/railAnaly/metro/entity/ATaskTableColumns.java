/**
* Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
*/
package com.attiot.railAnaly.metro.entity;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author attiot
 * 2018-05-26 09:12:40
 */
public class ATaskTableColumns{
    
    /***/
    private String id;
    /***/
    private String taskTableId;
    /***/
    private String parentId;
    /**字段排序*/
    private Integer colSort;
    /**编号*/
    private String colCode;
    /**名称*/
    private String colName;
    /**类型：0目录/分类；1输入框；2单选框*/
    private Integer colType;
    /**默认值*/
    private String defaultVal;
    /**选项*/
    private String colItems;

    private List itemList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskTableId() {
        return taskTableId;
    }

    public void setTaskTableId(String taskTableId) {
        this.taskTableId = taskTableId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getColSort() {
        return colSort;
    }

    public void setColSort(Integer colSort) {
        this.colSort = colSort;
    }

    public String getColCode() {
        return colCode;
    }

    public void setColCode(String colCode) {
        this.colCode = colCode;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public Integer getColType() {
        return colType;
    }

    public void setColType(Integer colType) {
        this.colType = colType;
    }

    public String getDefaultVal() {
        return defaultVal;
    }

    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    public String getColItems() {
        return colItems;
    }

    public void setColItems(String colItems) {
        this.colItems = colItems;
    }

    public List getItemList() {
        if(null != colItems && colItems.length()>0) {
            String[] array = colItems.split("/");
            for(int i=0;i<array.length;i++) {
                Map obj = new HashMap();
                obj.put("key",array[i]);
                obj.put("value",array[i]);
                itemList.add(obj);
            }
        }
        return itemList;
    }

    public void setItemList(List itemList) {
        this.itemList = itemList;
    }
}
