/**
 * Copyright (c) 2017-2027 厦门物之联智能科技有限公司.
 */
package com.attiot.railAnaly.task.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import com.attiot.railAnaly.foundation.SqlQueryParam;

/**
 * 风险预控表
 * @author attiot
 * 2018-05-28 10:30:22
 */
@Getter
@Setter
@ToString
public class TaskTableQueryParam extends SqlQueryParam {

    private String	id;
    //
    private String	tablename;
    //
    private String	tablecode;
    //
    private String	creator;
    //
    private Date	createtime;
    //
    private String	modifor;
    //
    private Date	modifytime;

}
