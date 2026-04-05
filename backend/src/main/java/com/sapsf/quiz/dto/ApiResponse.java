package com.sapsf.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private T data;
    private String error;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, null);
    }
}
