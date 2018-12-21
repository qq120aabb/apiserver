package com.apiserver.kernel.result;

import com.apiserver.kernel.exception.BusinessException;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author jarvis
 */
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = -3458066472643731732L;

    /**
     * 状态码
     */
    private Integer status;
    /**
     * 返回的消息
     */
    private String message;

    /**
     * 数据内容体
     */
    private T content;


    public static <E> JsonResult<E> getSuccessResult() {
        return getResult(StatusCode.OK, null);
    }

    public static <E> JsonResult<E> getSuccessResult(E data) {
        return getResult(StatusCode.OK, data);
    }

    public static <E> JsonResult<E> getFailureResult() {
        return getResult(StatusCode.SERVER_ERROR,null);
    }

    public static <E> JsonResult<E> getFailureResult(StatusCode code,E data){
        JsonResult<E> result = new JsonResult<E>();
        result.setStatus(code.getCode());
        result.setMessage(code.getMeaning());
        result.setContent(data);
        return result;
    }

    public static <E> JsonResult<E> getBusinessResult(BusinessException businessError) {
        JsonResult<E> result = new JsonResult<E>();
        result.setStatus(businessError.getCode());
        result.setMessage(businessError.getMessage());
        result.setContent(null);
        return result;
    }

    public static <E> JsonResult<E> getFailureResult(String errmsg) {
        JsonResult<E> result = new JsonResult<E>();
        result.setStatus(StatusCode.SERVER_ERROR.getCode());
        result.setMessage(errmsg);
        result.setContent(null);
        return result;
    }

    public static <E> JsonResult<E> getResult(StatusCode code,E data) {
        JsonResult<E> result = new JsonResult<E>();
        result.setStatus(code.getCode());
        result.setMessage(code.getMeaning());
        result.setContent(data);
        return result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }


}
