package com.tinnguyen263.mykanban.controller.ResponseObject;

public class ResponseObject {
    public boolean isSuccess;
    public com.tinnguyen263.mykanban.controller.ResponseObject.Error error;
    public Object data;

    public ResponseObject(boolean isSuccess, com.tinnguyen263.mykanban.controller.ResponseObject.Error error, Object data) {
        this.isSuccess = isSuccess;
        this.error = error;
        this.data = data;
    }
}
