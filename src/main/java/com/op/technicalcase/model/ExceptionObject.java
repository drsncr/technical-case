package com.op.technicalcase.model;

public class ExceptionObject {
    private Integer errorCode;
    private String errorMeesage;

    public ExceptionObject(Integer errorCode, String errorMeesage) {
        this.errorCode = errorCode;
        this.errorMeesage = errorMeesage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMeesage() {
        return errorMeesage;
    }

    public void setErrorMeesage(String errorMeesage) {
        this.errorMeesage = errorMeesage;
    }
}
