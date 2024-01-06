package com.example.demo.app.inquiry;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class InquiryForm {

    // アノテーションを使い各フィールドに対する条件を記述
    @Size(min = 1, max = 20, message = "Please input 20characters or less")
    private String name;

    // @Emailに関してはセキュリティが弱いらしく違うものを本来は使うといいらしい
    @NotNull
    @Email(message = "invalid E-mail Format")
    private String email;

    @NotNull
    private String contents;

    public InquiryForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
