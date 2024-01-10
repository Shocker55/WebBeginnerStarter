package com.example.demo.service;

import com.example.demo.entity.Survey;

import java.util.List;

public interface SurveyService {
    void save(Survey survey);

    List<Survey> getAll();
}
