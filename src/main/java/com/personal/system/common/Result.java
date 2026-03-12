package com.personal.system.common;

/**
 * 全局统一返回结果类
 * 使用泛型 <T> 来支持任意类型的数据载荷
 */
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    // 私有化构造函数，强制使用静态方法创建对象
    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // ====== 成功响应的快捷方法 ======

    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    // ====== 失败响应的快捷方法 ======

    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    // ====== Getter 和 Setter (必须有，否则 Spring 无法将其转为 JSON) ======

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}