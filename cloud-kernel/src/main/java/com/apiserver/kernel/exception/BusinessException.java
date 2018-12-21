package com.apiserver.kernel.exception;

import com.apiserver.kernel.result.StatusCode;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;  //错误码

    public BusinessException() {
    }

    public BusinessException(StatusCode resultEnum) {
        super(resultEnum.getMeaning());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
