package ka.masato.wordcloud.wordcloudcreator.controller.advice;

import ka.masato.wordcloud.wordcloudcreator.controller.model.ApiErrorMessage;
import ka.masato.wordcloud.wordcloudcreator.exception.FailedGetRawTextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RestControllerExceptionAdvice {

    private final Map<Class<? extends Exception>, String> messageMappings =
            Collections.unmodifiableMap(new LinkedHashMap() {{
                put(MethodArgumentNotValidException.class, "Require value is invalid.");
                put(BindException.class, "Require value is invalid.");
                put(HttpMessageNotReadableException.class, "JSON message is invalid.");
                put(FailedGetRawTextException.class, "Can not access target url.");
            }});

    public RestControllerExceptionAdvice() {
    }

    private String resolveMessage(Exception ex, String defaultMessage) {
        return messageMappings.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(ex.getClass())).findFirst()
                .map(Map.Entry::getValue).orElse(defaultMessage);
    }

    private ApiErrorMessage createApiError(Exception ex) {
        ApiErrorMessage apiError = new ApiErrorMessage();
        apiError.setMessage(resolveMessage(ex, ex.getMessage()));
        return apiError;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ApiErrorMessage result = createApiError(ex);
        ex.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> result.setDetails(fieldError.getField(), fieldError.getDefaultMessage()));
        return result;
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorMessage handleBindException(BindException ex) {
        ApiErrorMessage result = createApiError(ex);
        ex.getBindingResult().getFieldErrors().stream()
                .forEach(fieldError -> result.setDetails(fieldError.getField(), fieldError.getDefaultMessage()));
        return result;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorMessage handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ApiErrorMessage result = createApiError(ex);
        result.setDetails("", ex.getMessage());
        return result;
    }

    @ExceptionHandler({FailedGetRawTextException.class})
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiErrorMessage handleFailedGetRawTextException(FailedGetRawTextException ex) {
        ApiErrorMessage result = createApiError(ex);
        result.setDetails("", "The URL ");
        return result;
    }


}