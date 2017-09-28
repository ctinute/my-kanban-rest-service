package com.tinnguyen263.mykanban.controller.ResponseObject;

public class Error {
    public String status;
    public String title;
    public String detail;

    public Error(String status, String title, String detail) {
        this.status = status;
        this.title = title;
        this.detail = detail;
    }
}
