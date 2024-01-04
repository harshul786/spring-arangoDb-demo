package com.demo.models.ResponseModels;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
public class CustomResponseModel<T> {
    private T data;
    private boolean isSuccess = false;
    private String errorMessage = "";
    private int statusCode = 404;

    @Override
    public String toString() {
        return "CustomResponseModel{" +
                "data=" + data +
                ", isSuccess=" + isSuccess +
                ", errorMessage='" + errorMessage + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public static <T> ResponseEntity<CustomResponseModel<T>> wrapWithData(T data) {
        CustomResponseModel<T> customResponseModel = CustomResponseModel.<T>builder()
                .data(data)
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(customResponseModel);
    }
}
