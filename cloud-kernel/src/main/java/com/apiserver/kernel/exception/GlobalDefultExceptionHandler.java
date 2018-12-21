package com.apiserver.kernel.exception;

import com.apiserver.kernel.result.JsonResult;
import com.apiserver.kernel.result.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebResult;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
@Slf4j
public class GlobalDefultExceptionHandler {

    /**
     * 用来处理bean validation异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public JsonResult resolveConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> resultMap = new HashMap<>();

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        resultMap.put("statusMessage", ex.getMessage());
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            resultMap.put("statusMessage", errorMessage);

        }

        resultMap.put("statusCode", StatusCode.Not_ACCEPTABLE.getCode());
        return JsonResult.getFailureResult(StatusCode.INVALID_REQUEST, resultMap);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JsonResult resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> resultMap = new HashMap<>();
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        resultMap.put("statusMessage", ex.getMessage());
        if (!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            resultMap.put("statusMessage", errorMessage);
        }
        resultMap.put("statusCode", StatusCode.Not_ACCEPTABLE.getCode());
        return JsonResult.getFailureResult(StatusCode.INVALID_REQUEST, resultMap);
    }


}

