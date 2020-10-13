package com.example.PromotionEngine.PromotionEngine.Exception;

public class ErrorResponse {

    private String message;
    private String errorcode;

    public ErrorResponse(String message, String errorcode) {
        super();
        this.message = message;
        this.errorcode = errorcode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorcode() {
        return errorcode;
    }
}
