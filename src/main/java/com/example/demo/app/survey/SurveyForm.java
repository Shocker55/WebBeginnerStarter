package com.example.demo.app.survey;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SurveyForm {

    @NotNull
    @Min(0)
    @Max(150)
    private Integer age;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer satisfaction;

    @NotNull
    @Size(min = 1, max = 200, message = "Please input 200 characters or less")
    private String comment;


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
