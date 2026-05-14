package com.jobplus.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result() {}
    private Result(int code, String message, T data) { this.code = code; this.message = message; this.data = data; }

    public static <T> Result<T> success(T data) { return new Result<>(200, "success", data); }
    public static <T> Result<T> success() { return new Result<>(200, "success", null); }
    public static <T> Result<T> error(int code, String message) { return new Result<>(code, message, null); }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
