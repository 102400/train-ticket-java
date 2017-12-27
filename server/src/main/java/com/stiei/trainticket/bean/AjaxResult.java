package com.stiei.trainticket.bean;

/**
 * response json
 * @param <T>
 */
public class AjaxResult<T> {

    private String code = "0";
    private String message = "Success";
    private T data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    private AjaxResult() {}

    private static <T> AjaxResult build(String code, String message, T data) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.code = code;
        ajaxResult.message = message;
        ajaxResult.data = data;
        return ajaxResult;
    }

    private AjaxResult(T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> AjaxResult newSuccess(T data) {
        return build("0", "Success", data);
    }

    public static <T> AjaxResult newError(T data) {
        return build("-1", "Error", data);
    }

    public static <T> AjaxResult newIllegalAccess(T data) {
        return build("-2", "IllegalAccess", data);
    }
}
