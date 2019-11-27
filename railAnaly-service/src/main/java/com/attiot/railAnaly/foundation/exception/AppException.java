package com.attiot.railAnaly.foundation.exception;


/**
 * 功能/模块：应用异常<br/>
 * 类描述：返回相关错误信息<br/>
 * 修订历史：：<br/>
 * 日期  作者  参考  描述：<br/>
 *
 * @author dengsc
 * @version 1.0 17-5-19
 * @see
 */
public class AppException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ErrorInfo errorInfo;

    public ErrorInfo getErrorInfo() {
		return errorInfo;
	}

	public AppException() {
        super();
    }

    public AppException(ErrorInfo errorInfo) {
        super(errorInfo.getMessage());
        this.errorInfo = errorInfo;
    }

    public AppException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo.getMessage(), cause);
        this.errorInfo = errorInfo;
    }

    public String getCode() {
        return errorInfo.getCode();
    }

    public String getMessage() {
        return errorInfo.getMessage();
    }
}
