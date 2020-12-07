package com.tw.certmaster.handlers;

import com.tw.certmaster.exceptions.RequestParamValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class RequestParamValidationExceptionHandler {
    @ExceptionHandler(RequestParamValidationException.class)
    public final ResponseEntity<ErrorResponse> handleParamValidationException(
            RequestParamValidationException ex,
            WebRequest request
    )
    {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse error = new ErrorResponse("Bad request", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
