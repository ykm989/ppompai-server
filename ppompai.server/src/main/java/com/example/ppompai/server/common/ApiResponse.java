package com.example.ppompai.server.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;

    // 성공 케이스에만 채워지고, 실패/오류 시엔 null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", data, null);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>("fail", null, null);
    }

    public static <T> ApiResponse<Void> fail(String msg) {
        return new ApiResponse<>("fail", null, msg);
    }

    public static ApiResponse<Void> error(String msg) {
        return new ApiResponse<>("error", null, msg);
    }
}
