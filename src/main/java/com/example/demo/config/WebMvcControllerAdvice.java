package com.example.demo.config;

import com.example.demo.service.InquiryNotFoundException;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

// @ControllerAdviceアノテーションがついているクラスには全てのコントローラの共通処理を記述できる
@ControllerAdvice
public class WebMvcControllerAdvice {

    /*
     * This method changes empty character to null
     */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    // データがない時にdbからデータを取得した時に起きる例外をキャッチ
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleException(EmptyResultDataAccessException e, Model model) {
        model.addAttribute("message", e);
        return "error/CustomPage";
    }

    // 例外処理3
    @ExceptionHandler(InquiryNotFoundException.class)
    public String handleException(InquiryNotFoundException e, Model model) {
        model.addAttribute("message", e);
        return "error/CustomPage";
    }

}
