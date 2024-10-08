package com.apprack.pm_ms.model;

public class ApiResponse<T> {
    private int code;
    private boolean success;
    private String message;  // This will hold the success or failure message
    private T data;

    public ApiResponse(boolean success, int code, T data, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;

    }

    // Factory method for success response
    public static <T> ApiResponse<T> success(int code, T data, String message) {
        return new ApiResponse<>(true, code, data, message);
    }

    // Factory method for error response
    public static <T> ApiResponse<T> error(int code,  String message) {
        return new ApiResponse<>(false, code, null, message);
    }



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
