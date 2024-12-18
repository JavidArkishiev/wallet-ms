package com.example.walletms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String message;
    private boolean success;

    public BaseResponse(T data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public static <T> BaseResponse<T> oK(T data) {
        return new BaseResponse<>(data, "success", true);
    }


    public static <T> BaseResponse<T> success(String message) {
        return new BaseResponse<>(null, message, true);
    }

    public static <T> BaseResponse<T> failure(String message) {
        return new BaseResponse<>(null, message, false);
    }
}
