package com.example.demo.service;

// このファイルも独自例外を補足する際に作成
public class InquiryNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InquiryNotFoundException(String message) {
        super(message);
    }

}
